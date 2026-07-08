package com.beeleza.loan_service.service;

import com.beeleza.loan_service.domain.Loan;
import com.beeleza.loan_service.domain.LoanStatus;
import com.beeleza.loan_service.dto.LoanRequestDTO;
import com.beeleza.loan_service.dto.LoanResponseDTO;
import com.beeleza.loan_service.dto.NotificationRequestDTO;
import com.beeleza.loan_service.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);
    private static final BigDecimal DAILY_FINE_RATE = new BigDecimal("1.50");

    private final LoanRepository repository;
    private final WebClient webClient;

    public LoanService(LoanRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public LoanResponseDTO create(LoanRequestDTO request) {
        validateUserExists(request.getUserId());
        validateBookExists(request.getBookId());

        Loan loan = new Loan();
        loan.setUserId(request.getUserId());
        loan.setBookId(request.getBookId());
        loan.setLoanDate(request.getLoanDate() != null ? request.getLoanDate() : LocalDate.now());
        loan.setExpectedReturnDate(request.getExpectedReturnDate());
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setFine(BigDecimal.ZERO);

        String message = String.format("Empréstimo realizado - Livro: %s para o usuário: %s", request.getBookId(), request.getUserId());
        sendNotification(new NotificationRequestDTO(message, LocalDateTime.now()));

        return LoanResponseDTO.fromEntity(repository.save(loan));
    }

    public LoanResponseDTO returnBook(UUID id) {
        Loan loan = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan already returned");
        }

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);

        if (returnDate.isAfter(loan.getExpectedReturnDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(loan.getExpectedReturnDate(), returnDate);
            BigDecimal fine = DAILY_FINE_RATE.multiply(BigDecimal.valueOf(daysOverdue))
                    .setScale(2, RoundingMode.HALF_UP);
            loan.setFine(fine);
        } else {
            loan.setFine(BigDecimal.ZERO);
        }

        loan.setStatus(LoanStatus.RETURNED);

        return LoanResponseDTO.fromEntity(repository.save(loan));
    }

    public LoanResponseDTO findById(UUID id) {
        Loan loan = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
        return LoanResponseDTO.fromEntity(loan);
    }

    public List<LoanResponseDTO> findAll(LoanStatus status, UUID userId) {
        if (status != null && userId != null) {
            return repository.findByUserIdAndStatus(userId, status).stream()
                    .map(LoanResponseDTO::fromEntity)
                    .toList();
        }
        if (status != null) {
            return repository.findByStatus(status).stream()
                    .map(LoanResponseDTO::fromEntity)
                    .toList();
        }
        if (userId != null) {
            return repository.findByUserId(userId).stream()
                    .map(LoanResponseDTO::fromEntity)
                    .toList();
        }
        return repository.findAll().stream()
                .map(LoanResponseDTO::fromEntity)
                .toList();
    }

    public List<LoanResponseDTO> findOverdueLoans() {
        checkAndUpdateOverdueStatus();
        return repository.findByStatus(LoanStatus.OVERDUE).stream()
                .map(LoanResponseDTO::fromEntity)
                .toList();
    }

    public List<LoanResponseDTO> findActiveLoans() {
        return repository.findByStatus(LoanStatus.ACTIVE).stream()
                .map(LoanResponseDTO::fromEntity)
                .toList();
    }

    public void checkAndUpdateOverdueStatus() {
        List<Loan> overdueLoans = repository.findByStatusAndExpectedReturnDateBefore(
                LoanStatus.ACTIVE, LocalDate.now());
        for (Loan loan : overdueLoans) {
            loan.setStatus(LoanStatus.OVERDUE);
        }
        repository.saveAll(overdueLoans);
    }

    private void validateUserExists(UUID userId) {
        try {
            webClient.get()
                    .uri("http://localhost:8080/api/users/{id}", userId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId);
            }
            throw new ResponseStatusException(e.getStatusCode(), "Error validating user", e);
        }
    }

    private void validateBookExists(UUID bookId) {
        try {
            webClient.get()
                    .uri("http://localhost:8081/api/books/{id}", bookId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + bookId);
            }
            throw new ResponseStatusException(e.getStatusCode(), "Error validating book", e);
        }
    }

    private void sendNotification(NotificationRequestDTO request) {
        webClient.post()
                .uri("http://localhost:8083/api/notifications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> log.info("Notificação enviada com sucesso: {}", response),
                        error -> log.error("Erro ao enviar notificação: {}", error.getMessage(), error)
                );
    }
}

package com.beeleza.loan_service.dto;

import com.beeleza.loan_service.domain.Loan;
import com.beeleza.loan_service.domain.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanResponseDTO {

    private UUID id;
    private UUID userId;
    private UUID bookId;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private BigDecimal fine;

    public LoanResponseDTO() {
    }

    public LoanResponseDTO(UUID id, UUID userId, UUID bookId, LocalDate loanDate,
                           LocalDate expectedReturnDate, LocalDate returnDate,
                           LoanStatus status, BigDecimal fine) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.returnDate = returnDate;
        this.status = status;
        this.fine = fine;
    }

    public static LoanResponseDTO fromEntity(Loan loan) {
        return new LoanResponseDTO(
                loan.getId(),
                loan.getUserId(),
                loan.getBookId(),
                loan.getLoanDate(),
                loan.getExpectedReturnDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                loan.getFine()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}

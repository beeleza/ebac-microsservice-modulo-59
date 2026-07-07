package com.beeleza.loan_service.controller;

import com.beeleza.loan_service.domain.LoanStatus;
import com.beeleza.loan_service.dto.LoanRequestDTO;
import com.beeleza.loan_service.dto.LoanResponseDTO;
import com.beeleza.loan_service.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO create(@Valid @RequestBody LoanRequestDTO request) {
        return loanService.create(request);
    }

    @PutMapping("/{id}/return")
    public LoanResponseDTO returnBook(@PathVariable UUID id) {
        return loanService.returnBook(id);
    }

    @GetMapping("/{id}")
    public LoanResponseDTO findById(@PathVariable UUID id) {
        return loanService.findById(id);
    }

    @GetMapping
    public List<LoanResponseDTO> findAll(
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) UUID userId) {
        return loanService.findAll(status, userId);
    }

    @GetMapping("/overdue")
    public List<LoanResponseDTO> findOverdue() {
        return loanService.findOverdueLoans();
    }

    @GetMapping("/active")
    public List<LoanResponseDTO> findActive() {
        return loanService.findActiveLoans();
    }
}

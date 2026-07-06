package com.beeleza.loan_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanRequestDTO {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID bookId;

    private LocalDate loanDate;

    @NotNull
    @Future
    private LocalDate expectedReturnDate;

    private BigDecimal fine;

    public LoanRequestDTO() {
    }

    public LoanRequestDTO(UUID userId, UUID bookId, LocalDate loanDate,
                          LocalDate expectedReturnDate, BigDecimal fine) {
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.fine = fine;
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

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}

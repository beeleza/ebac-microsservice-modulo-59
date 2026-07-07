package com.beeleza.loan_service.repository;

import com.beeleza.loan_service.domain.Loan;
import com.beeleza.loan_service.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByUserId(UUID userId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByUserIdAndStatus(UUID userId, LoanStatus status);

    List<Loan> findByStatusAndExpectedReturnDateBefore(LoanStatus status, LocalDate date);
}

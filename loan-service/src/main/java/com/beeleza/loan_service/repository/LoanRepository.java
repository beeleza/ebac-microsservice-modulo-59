package com.beeleza.loan_service.repository;

import com.beeleza.loan_service.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}

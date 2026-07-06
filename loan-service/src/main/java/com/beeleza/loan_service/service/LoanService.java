package com.beeleza.loan_service.service;

import com.beeleza.loan_service.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LoanService {

    private final LoanRepository repository;
    private final WebClient webClient;

    public LoanService(LoanRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public String getUser() {
        return webClient.get()
                .uri("http://localhost:8080/api/users")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getHelloWorld() {
        return "Hello World";
    }
}

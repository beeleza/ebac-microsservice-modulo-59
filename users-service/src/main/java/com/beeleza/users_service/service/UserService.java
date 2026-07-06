package com.beeleza.users_service.service;

import com.beeleza.users_service.domain.dto.UserRequest;
import com.beeleza.users_service.domain.dto.UserResponse;
import com.beeleza.users_service.domain.model.User;
import com.beeleza.users_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse create(UserRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
        return UserResponse.fromEntity(repository.save(user));
    }

    public UserResponse findById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return UserResponse.fromEntity(user);
    }

    public List<UserResponse> findAll() {
        return repository.findAll().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public UserResponse update(UUID id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return UserResponse.fromEntity(repository.save(user));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repository.deleteById(id);
    }
}

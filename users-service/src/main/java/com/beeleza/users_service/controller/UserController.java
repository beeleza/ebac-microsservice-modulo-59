package com.beeleza.users_service.controller;

import com.beeleza.users_service.domain.dto.UserRequest;
import com.beeleza.users_service.domain.dto.UserResponse;
import com.beeleza.users_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody @Valid UserRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

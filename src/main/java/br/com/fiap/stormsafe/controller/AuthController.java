package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.dto.LoginRequest;
import br.com.fiap.stormsafe.dto.LoginResponse;
import br.com.fiap.stormsafe.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
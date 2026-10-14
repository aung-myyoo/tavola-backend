package com.tavola.backend.controller;

import com.tavola.backend.dto.LoginRequest;
import com.tavola.backend.dto.LoginResponse;
import com.tavola.backend.dto.StaffDto;
import com.tavola.backend.service.AuthService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** Public staff picker: active staff with name/photo/role, no PIN data, for the login screen. */
    @GetMapping("/roster")
    public List<StaffDto> getRoster() {
        return authService.getRoster();
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest body) {
        return authService.login(body.staffId(), body.pin());
    }
}

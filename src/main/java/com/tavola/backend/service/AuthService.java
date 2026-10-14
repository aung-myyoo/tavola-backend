package com.tavola.backend.service;

import com.tavola.backend.dto.LoginResponse;
import com.tavola.backend.dto.StaffDto;
import com.tavola.backend.model.Staff;
import com.tavola.backend.repository.StaffRepository;
import com.tavola.backend.security.InvalidCredentialsException;
import com.tavola.backend.security.JwtService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(StaffRepository staffRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public List<StaffDto> getRoster() {
        return staffRepository.findByActiveTrue().stream().map(StaffDto::from).toList();
    }

    public LoginResponse login(Long staffId, String pin) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new NoSuchElementException("Unknown staff member"));
        if (!staff.isActive() || !passwordEncoder.matches(pin, staff.getPinHash())) {
            throw new InvalidCredentialsException("Incorrect PIN");
        }
        String token = jwtService.generateToken(staff.getId(), staff.getRole().name());
        return new LoginResponse(token, StaffDto.from(staff));
    }
}

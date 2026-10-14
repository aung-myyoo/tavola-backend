package com.tavola.backend.service;

import com.tavola.backend.dto.CreateStaffRequest;
import com.tavola.backend.dto.StaffDto;
import com.tavola.backend.dto.UpdateStaffRequest;
import com.tavola.backend.model.Role;
import com.tavola.backend.model.Staff;
import com.tavola.backend.repository.StaffRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public StaffService(StaffRepository staffRepository, PasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    public List<StaffDto> getAll() {
        return staffRepository.findAll().stream().map(StaffDto::from).toList();
    }

    @Transactional
    public StaffDto create(CreateStaffRequest body) {
        Staff staff = new Staff(body.name(), Role.valueOf(body.role().toUpperCase()), passwordEncoder.encode(body.pin()));
        return StaffDto.from(staffRepository.save(staff));
    }

    @Transactional
    public StaffDto update(Long id, UpdateStaffRequest body) {
        Staff staff = requireStaff(id);
        staff.setName(body.name());
        staff.setRole(Role.valueOf(body.role().toUpperCase()));
        staff.setActive(body.active());
        return StaffDto.from(staffRepository.save(staff));
    }

    @Transactional
    public void delete(Long id) {
        Staff staff = requireStaff(id);
        fileStorageService.deleteStaffPhoto(staff.getPhotoUrl());
        staffRepository.delete(staff);
    }

    @Transactional
    public StaffDto updatePhoto(Long id, MultipartFile photo) {
        Staff staff = requireStaff(id);
        String oldPhoto = staff.getPhotoUrl();
        String newPhoto = fileStorageService.saveStaffPhoto(photo);
        staff.setPhotoUrl(newPhoto);
        Staff saved = staffRepository.save(staff);
        fileStorageService.deleteStaffPhoto(oldPhoto);
        return StaffDto.from(saved);
    }

    @Transactional
    public StaffDto removePhoto(Long id) {
        Staff staff = requireStaff(id);
        fileStorageService.deleteStaffPhoto(staff.getPhotoUrl());
        staff.setPhotoUrl(null);
        return StaffDto.from(staffRepository.save(staff));
    }

    private Staff requireStaff(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Unknown staff member: " + id));
    }
}

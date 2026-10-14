package com.tavola.backend.controller;

import com.tavola.backend.dto.StatusUpdateRequest;
import com.tavola.backend.dto.TableDto;
import com.tavola.backend.model.RestaurantTable;
import com.tavola.backend.model.TableStatus;
import com.tavola.backend.repository.RestaurantTableRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final RestaurantTableRepository tableRepository;

    public TableController(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @GetMapping
    public List<TableDto> getTables() {
        return tableRepository.findAll().stream().map(TableDto::from).toList();
    }

    @PatchMapping("/{number}/status")
    @PreAuthorize("hasAnyRole('SERVER', 'RECEPTIONIST', 'MANAGER', 'OWNER', 'OPERATOR')")
    public TableDto updateStatus(@PathVariable Integer number, @Valid @RequestBody StatusUpdateRequest body) {
        RestaurantTable table = tableRepository.findById(number)
                .orElseThrow(() -> new NoSuchElementException("Unknown table: " + number));
        table.setStatus(TableStatus.valueOf(body.status().toUpperCase()));
        return TableDto.from(tableRepository.save(table));
    }
}

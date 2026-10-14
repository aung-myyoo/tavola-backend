package com.tavola.backend.controller;

import com.tavola.backend.dto.MenuItemDto;
import com.tavola.backend.repository.MenuItemRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuItemRepository menuItemRepository;

    public MenuController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping
    public List<MenuItemDto> getMenu() {
        return menuItemRepository.findAll().stream().map(MenuItemDto::from).toList();
    }
}

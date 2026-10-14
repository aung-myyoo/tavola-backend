package com.tavola.backend.config;

import com.tavola.backend.model.MenuItem;
import com.tavola.backend.model.Role;
import com.tavola.backend.model.Staff;
import com.tavola.backend.model.RestaurantTable;
import com.tavola.backend.model.TableStatus;
import com.tavola.backend.repository.MenuItemRepository;
import com.tavola.backend.repository.RestaurantTableRepository;
import com.tavola.backend.repository.StaffRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantTableRepository tableRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            MenuItemRepository menuItemRepository,
            RestaurantTableRepository tableRepository,
            StaffRepository staffRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (menuItemRepository.count() == 0) {
            menuItemRepository.saveAll(List.of(
                    new MenuItem("garlic", "Starters", "Garlic Bread", "🥖", new BigDecimal("5.50"),
                            "Toasted ciabatta with herb butter and shaved parmesan, baked fresh to order.",
                            List.of("Vegetarian", "Contains Gluten"), 0,
                            "Contains: Gluten, Dairy. May contain traces of nuts.", true),
                    new MenuItem("soup", "Starters", "Tomato Soup", "🍵", new BigDecimal("6.50"),
                            "Slow-roasted vine tomatoes blended with basil oil, served with sourdough croutons.",
                            List.of("Vegan", "Contains Gluten"), 0,
                            "Contains: Gluten. Vegan-friendly.", true),
                    new MenuItem("salmon", "Mains", "Grilled Salmon", "🐟", new BigDecimal("18.00"),
                            "Fresh Atlantic salmon grilled with lemon butter sauce, seasonal veg and new potatoes.",
                            List.of("Gluten-Free", "Contains Fish"), 1,
                            "Contains: Fish. Dairy-free option available on request.", true),
                    new MenuItem("burger", "Mains", "Wagyu Burger", "🍔", new BigDecimal("16.00"),
                            "Wagyu beef patty in a brioche bun with smoked cheddar, caramelised onion and truffle mayo.",
                            List.of("Contains Gluten", "Contains Dairy"), 2,
                            "Contains: Gluten, Dairy, Egg. No nut traces.", true),
                    new MenuItem("pasta", "Mains", "Truffle Pasta", "🍝", new BigDecimal("14.00"),
                            "Tagliatelle tossed in a black truffle cream sauce with shaved pecorino.",
                            List.of("Vegetarian", "Contains Gluten"), 0,
                            "Contains: Gluten, Dairy, Egg.", false),
                    new MenuItem("curry", "Mains", "Spiced Chickpea Curry", "🍛", new BigDecimal("13.50"),
                            "Slow-simmered chickpeas in a coconut curry sauce, served with basmati rice and naan.",
                            List.of("Vegan", "Contains Gluten"), 3,
                            "Contains: Gluten. Vegan-friendly.", true),
                    new MenuItem("choco", "Desserts", "Chocolate Fondant", "🍫", new BigDecimal("8.00"),
                            "Warm dark chocolate fondant with a liquid centre, served with vanilla bean ice cream.",
                            List.of("Contains Dairy", "Vegetarian"), 0,
                            "Contains: Egg, Dairy, Gluten.", true),
                    new MenuItem("cheesecake", "Desserts", "Basque Cheesecake", "🍰", new BigDecimal("7.50"),
                            "Burnt Basque-style cheesecake, creamy centre with a caramelised top.",
                            List.of("Vegetarian", "Contains Dairy"), 0,
                            "Contains: Dairy, Egg, Gluten.", true),
                    new MenuItem("lemonade", "Drinks", "House Lemonade", "🍋", new BigDecimal("4.00"),
                            "Fresh-pressed lemon, mint and soda over ice.",
                            List.of("Vegan", "Gluten-Free"), 0,
                            "No known allergens.", true),
                    new MenuItem("espresso", "Drinks", "Espresso", "☕", new BigDecimal("3.00"),
                            "Double shot, roasted in-house.",
                            List.of("Vegan", "Gluten-Free"), 0,
                            "No known allergens.", true)
            ));
        }

        if (tableRepository.count() == 0) {
            tableRepository.saveAll(List.of(
                    new RestaurantTable(1, TableStatus.EMPTY, null, null, null),
                    new RestaurantTable(2, TableStatus.SEATED, 2, LocalDateTime.now(), "Alex"),
                    new RestaurantTable(3, TableStatus.UNPAID, 4, LocalDateTime.now().minusHours(1), "Sam"),
                    new RestaurantTable(4, TableStatus.SEATED, 3, LocalDateTime.now(), "Alex"),
                    new RestaurantTable(5, TableStatus.CALL, 2, LocalDateTime.now(), "Sam"),
                    new RestaurantTable(6, TableStatus.EMPTY, null, null, null),
                    new RestaurantTable(7, TableStatus.SEATED, 2, LocalDateTime.now(), "Alex"),
                    new RestaurantTable(8, TableStatus.READY, 4, LocalDateTime.now(), "Sam")
            ));
        }

        if (staffRepository.count() == 0) {
            staffRepository.saveAll(List.of(
                    new Staff("Rosa", Role.RECEPTIONIST, passwordEncoder.encode("1111")),
                    new Staff("Alex", Role.SERVER, passwordEncoder.encode("2222")),
                    new Staff("Sam", Role.SERVER, passwordEncoder.encode("2223")),
                    new Staff("Jamie", Role.CHEF, passwordEncoder.encode("3333")),
                    new Staff("Morgan", Role.MANAGER, passwordEncoder.encode("4444")),
                    new Staff("Taylor", Role.OPERATOR, passwordEncoder.encode("5555")),
                    new Staff("Jordan", Role.OWNER, passwordEncoder.encode("6666"))
            ));
        }
    }
}

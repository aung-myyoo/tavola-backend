package com.tavola.backend.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    private String id;

    private String category;
    private String name;
    private String emoji;
    private BigDecimal price;

    @Column(length = 500)
    private String description;

    @ElementCollection
    @CollectionTable(name = "menu_item_tags", joinColumns = @jakarta.persistence.JoinColumn(name = "menu_item_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    private int spice;

    @Column(length = 500)
    private String allergens;

    private boolean available;

    public MenuItem() {
    }

    public MenuItem(String id, String category, String name, String emoji, BigDecimal price,
                     String description, List<String> tags, int spice, String allergens, boolean available) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.emoji = emoji;
        this.price = price;
        this.description = description;
        this.tags = tags;
        this.spice = spice;
        this.allergens = allergens;
        this.available = available;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public int getSpice() { return spice; }
    public void setSpice(int spice) { this.spice = spice; }

    public String getAllergens() { return allergens; }
    public void setAllergens(String allergens) { this.allergens = allergens; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}

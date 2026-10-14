package com.tavola.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    private Integer number;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    private Integer guestCount;
    private LocalDateTime seatedAt;
    private String serverName;

    public RestaurantTable() {
    }

    public RestaurantTable(Integer number, TableStatus status, Integer guestCount, LocalDateTime seatedAt, String serverName) {
        this.number = number;
        this.status = status;
        this.guestCount = guestCount;
        this.seatedAt = seatedAt;
        this.serverName = serverName;
    }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }

    public Integer getGuestCount() { return guestCount; }
    public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }

    public LocalDateTime getSeatedAt() { return seatedAt; }
    public void setSeatedAt(LocalDateTime seatedAt) { this.seatedAt = seatedAt; }

    public String getServerName() { return serverName; }
    public void setServerName(String serverName) { this.serverName = serverName; }
}

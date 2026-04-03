package com.paulanussbrand.store.entities;

import com.paulanussbrand.store.enums.LeadStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String selectedSize;
    private String selectedColor;

    @Enumerated(EnumType.STRING)
    private LeadStatus status = LeadStatus.OPEN;

    private LocalDateTime createdAt;

    public Lead() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters manuais aqui...
}
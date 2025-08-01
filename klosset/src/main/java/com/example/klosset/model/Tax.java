package com.example.klosset.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Tax tax;

    // Getters & Setters
}

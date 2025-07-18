package com.example.klosset.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class StockOpname {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private int stokSistem;
    private int stokFisik;
    private String keterangan;
    private LocalDate tanggal;
    private int realStock;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRealStock(int realStock) {
        this.realStock = realStock;
    }

    public int getRealStock() {
        return realStock;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public int getStokSistem() {
        return stokSistem;
    }

    public void setStokSistem(int stokSistem) {
        this.stokSistem = stokSistem;
    }

    public int getStokFisik() {
        return stokFisik;
    }

    public void setStokFisik(int stokFisik) {
        this.stokFisik = stokFisik;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
}

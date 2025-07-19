package com.example.klosset.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama aset tidak boleh kosong")
    private String namaAset;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Jenis aset harus dipilih")
    private JenisAsset jenisAset;

    @NotNull(message = "Jumlah tidak boleh kosong")
    @Min(value = 1, message = "Jumlah minimal 1")
    private Integer qty;

    @NotNull(message = "Harga tidak boleh kosong")
    @DecimalMin(value = "0.01", message = "Harga harus lebih dari 0")
    private Double harga;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockOpname> stockOpnames;


    // Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    this.id = id;
}

    public String getNamaAset() {
        return namaAset;
    }

    public void setNamaAset(String namaAset) {
        this.namaAset = namaAset;
    }

    public JenisAsset getJenisAset() {
        return jenisAset;
    }

    public void setJenisAset(JenisAsset jenisAset) {
        this.jenisAset = jenisAset;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}

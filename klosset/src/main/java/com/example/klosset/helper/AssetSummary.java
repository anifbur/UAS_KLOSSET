package com.example.klosset.helper;

import com.example.klosset.model.JenisAsset;

public class AssetSummary {
    private JenisAsset kategori;
    private Double totalAsset;
    private Double pajakPersen = 0.2;
    private Double totalPajak;

    public AssetSummary(JenisAsset kategori, Double totalAsset) {
        this.kategori = kategori;
        this.totalAsset = totalAsset;
        this.totalPajak = totalAsset * pajakPersen;
    }

    public JenisAsset getKategori() {
        return kategori;
    }

    public Double getTotalAsset() {
        return totalAsset;
    }

    public Double getPajakPersen() {
        return pajakPersen;
    }

    public Double getTotalPajak() {
        return totalPajak;
    }
}

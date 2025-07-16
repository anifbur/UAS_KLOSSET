package com.example.klosset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Halaman dashboard/index
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // Halaman tambah aset (untuk user)
    @GetMapping("/assets/add")
    public String addAsset() {
        return "add-asset"; // file: templates/add-asset.html
    }

    // Halaman kalkulator pajak (untuk admin)
    @GetMapping("/tax/calculator")
    public String taxCalculator() {
        return "calculator"; // file: templates/calculator.html
    }

    // Halaman stok barang (untuk admin)
    @GetMapping("/stok")
    public String stokPage() {
        return "stok"; // file: templates/stok.html
    }

    // Halaman error 403 (jika akses ditolak)
    @GetMapping("/403")
    public String error403() {
        return "403"; // file: templates/403.html
    }
}

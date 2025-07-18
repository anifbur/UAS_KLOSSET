package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private AssetRepository assetRepository;

    // Tampilkan dashboard
    @GetMapping("/index")
    public String showDashboard(Model model, Authentication authentication) {
        // Ambil semua data aset
        List<Asset> assets = assetRepository.findAll();

        // Kirim data aset ke view
        model.addAttribute("assets", assets);

        // Tambahkan nama user jika ingin
        model.addAttribute("username", authentication.getName());

        return "index"; // Akan diarahkan ke dashboard.html
    }
}

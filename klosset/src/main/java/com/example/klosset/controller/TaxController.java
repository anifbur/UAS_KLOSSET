package com.example.klosset.controller;

import com.example.klosset.helper.AssetGroupProjection;
import com.example.klosset.model.Asset;
import com.example.klosset.model.JenisAsset;
import com.example.klosset.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.klosset.helper.AssetGroupProjection;
import com.example.klosset.helper.AssetSummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaxController {

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/tax/calculator")
    public String showTax(Model model, Authentication authentication) {
        List<AssetGroupProjection> groupedAssets = assetRepository.findGroupedAssets();

        // Mapping grouped data ke summary
        List<AssetSummary> summaries = groupedAssets.stream()
                .map(data -> new AssetSummary(
                        JenisAsset.valueOf(data.getKategori()),
                        data.getTotalAsset()))
                .collect(Collectors.toList());

        // Hitung totalAsset dan totalPajak
        Double totalAsset = 0.0;
        Double totalPajak = 0.0;

        for (AssetSummary summary : summaries) {
            Double asset = summary.getTotalAsset();
            Double pajakPersen = Double.valueOf(summary.getPajakPersen());
            Double pajak = asset * pajakPersen;

            totalAsset += asset;
            totalPajak += pajak;

            summary.setTotalPajak(pajak);
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("summaries", summaries);
        model.addAttribute("totalAsset", totalAsset);
        model.addAttribute("totalPajak", totalPajak);
        model.addAttribute("username", authentication.getName());

        return "tax";
    }

}

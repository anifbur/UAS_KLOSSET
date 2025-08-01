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

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaxController {

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/tax/calculator")
    public String showTax(Model model, Authentication authentication) {
        List<AssetGroupProjection> groupedAssets = assetRepository.findGroupedAssets();

        // Hitung total pajak (20%) manual
        List<AssetSummary> summaries = groupedAssets.stream()
                .map(data -> new AssetSummary(
                        JenisAsset.valueOf(data.getKategori()), // konversi String ke enum
                        data.getTotalAsset()))
                .collect(Collectors.toList());

        model.addAttribute("summaries", summaries);
        model.addAttribute("username", authentication.getName());
        return "tax";
    }

}

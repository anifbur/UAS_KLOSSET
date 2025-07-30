package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.repository.AssetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TaxController {

    @Autowired
    private AssetRepository taxRepository;

    @GetMapping("/tax/add")
    public String showAddForm(Model model) {
        model.addAttribute("asset", new Asset());
        return "add-asset";
    }

    @PostMapping("/tax/add")
    public String addAsset(@Valid @ModelAttribute("asset") Asset asset,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "add-asset";
        }

        taxRepository.save(asset);
        return "redirect:/index";
    }

    // ✅ Perbaikan updateAsset
    @PostMapping("/tax/update")
    public String updateAsset(@ModelAttribute Asset asset) {
        if (asset.getId() == null) {
            return "redirect:/index";
        }

        Optional<Asset> existingAssetOpt = taxRepository.findById(asset.getId());
        if (existingAssetOpt.isPresent()) {
            Asset existingAsset = existingAssetOpt.get();
            existingAsset.setNamaAset(asset.getNamaAset());
            existingAsset.setJenisAset(asset.getJenisAset());
            existingAsset.setQty(asset.getQty());
            existingAsset.setHarga(asset.getHarga());
            taxRepository.save(existingAsset);
        }

        return "redirect:/index";
    }

    @GetMapping("/tax/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        if (taxRepository.existsById(id)) {
            taxRepository.deleteById(id);
        }
        return "redirect:/index";
    }
}

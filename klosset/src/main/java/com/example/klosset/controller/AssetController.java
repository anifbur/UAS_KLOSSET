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
public class AssetController {

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/assets/add")
    public String showAddForm(Model model) {
        model.addAttribute("asset", new Asset());
        return "add-asset";
    }

    @PostMapping("/assets/add")
    public String addAsset(@Valid @ModelAttribute("asset") Asset asset,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "add-asset";
        }

        assetRepository.save(asset);
        return "redirect:/index";
    }

    // ✅ Perbaikan updateAsset
    @PostMapping("/assets/update")
    public String updateAsset(@ModelAttribute Asset asset) {
        if (asset.getId() == null) {
            return "redirect:/index";
        }

        Optional<Asset> existingAssetOpt = assetRepository.findById(asset.getId());
        if (existingAssetOpt.isPresent()) {
            Asset existingAsset = existingAssetOpt.get();
            existingAsset.setNamaAset(asset.getNamaAset());
            existingAsset.setJenisAset(asset.getJenisAset());
            existingAsset.setQty(asset.getQty());
            existingAsset.setHarga(asset.getHarga());
            assetRepository.save(existingAsset);
        }

        return "redirect:/index";
    }

    @GetMapping("/assets/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id);
        }
        return "redirect:/index";
    }
}

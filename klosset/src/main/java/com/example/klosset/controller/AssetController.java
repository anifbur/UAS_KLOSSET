package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.repository.AssetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
}

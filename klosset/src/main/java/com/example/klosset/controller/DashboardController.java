package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.model.JenisAsset;
import com.example.klosset.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private AssetRepository assetRepository;

   @GetMapping("/index")
public String showDashboard(Model model, Authentication authentication) {
    List<Asset> assets = assetRepository.findAll();

    
    Map<String, Integer> assetCountByType = assets.stream()
            .collect(Collectors.groupingBy(
                    asset -> asset.getJenisAset().toString(),
                    Collectors.summingInt(Asset::getQty)
            ));

    model.addAttribute("assets", assets);
    model.addAttribute("username", authentication.getName());
    model.addAttribute("assetCountByType", assetCountByType);

    return "index";
}


}

package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.model.StockOpname;
import com.example.klosset.repository.AssetRepository;
import com.example.klosset.repository.StockOpnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/stock-opname-list")
public class StockOpnameController {

    @Autowired
    private StockOpnameRepository stockOpnameRepository;

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("assets", assetRepository.findAll());
        return "stock-opname-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam("assetId") Long assetId,
                       @RequestParam("realStock") int realStock) {

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("ID aset tidak valid"));

        int systemStock = asset.getQty(); // Asumsi field jumlah = stok sistem
        String note = (realStock == systemStock) ? "Sesuai" : "Tidak Sesuai";

        StockOpname opname = new StockOpname();
        opname.setAsset(asset);
        opname.setStokFisik(realStock);
        opname.setStokSistem(systemStock);
        opname.setKeterangan(note);
        opname.setTanggal(LocalDate.now());

        stockOpnameRepository.save(opname);

        return "redirect:/stock-opname-list/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("stockOpnames", stockOpnameRepository.findAll());
        return "stock-opname-list";
    }
}


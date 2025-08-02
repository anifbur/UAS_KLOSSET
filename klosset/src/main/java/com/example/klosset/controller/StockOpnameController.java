package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.model.StockOpname;
import com.example.klosset.repository.AssetRepository;
import com.example.klosset.repository.StockOpnameRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/stock-opname-list")
public class StockOpnameController {

    @Autowired
    private StockOpnameRepository stockOpnameRepository;

    @Autowired
    private AssetRepository assetRepository;

    // Menampilkan form STO satuan
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("stockOpname", new StockOpname());
        model.addAttribute("assets", assetRepository.findAll());
        return "stock-opname-form";
    }

    // Simpan STO satuan
    @PostMapping("/save")
    public String save(@ModelAttribute StockOpname stockOpname) {
        Asset asset = assetRepository.findById(stockOpname.getAsset().getId()).orElse(null);
        if (asset != null) {
            stockOpname.setStokSistem(asset.getQty());
            stockOpname.setTanggal(LocalDate.now());
            if (stockOpname.getStokFisik() == asset.getQty()) {
                stockOpname.setKeterangan("Sesuai");
            } else {
                stockOpname.setKeterangan("Tidak Sesuai");
            }
            stockOpnameRepository.save(stockOpname);
        }
        return "redirect:/stock-opname-list/list";
    }

    // ✅ FIXED: Menampilkan list STO (group by tanggal)
    @GetMapping("/list")
    public String listStockOpname(Model model, Authentication authentication) {
        List<StockOpname> stockOpnames = stockOpnameRepository.findAll();

        List<LocalDate> tanggalUnik = stockOpnames.stream()
                .map(StockOpname::getTanggal)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        model.addAttribute("tanggalUnik", tanggalUnik);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);
        return "stock-opname-list";
    }

    // Menampilkan form STO All
    @GetMapping("/form-all")
    public String showFormAll(Model model) {
        model.addAttribute("assets", assetRepository.findAll());
        return "stock-opname-form-all";
    }

    // Simpan semua hasil STO sekaligus
    @PostMapping("/save-all")
    public String saveAll(@RequestParam MultiValueMap<String, String> formData) {
        List<Asset> allAssets = assetRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Asset asset : allAssets) {
            String realStockStr = formData.getFirst("realStock_" + asset.getId());
            if (realStockStr != null && !realStockStr.isEmpty()) {
                int realStock = Integer.parseInt(realStockStr);
                int systemStock = asset.getQty();
                String note = (realStock == systemStock) ? "Sesuai" : "Tidak Sesuai";

                StockOpname opname = new StockOpname();
                opname.setAsset(asset);
                opname.setStokFisik(realStock);
                opname.setStokSistem(systemStock);
                opname.setKeterangan(note);
                opname.setTanggal(today);
                stockOpnameRepository.save(opname);
            }
        }

        return "redirect:/stock-opname-list/list";
    }

    // Export PDF berdasarkan tanggal STO
    @GetMapping("/export/pdf/{tanggal}")
    @ResponseBody
    public void exportPdfByTanggal(@PathVariable("tanggal") String tanggalStr, HttpServletResponse response)
            throws Exception {
        LocalDate tanggal = LocalDate.parse(tanggalStr);
        List<StockOpname> data = stockOpnameRepository.findByTanggal(tanggal);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=stock-opname-" + tanggal + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Paragraph title = new Paragraph("Laporan Stock Opname (" + tanggal + ")",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 3, 2, 2, 3, 3 });
        Stream.of("Nama Aset", "Stok Sistem", "Stok Fisik", "Keterangan", "Tanggal")
                .forEach(col -> {
                    PdfPCell header = new PdfPCell(new Phrase(col));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

        for (StockOpname opname : data) {
            table.addCell(opname.getAsset().getNamaAset());
            table.addCell(String.valueOf(opname.getStokSistem()));
            table.addCell(String.valueOf(opname.getStokFisik()));
            table.addCell(opname.getKeterangan());
            table.addCell(opname.getTanggal().toString());
        }

        document.add(table);
        document.close();
    }
}

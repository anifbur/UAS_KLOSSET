package com.example.klosset.controller;

import com.example.klosset.model.Asset;
import com.example.klosset.model.StockOpname;
import com.example.klosset.repository.AssetRepository;
import com.example.klosset.repository.StockOpnameRepository;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

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

        int systemStock = asset.getQty();
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

    @GetMapping("/export/pdf")
    @ResponseBody
    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=stock-opname.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Laporan Stock Opname", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 2, 3, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        Stream.of("Nama Aset", "Stok Sistem", "Stok Fisik", "Keterangan", "Tanggal")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });

        for (StockOpname opname : stockOpnameRepository.findAll()) {
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

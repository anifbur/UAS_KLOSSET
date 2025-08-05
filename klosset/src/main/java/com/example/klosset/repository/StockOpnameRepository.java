package com.example.klosset.repository;

import com.example.klosset.model.StockOpname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockOpnameRepository extends JpaRepository<StockOpname, Long> {
    List<StockOpname> findByTanggal(LocalDate tanggal); // ✅ Tambahkan ini
}

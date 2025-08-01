package com.example.klosset.repository;

import com.example.klosset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Asset, Long> {

}

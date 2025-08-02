package com.example.klosset.repository;

import com.example.klosset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.klosset.helper.AssetGroupProjection;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query(value = "SELECT jenis_aset AS kategori, SUM(qty * harga) AS totalAsset FROM asset GROUP BY jenis_aset", nativeQuery = true)
    List<AssetGroupProjection> findGroupedAssets();

    List<Asset> findByNamaAsetContainingIgnoreCase(String keyword);
}

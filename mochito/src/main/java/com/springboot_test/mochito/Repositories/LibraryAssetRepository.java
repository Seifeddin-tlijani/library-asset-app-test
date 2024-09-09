package com.springboot_test.mochito.Repositories;

import com.springboot_test.mochito.entities.LibraryAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryAssetRepositor extends JpaRepository<LibraryAsset,Long> {
    LibraryAsset findByAssetCode(String assetCode);
    List<LibraryAsset> findByAssetTitle(String assetTitle);
    List<LibraryAsset> findByCreatorName(String assetCreator);
    List<LibraryAsset> findByPublisherName(String publisherName);
    List<LibraryAsset> findByAssetCategory(String assetCategory);
    List<LibraryAsset> findByAssetType(String assetCategory);
}

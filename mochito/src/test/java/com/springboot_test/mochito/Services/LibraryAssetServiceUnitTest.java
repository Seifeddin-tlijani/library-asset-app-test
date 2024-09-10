package com.springboot_test.mochito.Services;


import com.springboot_test.mochito.DTOs.AssetAttributesDTO;
import com.springboot_test.mochito.Repositories.LibraryAssetRepository;
import com.springboot_test.mochito.entities.LibraryAsset;
import com.springboot_test.mochito.enums.EntityCharacteristics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)

public class LibraryAssetServiceUnitTest {

    private final LibraryAsset bookThingsFallApart =
            LibraryAsset.builder().assetTitle("Things Fall Apart").assetCode("0385474547")
                    .editionYear((short) 1958).creatorName("Chinua Achebe").publisherName("William Heinemann")
                    .priceUSD("17.95").assetCategory(String.valueOf(EntityCharacteristics.BOOK_FICTION))
                    .assetType(String.valueOf(EntityCharacteristics.BOOK_PAPERBACK)).build();

    private final LibraryAsset mediaTAJazz =
            LibraryAsset.builder().assetTitle("Trans Atlantic Jazz").assetCode("0385474548")
                    .editionYear((short) 2001).creatorName("Jazperience").publisherName("L. Asle Roe")
                    .priceUSD("9.95").assetCategory(String.valueOf(EntityCharacteristics.MEDIA_AUDIO))
                    .assetType(String.valueOf(EntityCharacteristics.MEDIA_AUDIO)).build();

    private final List<LibraryAsset> baseAssets = Arrays.asList(this.bookThingsFallApart, this.mediaTAJazz);

    @Mock
    private LibraryAssetRepository mockRepository;

    @InjectMocks
    private LibraryAssetService assetService;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(LibraryAssetServiceUnitTest.class);
    }

    @Test
    public void whenAssetAdded_thenControlFlowAsExpected() {
        LibraryAsset assetToAdd = this.baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        Mockito.when(this.mockRepository.save(Mockito.any(LibraryAsset.class)))
                .thenReturn(assetToAdd);

        this.assetService.addAsset(assetToAdd);

        Mockito.verify(this.mockRepository, Mockito.times(1))
                .save(Mockito.any(LibraryAsset.class));
    }
    @Test
    public void whenAssetDeleted_thenControlFlowAsExpected() {
        Mockito.doNothing().when(this.mockRepository).delete(Mockito.any(LibraryAsset.class));
        this.assetService.removeAsset(this.baseAssets.get(new Random().nextInt(this.baseAssets.size())));

        Mockito.verify(this.mockRepository, Mockito.times(1))
                .delete(Mockito.any(LibraryAsset.class));
    }

    @Test
    public void whenAssetsDeletedByCode_thenControlFlowAsExpected() {
        Mockito.when(this.mockRepository.findByAssetCode(Mockito.any(String.class)))
                .thenReturn(this.baseAssets.get(new Random().nextInt(this.baseAssets.size())));
        Mockito.doNothing().when(this.mockRepository).delete(Mockito.any(LibraryAsset.class));

        this.assetService.removeAsset("Lorem-Ipsum");

        Mockito.verify(this.mockRepository, Mockito.times(1))
                .delete(Mockito.any(LibraryAsset.class));
    }

    @Test
    public void whenAssetUpdated_thenControlFlowAsExpected() {
        LibraryAsset assetToQuery = this.baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        Mockito.when(this.mockRepository.findByAssetCode(Mockito.any(String.class)))
                .thenReturn(assetToQuery);
        Mockito.when(this.mockRepository.save(Mockito.any(LibraryAsset.class))).thenReturn(assetToQuery);

        this.assetService.updateAssetAttributes("Lorem-Ipsum", new AssetAttributesDTO());

        Mockito.verify(this.mockRepository, Mockito.times(1))
                .save(Mockito.any(LibraryAsset.class));
    }
    @ParameterizedTest
    @CsvFileSource(resources = {"/assets.csv"}, numLinesToSkip = 1)
    public void whenAssetsQueried_thenDetailsAsExpected(String queriedBy, String queryValue) {
        List<LibraryAsset> objAssets = new ArrayList<>();

        boolean isParamTitle = queriedBy.equals("Title");

        if (isParamTitle) {
            Mockito.when(this.mockRepository.findByAssetTitle(Mockito.any(String.class)))
                    .thenReturn(this.baseAssets);

            objAssets = this.assetService.findByTitle(queryValue);
        }

        Assertions.assertEquals(this.baseAssets.size(), objAssets.size());
        for (int idx = 0; idx < this.baseAssets.size(); idx++) {
            Assertions.assertEquals(this.baseAssets.get(idx).getAssetTitle(), objAssets.get(idx).getAssetTitle());
        }
    }



}

package com.springboot_test.mochito.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AssetAttributesDTO {

    @JsonProperty
    private String assetTitle;
    @JsonProperty private String creatorName;
    @JsonProperty private String publisherName;
    @JsonProperty private String assetCategory;
    @JsonProperty private String assetType;
    @JsonProperty private Short editionYear;
    @JsonProperty private String priceUSD;
}

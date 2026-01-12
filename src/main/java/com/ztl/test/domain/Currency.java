package com.ztl.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    private String name;
    @JsonProperty("symbol")
    private String currencySymbol;
}

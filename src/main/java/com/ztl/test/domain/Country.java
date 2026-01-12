package com.ztl.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Country {
    @JsonProperty("name")
    Name name;
    @JsonProperty("currencies")
    Map<String, Currency> currencies;
}

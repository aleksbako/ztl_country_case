package com.ztl.test.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Currency details and countries using it")
public class ApiCurrencyDetail {
    @Schema(description = "Full currency name", example = "United States Dollar")
    private String fullName;
    @Schema(description = "List of country names using this currency")
    private List<String> countries;
}
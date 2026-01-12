package com.ztl.test.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Country data with its currencies")
public class ApiCountry {
    @Schema(description = "Country name", example = "Germany")
    String name;
    @Schema(description = "List of currencies used in this country")
    List<ApiCurrency> currency;
}

package com.ztl.test.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Currency information")
public class ApiCurrency {
    @Schema(description = "Currency ISO code", example = "EUR")
    String isoCode;

    @Schema(description = "Currency name", example = "Euro")
    String name;
}

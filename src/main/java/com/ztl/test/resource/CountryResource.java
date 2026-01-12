package com.ztl.test.resource;


import com.ztl.test.domain.Country;
import com.ztl.test.domain.SortOrder;
import com.ztl.test.domain.response.ApiCountry;
import com.ztl.test.service.CountryService;
import com.ztl.test.util.RateLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryResource {

    @Autowired
    CountryService countryService;

    private static final Logger log = LoggerFactory.getLogger(CountryResource.class);

    @GetMapping("/eu-currencies")
    @RateLimited(key = "country", interval = 20L)
    @Operation(summary = "Get all European countries with currencies")
    public ResponseEntity<List<ApiCountry>> getEuropeanCountriesCurrency(@Parameter(description = "Sort order: ASC or DESC", example = "ASC")
                                                                             @RequestParam(name = "sort", defaultValue = "ASC") SortOrder order) {
        log.info("Fetching European country currencies , ordering by {}", order);
        var response = countryService.getEuropeanCountryCurrencies(order);
        return ResponseEntity.ok(response);
    }
}

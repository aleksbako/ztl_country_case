package com.ztl.test.resource;

import com.ztl.test.domain.response.ApiCurrencyDetail;
import com.ztl.test.service.CurrencyService;
import com.ztl.test.util.RateLimited;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/currency")
public class CurrencyResource {

    @Autowired
    CurrencyService currencyService;

    private static final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    @GetMapping("/countries")
    @RateLimited(key = "currency", interval = 20L)
    @Operation(summary = "Get all currencies in the world and which countries use them")
    public ResponseEntity<Map<String, ApiCurrencyDetail>> getAllCountriesByCurrency(){
        log.info("Fetching all currencies and the countries that use them.");
        var response = currencyService.getAllCountriesByCurrency();
        return ResponseEntity.ok(response);
    }
}

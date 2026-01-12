package com.ztl.test.integration;

import com.ztl.test.service.CurrencyService;
import com.ztl.test.util.RateLimitAspect;
import com.ztl.test.util.RateLimited;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static com.ztl.test.helper.CurrencyHelper.createCurrencyDetail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyResourceIT {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurrencyService currencyService;

    @MockitoBean
    private RateLimitAspect rateLimitAspect;

    @Test
    void returnCurrenciesAndCountriesTest() throws Exception {
        doNothing().when(rateLimitAspect).rateLimitCheck(any(RateLimited.class));

        var response = Map.of(
                "EUR", createCurrencyDetail("Euro", List.of("Germany", "Austria")),
                "USD", createCurrencyDetail("Dollar", List.of("USA"))
        );

        when(currencyService.getAllCountriesByCurrency()).thenReturn(response);

        mockMvc.perform(get("/api/currency/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.EUR.fullName").value("Euro"))
                .andExpect(jsonPath("$.EUR.countries[0]").value("Germany"))
                .andExpect(jsonPath("$.EUR.countries[1]").value("Austria"))
                .andExpect(jsonPath("$.USD.fullName").value("Dollar"))
                .andExpect(jsonPath("$.USD.countries[0]").value("USA"));
    }

    @Test
    void returnEmptyMapWhenNoCurrenciesTest() throws Exception {
        doNothing().when(rateLimitAspect).rateLimitCheck(any(RateLimited.class));

        when(currencyService.getAllCountriesByCurrency()).thenReturn(Map.of());

        mockMvc.perform(get("/api/currency/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}

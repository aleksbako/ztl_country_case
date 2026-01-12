package com.ztl.test.integration;

import com.ztl.test.domain.Country;
import com.ztl.test.domain.Currency;
import com.ztl.test.domain.Name;
import com.ztl.test.exception.RateLimitExceededProblem;
import com.ztl.test.util.RateLimitAspect;
import com.ztl.test.util.RateLimited;
import com.ztl.test.util.ZTLWebClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static com.ztl.test.helper.CountryHelper.getCountryMock;
import static com.ztl.test.helper.CountryHelper.getCurrencyMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ZTLWebClient webClient;

    @MockitoBean
    private RateLimitAspect rateLimitAspect;

    @Test
    void returnEuropeanCountriesSortedAscendingTest() throws Exception {
        doNothing().when(rateLimitAspect).rateLimitCheck(any(RateLimited.class));

        List<Country> countries = List.of(
                getCountryMock("Germany", Map.of("EUR", getCurrencyMock("Euro"))),
                getCountryMock("Austria", Map.of("EUR", getCurrencyMock("Euro"))),
                getCountryMock("Belgium", Map.of("EUR", getCurrencyMock("Euro")))
        );

        when(webClient.getEntityList(
                Mockito.contains("/v3.1/region/europe"),
                Mockito.isNull(),
                Mockito.eq(Country.class)
        )).thenReturn(countries);

        mockMvc.perform(get("/api/country/eu-currencies").param("sort", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))

                .andExpect(jsonPath("$[0].name").value("Austria"))
                .andExpect(jsonPath("$[0].currency[0].isoCode").value("EUR"))
                .andExpect(jsonPath("$[0].currency[0].name").value("Euro"))

                .andExpect(jsonPath("$[1].name").value("Belgium"))
                .andExpect(jsonPath("$[1].currency[0].isoCode").value("EUR"))
                .andExpect(jsonPath("$[1].currency[0].name").value("Euro"))

                .andExpect(jsonPath("$[2].name").value("Germany"))
                .andExpect(jsonPath("$[2].currency[0].isoCode").value("EUR"))
                .andExpect(jsonPath("$[2].currency[0].name").value("Euro"));

    }

    @Test
    void returnEuropeanCountriesSortedDescendingTest() throws Exception {
        doNothing().when(rateLimitAspect).rateLimitCheck(any(RateLimited.class));

        List<Country> countries = List.of(
                getCountryMock("Germany", Map.of("EUR", getCurrencyMock("Euro"))),
                getCountryMock("Austria", Map.of("EUR", getCurrencyMock("Euro")))
        );

        when(webClient.getEntityList(
                Mockito.anyString(),
                Mockito.isNull(),
                Mockito.eq(Country.class)
        )).thenReturn(countries);

        mockMvc.perform(get("/api/country/eu-currencies").param("sort", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].name").value("Germany"))
                .andExpect(jsonPath("$[0].currency[0].isoCode").value("EUR"))
                .andExpect(jsonPath("$[0].currency[0].name").value("Euro"))

                .andExpect(jsonPath("$[1].name").value("Austria"))
                .andExpect(jsonPath("$[1].currency[0].isoCode").value("EUR"))
                .andExpect(jsonPath("$[1].currency[0].name").value("Euro"));
    }

    @Test
    void noCountryFoundTest() throws Exception {

        doNothing().when(rateLimitAspect)
                .rateLimitCheck(any(RateLimited.class));

        when(webClient.getEntityList(
                Mockito.anyString(),
                Mockito.isNull(),
                Mockito.eq(Country.class)
        )).thenReturn(List.of());

        doNothing().when(rateLimitAspect).rateLimitCheck(any(RateLimited.class));

        when(webClient.getEntityList(
                Mockito.anyString(),
                Mockito.isNull(),
                Mockito.eq(Country.class)
        )).thenReturn(List.of());

        mockMvc.perform(get("/api/country/eu-currencies"))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Resource not found"))
                .andExpect(jsonPath("$.detail").value("No European countries found"))
                .andExpect(jsonPath("$.instance").value("/api/country/eu-currencies"));
    }

}

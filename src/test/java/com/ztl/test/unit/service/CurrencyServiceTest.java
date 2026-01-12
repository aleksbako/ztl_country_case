package com.ztl.test.unit.service;

import com.ztl.test.repository.CountryRepository;
import com.ztl.test.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static com.ztl.test.helper.CountryHelper.getCountryMock;
import static com.ztl.test.helper.CountryHelper.getCurrencyMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CurrencyServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnMapForMultipleCountriesTest() {
        when(countryRepository.getAllCountriesNameAndCurrencies())
                .thenReturn(List.of(
                        getCountryMock("Germany", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("Austria", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("USA", Map.of("USD", getCurrencyMock("Dollar")))
                ));

        var result = currencyService.getAllCountriesByCurrency();

        assertEquals(2, result.size());

        var eur = result.get("EUR");
        assertEquals("Euro", eur.getFullName());
        assertTrue(eur.getCountries().containsAll(List.of("Germany", "Austria")));

        var usd = result.get("USD");
        assertEquals("Dollar", usd.getFullName());
        assertTrue(usd.getCountries().contains("USA"));
    }

    @Test
    void skipCountriesWithNoCurrenciesTest() {
        when(countryRepository.getAllCountriesNameAndCurrencies())
                .thenReturn(List.of(getCountryMock("TestLand", null)));

        var result = currencyService.getAllCountriesByCurrency();
        assertTrue(result.isEmpty());
    }

    @Test
    void handleMultipleCurrenciesPerCountryTest() {
        when(countryRepository.getAllCountriesNameAndCurrencies())
                .thenReturn(List.of(
                        getCountryMock("TestLand", Map.of(
                                "EUR", getCurrencyMock("Euro"),
                                "USD", getCurrencyMock("Dollar")
                        ))
                ));

        var result = currencyService.getAllCountriesByCurrency();
        assertEquals(2, result.size());

        assertEquals("Euro", result.get("EUR").getFullName());
        assertTrue(result.get("EUR").getCountries().contains("TestLand"));

        assertEquals("Dollar", result.get("USD").getFullName());
        assertTrue(result.get("USD").getCountries().contains("TestLand"));
    }

    @Test
    void returnEmptyMapWhenRepositoryEmptyTest() {
        when(countryRepository.getAllCountriesNameAndCurrencies())
                .thenReturn(List.of());

        var result = currencyService.getAllCountriesByCurrency();
        assertTrue(result.isEmpty());
    }

}

package com.ztl.test.unit.service;


import com.ztl.test.domain.SortOrder;
import com.ztl.test.exception.NotFoundProblem;
import com.ztl.test.repository.CountryRepository;
import com.ztl.test.service.CountryService;
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
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnSortedCountriesAscendingTest() {
        when(countryRepository.getRegionsCountryNameAndCurrencies("europe"))
                .thenReturn(List.of(
                        getCountryMock("Germany", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("Austria", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("Belgium", Map.of("EUR", getCurrencyMock("Euro")))
                ));

        var result = countryService.getEuropeanCountryCurrencies(SortOrder.ASC);

        assertEquals(3, result.size());
        assertEquals("Austria", result.get(0).getName());
        assertEquals("Belgium", result.get(1).getName());
        assertEquals("Germany", result.get(2).getName());
    }

    @Test
    void returnSortedCountriesDescendingTest() {
        when(countryRepository.getRegionsCountryNameAndCurrencies("europe"))
                .thenReturn(List.of(
                        getCountryMock("Germany", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("Austria", Map.of("EUR", getCurrencyMock("Euro"))),
                        getCountryMock("Belgium", Map.of("EUR", getCurrencyMock("Euro")))
                ));

        var result = countryService.getEuropeanCountryCurrencies(SortOrder.DESC);

        assertEquals(3, result.size());
        assertEquals("Germany", result.get(0).getName());
        assertEquals("Belgium", result.get(1).getName());
        assertEquals("Austria", result.get(2).getName());

    }

    @Test
    void throwNotFoundProblemWhenEmptyTest() {
        when(countryRepository.getRegionsCountryNameAndCurrencies("europe"))
                .thenReturn(List.of());

        assertThrows(NotFoundProblem.class,
                () -> countryService.getEuropeanCountryCurrencies(SortOrder.ASC));
    }

    @Test
    void handleCountryWithoutCurrencyTest() {
        when(countryRepository.getRegionsCountryNameAndCurrencies("europe"))
                .thenReturn(List.of(getCountryMock("Norway", Map.of())));

        var result = countryService.getEuropeanCountryCurrencies(SortOrder.ASC);
        assertEquals(1, result.size());
        assertEquals("Norway", result.get(0).getName());
        assertTrue(result.get(0).getCurrency().isEmpty());
    }

    @Test
    void sortCountriesByCurrencyWhenNameEqualAndCurrencyEmptyTest() {
        when(countryRepository.getRegionsCountryNameAndCurrencies("europe"))
                .thenReturn(List.of(
                        getCountryMock("Germany", Map.of()),
                        getCountryMock("Belgium", Map.of("EUR", getCurrencyMock("Euro")))
                ));

        var result = countryService.getEuropeanCountryCurrencies(SortOrder.ASC);

        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(c -> c.getCurrency().isEmpty()));
        assertEquals("Belgium", result.get(0).getName());
    }
}

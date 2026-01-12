package com.ztl.test.domain.mapper;

import com.ztl.test.domain.Country;
import com.ztl.test.domain.Currency;
import com.ztl.test.domain.response.ApiCountry;
import com.ztl.test.domain.response.ApiCurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CountryMapper {
    public static ApiCountry mapToApiCountry(Country country) {
        if (country == null)
            return null;

        ApiCountry apiCountry = new ApiCountry();

        if (country.getName() != null)
            apiCountry.setName(country.getName().getCommonName());

        apiCountry.setCurrency(mapToApiCurrencyList(country.getCurrencies()));

        return apiCountry;
    }

    public static List<ApiCurrency> mapToApiCurrencyList(Map<String, Currency> currencies) {
        if (currencies == null || currencies.isEmpty())
            return Collections.emptyList();

        var apiCurrencies = new ArrayList<ApiCurrency>();

        currencies.forEach((code, currency) -> apiCurrencies.add(mapToApiCurrency(code,currency)));

        return apiCurrencies;
    }

    private static ApiCurrency mapToApiCurrency(String currencyIso, Currency currency) {
        var apiCurrency = new ApiCurrency();
        apiCurrency.setIsoCode(currencyIso);
        apiCurrency.setName(currency.getName());
        return apiCurrency;
    }


}

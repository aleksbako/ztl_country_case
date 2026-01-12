package com.ztl.test.service;

import com.ztl.test.domain.Country;
import com.ztl.test.domain.response.ApiCurrencyDetail;
import com.ztl.test.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class CurrencyService {

    @Autowired
    private CountryRepository countryRepository;

    public Map<String, ApiCurrencyDetail> getAllCountriesByCurrency() {

        var countries = countryRepository.getAllCountriesNameAndCurrencies();
        Map<String, ApiCurrencyDetail> currencyMap = new HashMap<>();

        for (Country country : countries) {

            if (country.getCurrencies() == null) continue;

            String countryName = country.getName().getCommonName();

            country.getCurrencies().forEach((code, currency) -> {

                ApiCurrencyDetail usage =
                        currencyMap.computeIfAbsent(code, k -> {
                            ApiCurrencyDetail c = new ApiCurrencyDetail();
                            c.setFullName(currency.getName());
                            c.setCountries(new ArrayList<>());
                            return c;
                        });

                usage.getCountries().add(countryName);
            });
        }
        return currencyMap;
    }
}

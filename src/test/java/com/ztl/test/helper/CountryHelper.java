package com.ztl.test.helper;

import com.ztl.test.domain.Country;
import com.ztl.test.domain.Currency;
import com.ztl.test.domain.Name;

import java.util.Map;

public class CountryHelper {
    public static Country getCountryMock(String name, Map<String, Currency> currencies) {
        Country c = new Country();
        Name n = new Name();
        n.setCommonName(name);
        c.setName(n);
        c.setCurrencies(currencies);
        return c;
    }

    public static Currency getCurrencyMock(String name) {
        Currency c = new Currency();
        c.setName(name);
        return c;
    }
}

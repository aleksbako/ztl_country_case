package com.ztl.test.helper;

import com.ztl.test.domain.response.ApiCurrencyDetail;

import java.util.List;

public class CurrencyHelper {
    public static ApiCurrencyDetail createCurrencyDetail(String fullName, List<String> countries) {
        ApiCurrencyDetail detail = new ApiCurrencyDetail();
        detail.setFullName(fullName);
        detail.setCountries(countries);
        return detail;
    }
}

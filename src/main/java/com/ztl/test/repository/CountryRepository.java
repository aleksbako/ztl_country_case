package com.ztl.test.repository;

import com.ztl.test.config.HostResolver;
import com.ztl.test.domain.Country;
import com.ztl.test.util.ZTLWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepository {
    private final String HOST = "restCountries";
    private final String GET_COUNTRIES_BY_REGION = "/v3.1/region/%s";
    private final String GET_ALL_COUNTRIES = "/v3.1/all?fields=name,currencies";

    @Autowired
    private HostResolver hostResolver;

    @Autowired
    private ZTLWebClient webClient;

    public List<Country> getRegionsCountryNameAndCurrencies(String region) {
        var endPoint = buildUrl(String.format(GET_COUNTRIES_BY_REGION, region)) + "?fields=name,currencies";
        return webClient.getEntityList(endPoint, null, Country.class);
    }

    public List<Country> getAllCountriesNameAndCurrencies() {
        var endPoint = buildUrl(GET_ALL_COUNTRIES) + "?fields=name,currencies";
        return webClient.getEntityList(endPoint, null, Country.class);
    }

    public String buildUrl(String endPoint) {
        return hostResolver.getHost(HOST).getHostUrl().concat(endPoint);
    }
}

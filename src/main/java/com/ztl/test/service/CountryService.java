package com.ztl.test.service;

import com.ztl.test.domain.Country;
import com.ztl.test.domain.SortOrder;
import com.ztl.test.domain.mapper.CountryMapper;
import com.ztl.test.domain.response.ApiCountry;
import com.ztl.test.exception.NotFoundProblem;
import com.ztl.test.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.ztl.test.util.StringUtil.stringComparator;

@Service
public class CountryService {

    @Autowired
    public CountryRepository countryRepository;

    public List<ApiCountry> getEuropeanCountryCurrencies(SortOrder orderBy) {

        var countries = countryRepository.getRegionsCountryNameAndCurrencies("europe");

        if (countries == null || countries.isEmpty())
            throw new NotFoundProblem("No European countries found");


        List<ApiCountry> apiCountries = countries.stream()
                .map(CountryMapper::mapToApiCountry)
                .toList();

        Comparator<ApiCountry> comparator =
                Comparator.comparing(
                        ApiCountry::getName,
                        stringComparator(orderBy)
                ).thenComparing(
                        c -> c.getCurrency().isEmpty()
                                ? null
                                : c.getCurrency().getFirst().getName(),
                        stringComparator(orderBy)
                );


        return apiCountries.stream()
                .sorted(comparator)
                .toList();
    }
}

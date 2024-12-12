package com.volvo.CountryInfoAPI.service;

import com.volvo.CountryInfoAPI.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface CountryService {
    Country getCountryInfo(String alpha3Code);

    Country extractCountryData(String countryData);

    Page<Country> getAllCountries(PageRequest pageRequest);
}


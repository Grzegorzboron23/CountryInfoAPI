package com.volvo.CountryInfoAPI.controller;

import com.volvo.CountryInfoAPI.model.Country;
import com.volvo.CountryInfoAPI.service.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{alpha3Code}")
    public ResponseEntity<Country> getCountry(@PathVariable String alpha3Code) {
        Country country = countryService.getCountryInfo(alpha3Code);

        if (country != null) {
            return ResponseEntity.ok(country);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/showAll")
    public ResponseEntity<Page<Country>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Country> countries = countryService.getAllCountries(PageRequest.of(page, size));
        if (countries.hasContent()) {
            return ResponseEntity.ok(countries);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}


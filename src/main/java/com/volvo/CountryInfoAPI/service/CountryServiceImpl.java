package com.volvo.CountryInfoAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volvo.CountryInfoAPI.exception.CountryNotFoundException;
import com.volvo.CountryInfoAPI.model.Country;
import com.volvo.CountryInfoAPI.repository.CountryRepository;
import com.volvo.CountryInfoAPI.utils.CountryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger logger = LogManager.getLogger(CountryServiceImpl.class);


    private final WebClient webClient;
    private final CountryRepository countryRepository;

    public CountryServiceImpl(WebClient countryWebClient, CountryRepository countryRepository) {
        this.webClient = countryWebClient;
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getCountryInfo(String alpha3Code) {
        try {
            String countryData = webClient.get()
                    .uri("/v3.1/alpha/" + alpha3Code)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Data received for country code {}: {}", alpha3Code, countryData);
            Country country = extractCountryData(countryData);
            countryRepository.save(country);
            return country;

        } catch (WebClientResponseException e) {
            logger.error("Error fetching country data: {}, Status code: {}", e);
            throw new CountryNotFoundException("Country with code " + alpha3Code + " not found");
        } catch (Exception e) {
            logger.error("Unexpected error while fetching country data: {}", e);
            throw new ServiceException("An unexpected error occurred while fetching country data");
        }
    }

    @Override
    public Country extractCountryData(String countryData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(countryData);
            JsonNode countryNode = root.get(0);

            Country country = new Country();
            country.setCommonName(CountryUtils.getTextField(countryNode, "name", "common"));
            country.setOfficialName(CountryUtils.getTextField(countryNode, "name", "official"));
            country.setRegion(countryNode.path("region").asText());
            country.setSubregion(countryNode.path("subregion").asText());
            country.setPopulation(countryNode.path("population").asLong());
            country.setCapital(CountryUtils.extractFirstFromArray(countryNode.path("capital")));
            country.setTimezones(CountryUtils.extractList(countryNode.path("timezones")));
            country.setBorders(CountryUtils.extractList(countryNode.path("borders")));
            country.setLanguages(CountryUtils.extractMap(countryNode.path("languages")));
            country.setCurrencies(extractCurrencies(countryNode.path("currencies")));

            return country;

        } catch (Exception e) {
            throw new ServiceException("Failed to parse country data: " + e.getMessage(), e);
        }
    }

    private Map<String, String> extractCurrencies(JsonNode currenciesNode) {
        Map<String, String> currencies = new HashMap<>();
        if (currenciesNode.isObject()) {
            currenciesNode.fields().forEachRemaining(entry -> {
                String currencyName = entry.getValue().path("name").asText(null);
                currencies.put(entry.getKey(), currencyName);
            });
        }
        return currencies;
    }

    @Override
    public Page<Country> getAllCountries(PageRequest pageRequest) {
        return countryRepository.findAll(pageRequest);
    }
}
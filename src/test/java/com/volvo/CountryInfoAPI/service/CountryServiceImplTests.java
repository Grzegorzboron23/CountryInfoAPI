package com.volvo.CountryInfoAPI.service;

import com.volvo.CountryInfoAPI.model.Country;
import com.volvo.CountryInfoAPI.repository.CountryRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CountryServiceImplTests {

    private static final String MOCK_COUNTRY_DATA = """
                [{
                    "name": {"common": "United States", "official": "United States of America"},
                    "region": "Americas",
                    "subregion": "Northern America",
                    "population": 331893745,
                    "capital": ["Washington D.C."],
                    "timezones": ["UTC-05:00"],
                    "borders": ["CAN", "MEX"],
                    "languages": {"eng": "English"},
                    "currencies": {"USD": {"name": "United States dollar", "symbol": "$"}}
                }]
            """;
    @Mock
    private WebClient webClient;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountryInfoWhenDataIsValid() {
        // Act
        Country country = countryService.extractCountryData(MOCK_COUNTRY_DATA);

        // Assert
        assertNotNull(country); // Ensure the returned object is not null
        assertEquals("United States", country.getCommonName());
        assertEquals("United States of America", country.getOfficialName());
        assertEquals("Americas", country.getRegion());
        assertEquals("Northern America", country.getSubregion());
        assertEquals("Washington D.C.", country.getCapital());
        assertEquals(331893745, country.getPopulation());
        assertEquals(List.of("UTC-05:00"), country.getTimezones());
        assertEquals(Map.of("eng", "English"), country.getLanguages());
        assertEquals(Map.of("USD", "United States dollar"), country.getCurrencies());
    }

    @Test
    void shouldReturnServiceExceptionWhenDataIsInvalid() {
        // Act & Assert
        assertThrows(ServiceException.class, () -> countryService.extractCountryData(null));
    }
}

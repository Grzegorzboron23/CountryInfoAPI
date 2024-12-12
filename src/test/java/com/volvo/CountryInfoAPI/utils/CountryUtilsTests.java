package com.volvo.CountryInfoAPI.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class CountryUtilsTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnTextFieldWhenValidNode() throws Exception {
        // Arrange
        String json = """
                    {
                        "name": {
                            "common": "U",
                            "official": "Uni"
                        }
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        String commonName = CountryUtils.getTextField(node, "name", "common");

        // Assert
        assertEquals("U", commonName);
    }

    @Test
    void shouldReturnNullWhenInvalidField() throws Exception {
        // Arrange
        String json = """
                    {
                        "name": {
                            "common": "U"
                        }
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        String officialName = CountryUtils.getTextField(node, "name", "official");

        // Assert
        assertNull(officialName);
    }

    @Test
    void shouldExtractFirstFromArrayWhenValidArray() throws Exception {
        // Arrange
        String json = """
                    {
                        "capital": ["W", "N"]
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        String firstCapital = CountryUtils.extractFirstFromArray(node.path("capital"));

        // Assert
        assertEquals("W", firstCapital);
    }

    @Test
    void shouldReturnNullWhenArrayIsEmpty() throws Exception {
        // Arrange
        String json = """
                    {
                        "capital": []
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        String firstCapital = CountryUtils.extractFirstFromArray(node.path("capital"));

        // Assert
        assertNull(firstCapital);
    }

    @Test
    void shouldExtractListWhenValidArray() throws Exception {
        // Arrange
        String json = """
                    {
                        "timezones": ["U", "U"]
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        List<String> timezones = CountryUtils.extractList(node.path("timezones"));

        // Assert
        assertEquals(List.of("U", "U"), timezones);
    }

    @Test
    void shouldReturnEmptyListWhenArrayIsEmpty() throws Exception {
        // Arrange
        String json = """
                    {
                        "timezones": []
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        List<String> timezones = CountryUtils.extractList(node.path("timezones"));

        // Assert
        assertTrue(timezones.isEmpty());
    }

    @Test
    void shouldExtractMapWhenValidObject() throws Exception {
        // Arrange
        String json = """
                    {
                        "languages": {
                            "e": "E",
                            "s": "S"
                        }
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        Map<String, String> languages = CountryUtils.extractMap(node.path("languages"));

        // Assert
        assertEquals(Map.of("e", "E", "s", "S"), languages);
    }

    @Test
    void shouldReturnEmptyMapWhenObjectIsEmpty() throws Exception {
        // Arrange
        String json = """
                    {
                        "languages": {}
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        Map<String, String> languages = CountryUtils.extractMap(node.path("languages"));

        // Assert
        assertTrue(languages.isEmpty());
    }

    @Test
    void shouldReturnEmptyMapWhenNodeIsNotObject() throws Exception {
        // Arrange
        String json = """
                    {
                        "languages": ["E", "S"]
                    }
                """;
        JsonNode node = objectMapper.readTree(json);

        // Act
        Map<String, String> languages = CountryUtils.extractMap(node.path("languages"));

        // Assert
        assertTrue(languages.isEmpty());
    }
}

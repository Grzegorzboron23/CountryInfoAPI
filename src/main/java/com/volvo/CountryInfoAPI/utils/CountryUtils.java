package com.volvo.CountryInfoAPI.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryUtils {

    public static String getTextField(JsonNode node, String parentField, String childField) {
        return node.path(parentField).path(childField).asText(null);
    }

    public static String extractFirstFromArray(JsonNode arrayNode) {
        if (arrayNode.isArray() && !arrayNode.isEmpty()) {
            return arrayNode.get(0).asText();
        }
        return null;
    }

    public static List<String> extractList(JsonNode arrayNode) {
        List<String> list = new ArrayList<>();
        arrayNode.forEach(item -> list.add(item.asText()));
        return list;
    }

    public static Map<String, String> extractMap(JsonNode objectNode) {
        Map<String, String> map = new HashMap<>();
        if (objectNode.isObject()) {
            objectNode.fields().forEachRemaining(entry -> map.put(entry.getKey(), entry.getValue().asText()));
        }
        return map;
    }
}

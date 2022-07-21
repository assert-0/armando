package com.mindsmiths.mitems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.sdk.core.db.DataUtils;
import com.mindsmiths.sdk.utils.serialization.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class Mitems {

    public static String getText(String elementSlug) {
        Element element = getElement(elementSlug);
        assert element.getType() == ElementType.TEXT;
        return element.getContent();
    }

    public static Option[] getOptions(String elementSlug) {
        Element element = getElement(elementSlug);
        assert element.getType() == ElementType.OPTIONS;
        try {
            return Mapper.getReader(Option[].class).readValue(element.getContent());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> getMapping(String elementSlug) {
        Element element = getElement(elementSlug);
        assert element.getType() == ElementType.OPTIONS;
        Map<String, String> mapping = new HashMap<>();
        try {
            Option[] options = Mapper.getReader(Option[].class).readValue(element.getContent());
            for (Option option : options)
                mapping.put(option.getId(), option.getText());
        } catch (IOException e) {
            throw new RuntimeException("Failed to get mapping: " + elementSlug, e);
        }
        return mapping;
    }

    public static JsonNode getJson(String elementSlug) {
        Element element = getElement(elementSlug);
        assert element.getType() == ElementType.JSON;
        try {
            return Mapper.getMapper().readTree(element.getContent());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Flow getFlow(String slug) {
        return DataUtils.get(eq("slug", slug), Flow.class);
    }

    public static Item getItem(String slug) {
        Flow flow = DataUtils.get(eq("items.slug", slug), Flow.class);
        if (flow == null)
            throw new RuntimeException("Item does not exist: " + slug);
        for (Item item : flow.getItems()) {
            if (item.getSlug().equals(slug))
                return item;
        }
        throw new RuntimeException("Item does not exist: " + slug);
    }

    public static Element getElement(String slug) {
        Flow flow = DataUtils.get(eq("items.elements.slug", slug), Flow.class);
        if (flow == null)
            throw new RuntimeException("Element does not exist: " + slug);

        for (Item item : flow.getItems()) {
            for (Element element : item.getElements()) {
                if (element.getSlug().equals(slug))
                    return element;
            }
        }
        throw new RuntimeException("Element does not exist: " + slug);
    }
}

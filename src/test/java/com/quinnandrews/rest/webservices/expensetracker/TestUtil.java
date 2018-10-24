package com.quinnandrews.rest.webservices.expensetracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    public static final String EXAMPLE_NAME = "Example Name";
    public static final String EXAMPLE_NAME_MODIFIED = "Example Name (Modified)";
    public static final String EXAMPLE_NAME_GREATER_THAN_32 = "This Object Name has 34 Characters";

    public static byte[] convertObjectToJsonBytes(final Object object) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static <T> T convertJsonStringToObject(final String json, final Class<T> objectClass) throws IOException {
        return new ObjectMapper().readValue(json, objectClass);
    }

}

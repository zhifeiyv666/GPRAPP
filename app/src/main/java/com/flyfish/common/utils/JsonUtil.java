package com.flyfish.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static JsonUtil instance = null;

    private ObjectMapper objectMapper;

    public static JsonUtil getInstance() {
        if(instance == null){
            synchronized (JsonUtil.class) {
                if(instance == null) {
                    instance = new JsonUtil();
                }
            }
        }
        return instance;
    }

    private JsonUtil() {
        objectMapper = new ObjectMapper();
    }

    public String serialozeToString(Object obj) {
        if(obj == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // do nothing
        }
        return null;
    }

    public Object deserialize(TypeReference typeReference, String jsonString) {
        if(jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonString,typeReference);
        } catch (IOException e) {
            // do nothing
        }
        return null;
    }
}

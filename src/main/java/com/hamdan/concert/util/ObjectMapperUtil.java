package com.hamdan.concert.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ObjectMapperUtil {
    private ObjectMapperUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static class LazyHolder {
        static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                .build()
                .findAndRegisterModules();
    }

    public static ObjectMapper getObjectMapper() {
        return LazyHolder.OBJECT_MAPPER;
    }

    public static <T> T convertValue(Object fromValue, TypeReference<?> toValueTypeRef) {
        T result = null;
        try {
            result = (T) LazyHolder.OBJECT_MAPPER.convertValue(fromValue, toValueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T convertValue(Object fromValue, Class<T> toClazz) {
        T result = null;
        try {
            result = LazyHolder.OBJECT_MAPPER.convertValue(fromValue, toClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

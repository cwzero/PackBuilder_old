package com.liquidforte.packbuilder.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Provider;

import javax.ws.rs.ext.ContextResolver;

public class ObjectMapperProvider implements Provider<ObjectMapper>, ContextResolver<ObjectMapper> {
    @Override
    public ObjectMapper get() {
        ObjectMapper result = new ObjectMapper();
        initMapper(result);
        return result;
    }

    private void initMapper(ObjectMapper mapper) {
        // TODO: Implement
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return get();
    }
}

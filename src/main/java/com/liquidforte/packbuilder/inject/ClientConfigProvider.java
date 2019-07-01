package com.liquidforte.packbuilder.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ext.ContextResolver;

public class ClientConfigProvider implements Provider<ClientConfig> {
    private ContextResolver<ObjectMapper> mapperProvider;

    @Inject
    public void setMapperProvider(ContextResolver<ObjectMapper> mapperProvider) {
        this.mapperProvider = mapperProvider;
    }

    @Override
    public ClientConfig get() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        clientConfig.register(mapperProvider);
        return clientConfig;
    }
}
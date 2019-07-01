package com.liquidforte.packbuilder.inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.liquidforte.packbuilder.PackBuilder;

public class PackBuilderModule extends AbstractModule {
	protected void configure() {
		bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);
		bind(new TypeLiteral<ContextResolver<ObjectMapper>>() {
		}).to(ObjectMapperProvider.class);

		bind(Client.class).toProvider(JerseyClientProvider.class);
		bind(ClientConfig.class).toProvider(ClientConfigProvider.class);

		bind(PackBuilder.class);
	}
}

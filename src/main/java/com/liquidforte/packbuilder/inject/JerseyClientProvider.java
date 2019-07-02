package com.liquidforte.packbuilder.inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JerseyClientProvider implements Provider<Client> {
	private ClientConfig clientConfig;

	@Inject
	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Client get() {
		Client result = ClientBuilder.newClient(clientConfig);
		return result;
	}
}

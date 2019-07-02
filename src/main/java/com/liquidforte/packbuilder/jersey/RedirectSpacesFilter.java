package com.liquidforte.packbuilder.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class RedirectSpacesFilter implements ClientResponseFilter {	
	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		/*if (responseContext.getStatusInfo().getFamily() != Response.Status.Family.REDIRECTION)
			return;
			*/

		Response resp;
		try {
			URI u = responseContext.getLocation();
			if (u == null) {
				return;
			}
			String uri = responseContext.getLocation().toString();
			String adjusted = uri.replace(' ', '+');
			System.out.println("URI: " + uri);
			System.out.println("Adjusted: " + adjusted);
			resp = requestContext.getClient()
					.target(new URI(adjusted)).request()
					.method(requestContext.getMethod());

			responseContext.setEntityStream((InputStream) resp.getEntity());
			responseContext.setStatusInfo(resp.getStatusInfo());
			responseContext.setStatus(resp.getStatus());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

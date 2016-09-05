package org.learnings.digitalOcean;

import org.learnings.system.restclient.CsaConnector;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class DigitalOceanConnectorFactory {
	
	public static DigitalOceanConnector getConnector(String fqdn, String port) {
		
		 RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint("http://" + fqdn + ":" + port)
		        .setConverter(new JacksonConverter(new ObjectMapper()))
		        .build();

        DigitalOceanConnector service = restAdapter.create(DigitalOceanConnector.class);
        return service;
	}
}

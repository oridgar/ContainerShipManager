package org.learnings.system.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class CsaConnectorFactory {
	
	
	public static CsaConnector getConnector(String fqdn, String port) {
		
		 RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint("http://" + fqdn + ":" + port)
		        .setConverter(new JacksonConverter(new ObjectMapper()))
		        .build();

         CsaConnector service = restAdapter.create(CsaConnector.class);
         return service;
	}
	
	
}

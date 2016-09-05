package org.learnings.digitalOcean;

import org.learnings.libs.RegisterCsaDO;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;

public interface DigitalOceanConnector {

	
	  @POST("/csa/register")
	  Response register(@Body RegisterCsaDO message);
	  
	  @DELETE("/csa/unregister")
	  Response unregister();
}

package org.learnings.system.restclient;

import java.util.List;

import org.learnings.libs.RegisterCsaDO;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;

public interface CsaConnector {
	  @POST("/csa/register")
	  Response register(@Body RegisterCsaDO message);
	  
	  @POST("/csa/unregister")
	  Response unregister(@Body RegisterCsaDO message);
}

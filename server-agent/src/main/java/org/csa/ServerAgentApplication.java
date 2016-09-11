package org.csa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class ServerAgentApplication extends SpringBootServletInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(ServerAgentApplication.class, args);
	}

	
}

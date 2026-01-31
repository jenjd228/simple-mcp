package com.ai.simple_mcp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Simple MCP API", version = "1.0", description = "API для принятия решений"))
public class SimpleMcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleMcpApplication.class, args);
	}

}

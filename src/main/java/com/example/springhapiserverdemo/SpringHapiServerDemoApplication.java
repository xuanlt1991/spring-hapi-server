package com.example.springhapiserverdemo;

import com.example.springhapiserverdemo.rest.FhirRestfulServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringHapiServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHapiServerDemoApplication.class, args);
	}

	@Autowired
	private ApplicationContext context;

	@Bean
	public ServletRegistrationBean ServletRegistrationBean() {
		ServletRegistrationBean registration= new ServletRegistrationBean(new FhirRestfulServer(context),"/*");
		registration.setName("FhirServlet");
		return registration;
	}
}

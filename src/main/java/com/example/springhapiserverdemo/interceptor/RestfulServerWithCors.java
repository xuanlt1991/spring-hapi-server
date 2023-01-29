package com.example.springhapiserverdemo.interceptor;

import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.CorsInterceptor;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/fhir/*"}, displayName = "FHIR Server")
public class RestfulServerWithCors extends RestfulServer {
    @Override
    protected  void  initialize() throws ServletException {
        // ... define your resource providers here ...
        // Define your CORS configuration. This is an example
        // showing a typical setup. You should customize this
        // to your specific needs
        CorsConfiguration  config = new CorsConfiguration();
        config.addAllowedHeader("x-fhir-starter");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Content-Type");
        config.addAllowedOrigin("*");
        config.addExposedHeader("Location");
        config.addExposedHeader("Content-Location");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // Create the interceptor and register it
        CorsInterceptor  interceptor = new CorsInterceptor(config);
        registerInterceptor(interceptor);
    }
}
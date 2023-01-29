package com.example.springhapiserverdemo.rest;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.openapi.OpenApiInterceptor;
import ca.uhn.fhir.rest.server.RestfulServer;
import com.example.springhapiserverdemo.provider.PatientProvider;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.Collections;

/*@WebServlet("/*")*/
@WebServlet(urlPatterns = { "/fhir/*" }, displayName = "FHIR Server")
public class FhirRestfulServer extends RestfulServer {

    private ApplicationContext applicationContext;

    public FhirRestfulServer(ApplicationContext context) {

        this.applicationContext = context;
    }

    @Override
    protected void initialize() throws ServletException {
        super.initialize();
        setFhirContext(FhirContext.forR4());
        setResourceProviders(Collections.singletonList(applicationContext.getBean(PatientProvider.class)));
        OpenApiInterceptor openApiInterceptor = new OpenApiInterceptor();
        registerInterceptor(openApiInterceptor);
    }
}
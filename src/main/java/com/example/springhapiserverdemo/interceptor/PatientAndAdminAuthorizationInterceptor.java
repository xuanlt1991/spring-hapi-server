package com.example.springhapiserverdemo.interceptor;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import org.hl7.fhir.r4.model.IdType;

import java.util.List;

public class PatientAndAdminAuthorizationInterceptor extends AuthorizationInterceptor {

    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {

        // Process authorization header - The following is a fake
        // implementation. Obviously we'd want something more real
        // for a production scenario.
        //
        // In this basic example we have two hardcoded bearer tokens,
        // one which is for a user that has access to one patient, and
        // another that has full access.
        IdType userIdPatientId = null;
        String authHeader = theRequestDetails.getHeader("Authorization");
        if (isNormalUser(authHeader)) {
            // This user has only access to the Patient resource with id 1.
            // If the user is a specific patient, we create the following rule chain:
            // Allow the user to read anything in their own patient compartment
            // Allow the user to write anything in their own patient compartment
            // If a client request doesn't pass either of the above, deny it
            userIdPatientId = new IdType("Patient", 1L);
            return new RuleBuilder()
                    .allow().read().allResources().inCompartment("Patient", userIdPatientId).andThen()
                    .allow().write().allResources().inCompartment("Patient", userIdPatientId).andThen()
                    .denyAll()
                    .build();
        } else if (isAdmin(authHeader)) {
            // If the user is an admin, allow everything
            return new RuleBuilder()
                    .allowAll()
                    .build();
        } else {
            // Throw an HTTP 401
            throw new AuthenticationException("Missing or invalid Authorization header value");
        }
    }

    private boolean isNormalUser(String authHeader){
        return "Bearer dfw98h38r".equals(authHeader);

    }
    private boolean isAdmin(String authHeader){
        return "Bearer 39ff939jgg".equals(authHeader);
    }

}

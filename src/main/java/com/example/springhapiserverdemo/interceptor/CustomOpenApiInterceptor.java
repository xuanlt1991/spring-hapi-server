package com.example.springhapiserverdemo.interceptor;

/*-
 * #%L
 * hapi-fhir-server-openapi
 * %%
 * Copyright (C) 2014 - 2023 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.rest.openapi.OpenApiInterceptor;

public class CustomOpenApiInterceptor extends OpenApiInterceptor {

    public static final String DHM_PNG = "dhm.png";

    /**
     * Constructor
     */
    public CustomOpenApiInterceptor() {
        super();
        setBannerImage(DHM_PNG);
        addResourcePathToClasspath("/swagger-ui/" + DHM_PNG, "/dhm.png");
        System.out.println("Call here");
    }

}
package com.platform.api.client;

import com.platform.core.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

/**
 * ApiClient — core HTTP client wrapper around REST Assured.
 * Environment routing, auth injection, and response extraction in one place.
 */
public class ApiClient {

    private static final Logger log = LogManager.getLogger(ApiClient.class);

    static {
        RestAssured.baseURI = ConfigManager.getInstance().getApiBaseUrl();
        log.info("ApiClient base URI: {}", RestAssured.baseURI);
    }

    private RequestSpecification baseSpec() {
        return given()
                .contentType("application/json")
                .accept("application/json");
    }

    private RequestSpecification authenticatedSpec() {
        String token = ConfigManager.getInstance().get("api.auth.token");
        return baseSpec().header("Authorization", "Bearer " + token);
    }

    public Response get(String endpoint) {
        log.info("GET {}", endpoint);
        return authenticatedSpec().get(endpoint).then().extract().response();
    }

    public Response post(String endpoint, Object body) {
        log.info("POST {}", endpoint);
        return authenticatedSpec().body(body).post(endpoint).then().extract().response();
    }

    public Response put(String endpoint, Object body) {
        log.info("PUT {}", endpoint);
        return authenticatedSpec().body(body).put(endpoint).then().extract().response();
    }

    public Response delete(String endpoint) {
        log.info("DELETE {}", endpoint);
        return authenticatedSpec().delete(endpoint).then().extract().response();
    }
}

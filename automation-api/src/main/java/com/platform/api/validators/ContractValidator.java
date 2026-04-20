package com.platform.api.validators;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * ContractValidator — validates API response status, headers, body, and JSON schema.
 */
public final class ContractValidator {

    private static final Logger log = LogManager.getLogger(ContractValidator.class);

    private ContractValidator() {}

    public static void assertStatus(Response response, int expectedStatus) {
        int actual = response.getStatusCode();
        assertThat("HTTP status mismatch", actual, equalTo(expectedStatus));
        log.info("Status {} ✓", actual);
    }

    public static void assertSchema(Response response, String schemaClasspath) {
        log.info("Validating schema: {}", schemaClasspath);
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaClasspath));
        log.info("Schema validation passed ✓");
    }

    public static void assertResponseTime(Response response, long maxMillis) {
        long actual = response.getTime();
        assertThat("Response time exceeded " + maxMillis + "ms", actual, lessThan(maxMillis));
        log.info("Response time {}ms ✓", actual);
    }

    public static void assertBodyContains(Response response, String jsonPath, Object expectedValue) {
        response.then().assertThat().body(jsonPath, equalTo(expectedValue));
        log.info("Body assertion [{}={}] ✓", jsonPath, expectedValue);
    }
}

package com.platform.api.builders;

import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * RequestBuilder — fluent builder for constructing typed HTTP requests.
 * Use for complex requests needing custom headers, params, or auth schemes.
 */
public class RequestBuilder {

    private static final Logger log = LogManager.getLogger(RequestBuilder.class);

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> params  = new HashMap<>();
    private Object body;
    private String contentType = "application/json";

    public RequestBuilder header(String key, String value) {
        headers.put(key, value); return this;
    }

    public RequestBuilder param(String key, Object value) {
        params.put(key, value); return this;
    }

    public RequestBuilder body(Object payload) {
        this.body = payload; return this;
    }

    public RequestBuilder contentType(String type) {
        this.contentType = type; return this;
    }

    public RequestSpecification build() {
        RequestSpecification spec = given().contentType(contentType).accept("application/json");
        headers.forEach(spec::header);
        if (!params.isEmpty()) spec.queryParams(params);
        if (body != null) spec.body(body);
        log.debug("Request built — headers: {} params: {}", headers.keySet(), params.keySet());
        return spec;
    }
}

package com.platform.tests.regression;

import com.platform.api.client.ApiClient;
import com.platform.api.client.BaseApiTest;
import com.platform.api.validators.ContractValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * SampleRegressionTest — cross-channel regression tests.
 * Place tests here that span UI + API concerns (e.g., end-to-end flows).
 */
public class SampleRegressionTest extends BaseApiTest {

    private final ApiClient apiClient = new ApiClient();

    @Test(groups = {"regression", "e2e"},
          description = "E2E: create user via API and verify it persists")
    public void verifyUserCreationEndToEnd() {
        String payload = "{\"name\":\"E2E User\",\"email\":\"e2e@test.com\",\"role\":\"qa\"}";
        Response create = apiClient.post("/users", payload);
        ContractValidator.assertStatus(create, 201);
        String userId = create.jsonPath().getString("id");
        Response fetch = apiClient.get("/users/" + userId);
        ContractValidator.assertStatus(fetch, 200);
        ContractValidator.assertBodyContains(fetch, "email", "e2e@test.com");
        log.info("E2E user creation verified — id: {}", userId);
    }
}

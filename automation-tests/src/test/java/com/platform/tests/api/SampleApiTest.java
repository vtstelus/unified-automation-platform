package com.platform.tests.api;

import com.platform.api.client.ApiClient;
import com.platform.api.client.BaseApiTest;
import com.platform.api.validators.ContractValidator;
import com.platform.core.data.providers.DataProviderUtil;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * SampleApiTest — API smoke and data-driven tests.
 * Extend BaseApiTest; use ApiClient and ContractValidator — no raw RestAssured in tests.
 */
public class SampleApiTest extends BaseApiTest {

    private final ApiClient apiClient = new ApiClient();

    @Test(groups = {"smoke", "api"},
          description = "Verify /health endpoint returns 200")
    public void verifyHealthEndpoint() {
        Response response = apiClient.get("/health");
        ContractValidator.assertStatus(response, 200);
        ContractValidator.assertResponseTime(response, 3000L);
    }

    @Test(groups = {"regression", "api"},
          description = "Verify /users endpoint returns 200 and valid schema")
    public void verifyUsersEndpointSchema() {
        Response response = apiClient.get("/users");
        ContractValidator.assertStatus(response, 200);
        ContractValidator.assertSchema(response, "schemas/user-schema.json");
    }

    @Test(groups = {"regression", "api"}, dataProvider = "userIds",
          description = "Data-driven: verify each user ID returns 200")
    public void verifyUserById(String userId, String expectedUsername) {
        Response response = apiClient.get("/users/" + userId);
        ContractValidator.assertStatus(response, 200);
        ContractValidator.assertBodyContains(response, "username", expectedUsername);
        log.info("User {} verified: {}", userId, expectedUsername);
    }

    @DataProvider(name = "userIds", parallel = true)
    public Object[][] userIds() {
        return DataProviderUtil.readCsv("testdata/csv/users.csv");
    }
}

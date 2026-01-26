package webapiautomation.api.client;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApiClient {

    protected RequestSpecification baseRequest() {
        return RestAssured.given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json");
    }
}

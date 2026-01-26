package webapiautomation.api.steps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiStepDefinitions {
    private Response lastResponse;
private String createdUserId;

@When("client sends GET user list")
public void client_sends_get_user_list() {
    lastResponse = RestAssured.given()
        .baseUri("https://dummyapi.io/data/v1")
        .header("app-id", "63a804408eb0cb069b57e43a")
        .get("/user");
}

@When("client creates a new user with random email")
public void client_creates_new_user_random_email() {
    String email = "auto_" + UUID.randomUUID() + "@example.com";
    Map<String,Object> body = new HashMap<>();
    body.put("firstName", "Auto");
    body.put("lastName", "User");
    body.put("email", email);

    lastResponse = RestAssured.given()
        .baseUri("https://dummyapi.io/data/v1")
        .header("app-id", "63a804408eb0cb069b57e43a")
        .contentType("application/json")
        .body(body)
        .post("/user/create");

    createdUserId = lastResponse.jsonPath().getString("id");
}

@Given("an existing user created via API")
public void an_existing_user_created_via_api() {
    client_creates_new_user_random_email();
}

@When("client updates that user lastname to {string}")
public void client_updates_that_user_lastname_to(String lastName) {
    Map<String,Object> body = new HashMap<>();
    body.put("lastName", lastName);

    lastResponse = RestAssured.given()
        .baseUri("https://dummyapi.io/data/v1")
        .header("app-id", "63a804408eb0cb069b57e43a")
        .contentType("application/json")
        .body(body)
        .put("/user/" + createdUserId);
}

@When("client deletes that user")
public void client_deletes_that_user() {
    lastResponse = RestAssured.given()
        .baseUri("https://dummyapi.io/data/v1")
        .header("app-id", "63a804408eb0cb069b57e43a")
        .delete("/user/" + createdUserId);
}

@When("client sends GET user by id {string}")
public void client_sends_get_user_by_id(String id) {
    lastResponse = RestAssured.given()
        .baseUri("https://dummyapi.io/data/v1")
        .header("app-id", "63a804408eb0cb069b57e43a")
        .get("/user/" + id);
}

@Then("response status code should be {int}")
public void response_status_code_should_be(Integer statusCode) {
    assertThat(lastResponse.getStatusCode()).isEqualTo(statusCode);
}

@Then("response body should contain field {string}")
public void response_body_should_contain_field(String field) {
    assertThat(lastResponse.jsonPath().getString(field)).isNotNull();
 }
}


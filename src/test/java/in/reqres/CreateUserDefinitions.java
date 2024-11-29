package in.reqres;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class CreateUserDefinitions {
    /**
     * Represents the response from the API after creating a user.
     */
    private Response response;

    // Create a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(CreateUserDefinitions.class);

    /**
     * Creates a new user with the given name and job.
     *
     * @param name The name of the user to create
     * @param job The job of the user to create
     */
    @When("I create a user with name {string} and job {string}")
    public void createUser(String name, String job) {
        try {
            // Set the base URI for the API
            RestAssured.baseURI = "https://reqres.in/api";

            // Create a new user with the given name and job
            response = given()
                    .header("Content-Type", "application/json")
                    .body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
                    .when()
                    .post("/users")
                    .then()
                    .extract()
                    .response();
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    /**
     * Validates that the response status matches the given status code.
     *
     * @param statusCode The expected status code
     */
    @Then("the response status should be {int}")
    public void validateResponseStatus(int statusCode) {
        response.then().statusCode(statusCode);
    }

    /**
     * Validates that the response contains the expected name, job, and createdAt fields.
     *
     * @param name The expected name
     * @param job The expected job
     */
    @Then("the response should have name {string}, job {string}, and a createdAt field")
    public void validateResponse(String name, String job) {
        try {
            Assert.assertEquals(response.jsonPath().getString("name"), name);
            Assert.assertEquals(response.jsonPath().getString("job"), job);
            Assert.assertNotNull(response.jsonPath().getString("createdAt"));
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    /**
     * Validates that the error message contains the given text.
     *
     * @param errorMessage The expected error message text
     */
    @Then("the error message should contain {string}")
    public void validateErrorMessage(String errorMessage) {
        try {
            Assert.assertTrue(response.getBody().asString().contains(errorMessage));
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }
}

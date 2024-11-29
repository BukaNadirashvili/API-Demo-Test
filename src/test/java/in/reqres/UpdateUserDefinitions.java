package in.reqres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.an.E;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class UpdateUserDefinitions {
    private String endpoint;  // API endpoint URL
    private Response response; // Response object to store API responses
    private Map<String, String> requestBody; // Map to hold request data

    // Create a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(CreateUserDefinitions.class);

    // Sets the API endpoint
    @Given("the API endpoint is {string}")
    public void setApiEndpoint(String url) {
        this.endpoint = url;
    }

    // Sends a PUT request with the given data
    @When("I send a PUT request with the following data:")
    public void sendPutRequest(DataTable dataTable) {
        try {
            // Convert DataTable to a list of maps
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

            // Assuming you only need the first map for the request
            Map<String, String> requestData = data.get(0); // Change this if you need to handle multiple rows

            // Convert the Map to a JSON string
            String requestBody;
            requestBody = new ObjectMapper().writeValueAsString(requestData);


            // System.out.println("Request Body: " + requestBody);
            // Send the PUT request
            response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body(requestBody) // Attach the JSON request body
                    .when()
                    .put(endpoint); // Perform the PUT request on the specified endpoint

            // Log the response for debugging
            // System.out.println("Status Code: " + response.getStatusCode());
            // System.out.println("Response: " + response.asString());
            // System.out.println("Response Headers: " + response.getHeaders());
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    // Sends a PATCH request with the given data
    @When("I send a PATCH request with the following data:")
    public void sendPatchRequest(DataTable dataTable) {
        try {
            // Convert DataTable to a list of maps
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

            // Assuming you only need the first map for the request
            Map<String, String> requestData = data.get(0); // Change this if you need to handle multiple rows

            // Convert the Map to a JSON string
            String requestBody;
            requestBody = new ObjectMapper().writeValueAsString(requestData);


            // System.out.println("Request Body: " + requestBody);

            response = RestAssured.given()
                    .header("Content-Type", "application/json") // Set content type
                    .body(requestBody) // Attach the request body
                    .when()
                    .patch(endpoint); // Send PATCH request
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    @Then("I receive a response with status code {int}")
    public void validateResponseStatusCode(int responseCode) {
        // Assert that the actual response status code matches the expected response code
        Assert.assertEquals(response.getStatusCode(), responseCode, "Status code does not match expected value.");

        // Optional: Print out the actual status code for debugging
        // System.out.println("Expected status code: " + responseCode + ", Actual status code: " + response.getStatusCode());
    }

    @Then("the response contains updated user data")
    public void responseContainsUpdatedUserData() {
        try {
            // Expected values for validation
            String expectedName = "John"; // Expected name in the response
            String expectedJob = "leader"; // Expected job in the response

            // Extract actual values from the response
            String actualName = response.jsonPath().getString("name");
            String actualJob = response.jsonPath().getString("job");

            // System.out.println("ActualName: " + actualName);
            // Assert that the actual values match the expected values
            Assert.assertEquals(actualName, expectedName, "The name in the response does not match the expected value.");
            Assert.assertEquals(actualJob, expectedJob, "The job in the response does not match the expected value.");

            // Optional: Validate presence of updatedAt field
            String updatedAt = response.jsonPath().getString("updatedAt");
            Assert.assertNotNull(updatedAt, "The updatedAt field should not be null.");

            // Optional: Print out the extracted values for debugging
            // System.out.println("Expected Name: " + expectedName + ", Actual Name: " + actualName);
            // System.out.println("Expected Job: " + expectedJob + ", Actual Job: " + actualJob);
            // System.out.println("Updated At: " + updatedAt);
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    @Then("the response contains an updatedAt field")
    public void verifyUpdatedAtField() {
        String updatedAt = response.jsonPath().getString("updatedAt"); // Extract updatedAt field
        Assert.assertNotNull(updatedAt); // Ensure updatedAt is not null
        // Optionally, validate the date format if necessary
    }

}
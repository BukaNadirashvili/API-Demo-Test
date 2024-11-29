package in.reqres;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ListOfUsersDefinitions {

    /**
     * Stores the URL of the API to interact with.
     */
    private URL apiUrl;

    /**
     * Stores the response from API calls.
     */
    private Response response;

    // Create a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(CreateUserDefinitions.class);

    /**
     * Sets up the API URL for testing.
     *
     * @param url The URL of the API to use for testing
     */
    @Given("I have a list of users with this api address {string}")
    public void iHaveAListOfUsersWithThisApiAddress(String url) {
        try {
            apiUrl = new URL(url);
        } catch (MalformedURLException e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
            throw new RuntimeException("Invalid API URL", e);
        }
    }

    /**
     * Sets up an invalid API URL for testing.
     *
     * @param url The invalid URL of the API to use for testing
     */
    @Given("I have used invalid api url {string}")
    public void iHaveUsedInvalidApiUrl(String url) {
        try {
            apiUrl = new URL(url);
        } catch (MalformedURLException e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
            throw new RuntimeException("Invalid API URL", e);
        }
    }

    /**
     * Sends a GET request to the API.
     */
    @When("I send request")
    public void iSendRequest() {
        try {
            response = RestAssured.get(apiUrl);
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    /**
     * Validates that the response status code matches the expected value.
     *
     * @param expected The expected status code
     */
    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expected) {
        Assert.assertEquals(response.statusCode(), expected);
    }

    /**
     * Validates that the users list is not empty.
     */
    @Then("the users list should not be empty")
    public void theUsersListShouldNotBeEmpty() {
        try {
            // Extract the "data" part of the JSON (which is an array of users)
            List<Map<String, Object>> users = response.jsonPath().getList("data");

            // Check if the "data" array is not empty
            Assert.assertFalse(users.isEmpty(), "User data array should not be empty");
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    /**
     * Validates that the user has the specified fields.
     *
     * @param id The expected ID
     * @param email The expected email
     * @param firstName The expected first name
     * @param lastName The expected last name
     * @param avatar The expected avatar
     */
    @Then("the user should have: {string}, {string}, {string}, {string} and {string}")
    public void theUserShouldHaveFields(String id,
                                        String email,
                                        String firstName,
                                        String lastName,
                                        String avatar) {

        try {
            // Send GET request and extract the response
            Response userResponse = RestAssured
                    .given()
                    .when()
                    .get(apiUrl)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            // Extract the "data" part of the JSON (which is an array of users)
            List<Map<String, Object>> users = userResponse.jsonPath().getList("data");

            // Check if the "data" array is not empty
            Assert.assertFalse(users.isEmpty(), "User data array should not be empty");

            // Iterate through each user in the array and check for fields
            for (Map<String, Object> user : users) {
                Assert.assertTrue(user.containsKey(id), "User should have an '" + id + "' field");
                Assert.assertTrue(user.containsKey(email), "User should have an '" + email + "' field");
                Assert.assertTrue(user.containsKey(firstName), "User should have a '" + firstName + "' field");
                Assert.assertTrue(user.containsKey(lastName), "User should have a '" + lastName + "' field");
                Assert.assertTrue(user.containsKey(avatar), "User should have a '" + avatar + "' field");

                // Optionally check that fields are not null or empty
                Assert.assertNotNull(user.get(id), "User '" + id + "' should not be null");
                Assert.assertNotNull(user.get(email), "User '" + email + "' should not be null");
                Assert.assertNotNull(user.get(firstName), "User '" + firstName + "' should not be null");
                Assert.assertNotNull(user.get(lastName), "User '" + lastName + "' should not be null");
                Assert.assertNotNull(user.get(avatar), "User '" + avatar + "' should not be null");

                Assert.assertFalse(user.get(id).toString().isEmpty(), "User '" + id + "' should not be empty");
                Assert.assertFalse(user.get(email).toString().isEmpty(), "User '" + email + "' should not be empty");
                Assert.assertFalse(user.get(firstName).toString().isEmpty(), "User '" + firstName + "' should not be empty");
                Assert.assertFalse(user.get(lastName).toString().isEmpty(), "User '" + lastName + "' should not be empty");
                Assert.assertFalse(user.get(avatar).toString().isEmpty(), "User '" + avatar + "' should not be empty");
            }
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }

    /**
     * Validates that the response status code does not match the expected value.
     *
     * @param expected The unexpected status code
     */
    @Then("the response code should not be {int}")
    public void theResponseCodeShouldNotBe(int expected) {
        Assert.assertNotEquals(response.statusCode(), expected);
    }

    /**
     * Validates that the response returns the expected total number of pages.
     *
     * @param totalPages The expected total number of pages
     */
    @Then("the response should return {int} pages")
    public void theResponseShouldReturnPages(int totalPages) {
        try {
            Assert.assertEquals(200, response.getStatusCode());
            int actualTotalPages = response.jsonPath().getInt("total_pages");
            Assert.assertEquals(actualTotalPages, totalPages, "Expected total pages to be " + actualTotalPages);
        } catch (Exception e) {
            // Log the exception message and stack trace as an ERROR
            logger.error("An error occurred: ", e);  // Logs the exception with stack trace
        }
    }
}
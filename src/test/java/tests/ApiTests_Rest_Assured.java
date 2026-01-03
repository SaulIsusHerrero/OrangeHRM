package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests_Rest_Assured {

    @BeforeAll
    static void beforeAll() {
        // Relax HTTPS validation (useful for self-signed certs; harmless here)
        RestAssured.useRelaxedHTTPSValidation();

        // Base configuration for all tests
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    /** POST HTTP method: Create a new POST */
    void testCreatePost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }")
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(201) // POST normally returns 201 code : created
                .body("id", notNullValue()) // New resource should have an ID
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1));
    }

    @Test
    /** GET HTTP method: Read a POST */
    void testGetPost() {
        given().log().all()
                .when()
                .get("/posts/1")
                .then().log().all()
                .statusCode(200) // GET normally returns 200 code : OK
                // Valid fields returned by /posts/1 on jsonplaceholder:
                // userId, id, title, body
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    /** PUT HTTP method: updates completely an existing POST */
    void testUpdatePost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"id\": 1, \"title\": \"Updated title\", \"body\": \"Updated Content\", \"userId\": 1 }")
                .when()
                .put("/posts/1")
                .then().log().all()
                .statusCode(200) // PUT normally returns 200 code : OK
                .body("id", equalTo(1))
                .body("title", equalTo("Updated title"))
                .body("body", equalTo("Updated Content"))
                .body("userId", equalTo(1));
    }

    @Test
    /** PATCH HTTP method: updates partially an existing POST */
    void testPatchPost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"title\": \"Partially updated title\" }") // Only updating the title
                .when()
                .patch("/posts/1") // Update the post with ID = 1
                .then().log().all()
                .statusCode(200) // PATCH normally returns 200 code : OK
                .body("id", equalTo(1)) // The ID remains the same
                .body("title", equalTo("Partially updated title")); // Validate the change
    }

    @Test
    /** DELETE HTTP method: deletes an existing POST */
    void testDeletePost() {
        given().log().all()
                .when()
                .delete("/posts/1") // Delete the post with ID = 1
                .then().log().all()
                .statusCode(200); // DELETE normally returns 200 code : OK
    }

    @Test
    /** Detect an invalid ID */
    void testGetPostInvalidId() {
        given().log().all()
                .when()
                .get("/posts/999999") // Using an ID that does not exist
                .then().log().all()
                .statusCode(404) // Expecting 404 Not Found
                .body(equalTo("{}")); // For jsonplaceholder, invalid IDs return an empty object
    }

}

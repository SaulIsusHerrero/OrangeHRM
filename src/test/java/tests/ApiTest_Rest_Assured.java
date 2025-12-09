
package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest_Rest_Assured {

    @BeforeAll
    static void beforeAll() {
        // Relax HTTPS validation (useful for self-signed certs; harmless here)
        RestAssured.useRelaxedHTTPSValidation();

        // Base configuration for all tests
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void testGetPost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                // Valid fields returned by /posts/1 on jsonplaceholder:
                // userId, id, title, body
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }
}

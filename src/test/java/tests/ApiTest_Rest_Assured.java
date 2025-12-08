package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest_Rest_Assured {

    @Test
    public void testGetPost() {
        // Base configuration
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("Username", nullValue())
                .body("title", notNullValue());
    }
}

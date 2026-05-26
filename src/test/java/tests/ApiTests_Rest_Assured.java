package tests;

/** Importaciones necesarias de RestAssured, JUnit 5 y Hamcrest para las aserciones (validación).*/

// Herramientas para enviar y validar peticiones HTTP.
import io.restassured.RestAssured;
// Se crea la estructura básica para los tests.
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
// Permite usar la palabra given() para empezar a construir y configurar la petición HTTP (como las cabeceras o el cuerpo del mensaje).
import static io.restassured.RestAssured.given;
// Permite usar la validación notNullValue() para comprobar y asegurar que un dato de la respuesta del servidor no venga vacío o nulo.
import static org.hamcrest.Matchers.notNullValue;
// Permite usar la validación equalTo() para comprobar que el dato devuelto por el servidor es exactamente igual al valor que se esperaba.
import static org.hamcrest.Matchers.equalTo;

public class ApiTests_Rest_Assured {
    // @BeforeAll indica que este metodo se ejecutará una sola vez antes de que comiencen todos los tests de esta clase. Es una configuración global.
    @BeforeAll
    static void beforeAll() {
        // Relax HTTPS validation (useful for self-signed certs; harmless here)
        RestAssured.useRelaxedHTTPSValidation();

        /** Establece la URL base global para todas las peticiones de esta clase. Es una URL pública para hacer pruebas de API.
        Y no hace falta volver a escribir la misma URL en cada uno de los tests.*/
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    /** POST HTTP metodo: Crea un nuevo POST, es decir, un nuevo registro en BBDDs si fuese un POST real, no en una API de pruebas o servicio de simulación*/
    void testCreatePost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }")
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(201) // POST normalmente retorna un código 201 : creado correctamente.
                .body("id", notNullValue()) // El nuevo recurso ha de tener un ID nuevo también.
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1));
    }

    @Test
    /** GET metodo HTTP : Lee un POST */
    void testGetPost() {
        given().log().all()
                .when()
                .get("/posts/1")
                .then().log().all()
                .statusCode(200) // GET normalmente retorna un  codigo 200 : OK
                // Se valida que los campos devueltos por el endpoint (URL específica de una API donde nos conectamos) /posts/1:
                .body("userId", notNullValue()) // Verificamos que el userId exista (no sea nulo).
                .body("id", equalTo(1)) // Confirmamos que nos devolvió exactamente el ID que solicitamos (1).
                .body("title", notNullValue()) // Verificamos que contenga un título.
                .body("body", notNullValue()); // Verificamos que contenga un cuerpo de texto.
    }

    @Test
    /** Metodo HTTP PUT: Actualiza de manera completa un POST/registro que ya existe */
    void testUpdatePost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"id\": 1, \"title\": \"Updated title\", \"body\": \"Updated Content\", \"userId\": 1 }")
                .when()
                .put("/posts/1")
                .then().log().all()
                .statusCode(200) // PUT normalmente retorna un codigo 200 : OK
                .body("id", equalTo(1))
                .body("title", equalTo("Updated title"))
                .body("body", equalTo("Updated Content"))
                .body("userId", equalTo(1));
    }

    @Test
    /** Metodo HTTP PATCH: actualiza de manera parcial un POST/registro que ya existe*/
    void testPatchPost() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{ \"title\": \"Partially updated title\" }") // Solamente actualizamos el título.
                .when()
                .patch("/posts/1") // Actualiza el post con ID = 1
                .then().log().all()
                .statusCode(200) // PATCH normalmente retorna codigo 200 : OK.
                .body("id", equalTo(1)) // El ID permanece con el mismo valor.
                .body("title", equalTo("Partially updated title")); // Valida el cambio realizado.
    }

    @Test
    /** Metodo HTTP DELETE: Elimina un POST/registro existente */
    void testDeletePost() {
        given().log().all()
                .when()
                .delete("/posts/1") // Elimina el post con ID = 1.
                .then().log().all()
                .statusCode(200); // DELETE normalmente retorna el codigo 200 : OK.
    }

    @Test
    /** Detect an invalid ID */
    void testGetPostInvalidId() {
        given().log().all()
                .when()
                .get("/posts/999999") // Se usa un ID que no existe.
                .then().log().all()
                .statusCode(404) // Se espera un 404 - Not Found.
                .body(equalTo("{}")); // Para jsonplaceholder, invalidos IDs retornan un objeto vacío.
    }

}

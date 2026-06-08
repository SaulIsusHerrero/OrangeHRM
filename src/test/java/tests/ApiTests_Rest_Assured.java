package tests;

/** Autor : Saúl Isús Herrero. Fecha : 03 JUNIO 2026.
 * Entorno : api de testing https://jsonplaceholder.typicode.com
 */


/** Importaciones necesarias de RestAssured, JUnit 5 y Hamcrest para las aserciones (validación).*/

// Herramientas para enviar y validar peticiones HTTP.
import io.restassured.RestAssured;
// Se crea la estructura básica para los tests.
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
// Permite usar la palabra given() para empezar a construir y configurar la petición HTTP, se dan las precondiciones.
import static io.restassured.RestAssured.given;
// Permite usar la validación notNullValue() para comprobar y asegurar que un dato en la respuesta del servidor no venga vacío o nulo.
import static org.hamcrest.Matchers.notNullValue;
// Permite usar la validación equalTo() para comprobar que el dato devuelto por el servidor es exactamente igual al valor que se esperaba.
import static org.hamcrest.Matchers.equalTo;

public class ApiTests_Rest_Assured {
    // @BeforeAll indica que este metodo se ejecutará una sola vez antes de que comiencen todos los tests de esta clase. Es una configuración global.
    @BeforeAll
    static void beforeAll() {
        /** Se usa para desactivar la validación de certificados SSL/HTTPS, permitiendo que RestAssured acepte conexiones seguras
        incluso si el certificado es inválido, autofirmado o no confiable. */
        RestAssured.useRelaxedHTTPSValidation();

        /** Establece la URL base global para todas las peticiones de esta clase. Es una URL pública para hacer pruebas de API.
        Y no hace falta volver a escribir la misma URL en cada uno de los tests.*/
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    /** POST HTTP metodo: Crea un nuevo POST, es decir, un nuevo registro en Base de Datos */
    void testCreatePost() {
        given().log().all() // precondiciones o contexto inicial.
                .header("Content-Type", "application/json")
                .body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }")
                .when() // acción de crear un post.
                .post("/posts") //el post se crea en esta url : url base + apendice :  https://jsonplaceholder.typicode.com/posts
                .then().log().all() // validación de la creación del post.
                .statusCode(201) // POST retorna un código 201 : creado correctamente. Si falla puede devolver un error 404 ó 5XX.
                .body("id", notNullValue()) // Se valida que el nuevo registro en la Base de Datos del servidor tenga un ID nuevo y correcto.
                .body("title", equalTo("foo")) // Se valida que el título sea correcto foo es una palabra usada cuando solo es petición de prueba no real.
                .body("body", equalTo("bar")) // Se valida que el body sea correcto y bar es también una palabra usada para pruebas.
                .body("userId", equalTo(1)); // Se valida que este campo contenga el valor esperado que es 1.
    }

    @Test
    /** GET metodo HTTP : Obtiene un POST */
    void testGetPost() {
        given().log().all() // Muestra en consola todos los detalles de la petición antes de hacer la acción de GET.
                .when()
                .get("/posts/1")// se realiza la accion de obtener un registro.
                .then().log().all() // Muestra en consola todos los detalles de la respuesta (status code, headers, body, etc.) después de hacer la acción de GET.
                // Valida que la respuesta sea correcta: código http 200.
                .statusCode(200)
                /**
                * Se valida que los campos devueltos por el endpoint (URL específica de una API donde nos conectamos) https://jsonplaceholder.typicode.com/posts/1
                * sean correctos. */
                .body("userId", notNullValue()) // Verificamos que el userId exista (no sea nulo).
                .body("id", equalTo(1)) // Confirmamos que nos devolvió exactamente el ID que solicitamos (1).
                .body("title", notNullValue()) // Verificamos que contenga un título.
                .body("body", notNullValue()); // Verificamos que contenga un cuerpo de texto.
    }

    @Test
    /** Metodo HTTP PUT: Actualiza de manera completa un POST/registro que ya existe */
    void testUpdatePost() {
        given().log().all() //configura la petición de PUT
                .header("Content-Type", "application/json") // Añade la cabecera para indicar que el cuerpo de la petición es en formato JSON.
                .body("{ \"id\": 1, \"title\": \"Updated title\", \"body\": \"Updated Content\", \"userId\": 1 }")
                .when() // realizamos la actualización completa del registro.
                .put("/posts/1")// actualizamos el post que tiene = 1.
                .then().log().all()
                .statusCode(200) // Valida que la respuesta sea correcta del PUT si es así retorna un código 200 : correcto.
                .body("id", equalTo(1)) // Valida que el ID del post actualizado siga siendo el mismo (1).
                .body("title", equalTo("Updated title")) // Valida que el título sea el indicado en la petición.
                .body("body", equalTo("Updated Content")) // Valida que el cuerpo sea el indicado en la petición.
                .body("userId", equalTo(1)); // Valida que el userId siga siendo el mismo (1) después de la actualización.
    }

    @Test
    /** Metodo HTTP PATCH: actualiza de manera parcial un POST/registro que ya existe*/
    void testPatchPost() {
        given().log().all() // configura la petición de PATCH.
                .header("Content-Type", "application/json") // Añade la cabecera para indicar que el cuerpo de la petición es en formato JSON.
                .body("{ \"title\": \"Partially updated title\" }") // Sólo actualizamos el título.
                .when()
                .patch("/posts/1") // Actualiza el post que tiene ID = 1.
                .then().log().all()
                .statusCode(200) // Valida que la respuesta sea correcta del PATCH si es así retorna un código 200 : correcto.
                .body("id", equalTo(1)) // Valida el ID permanezca con el mismo valor.
                .body("title", equalTo("Partially updated title")); // Valida el cambio que se ha realizado.
    }

    @Test
    /** Metodo HTTP DELETE: Elimina un POST/registro existente */
    void testDeletePost() {
        given().log().all() // muestra la petición de DELETE.
                .when() // realiza la acción de eliminar un post/registro.
                .delete("/posts/1") // Elimina el post con ID = 1.
                .then().log().all() // muestra el resultado de la acción.
                .statusCode(200); // Valida que la respuesta sea correcta y ,por tanto, devuelva un código 200.
    }

    @Test
    /** Detectar un ID inválido */
    void testGetPostInvalidId() {
        given().log().all() // Muestra la petición de obtener un post.
                .when() // Realiza la acción.
                .get("/posts/999999") // Intenta obtener un post con un ID 999999 que no existe. Por tanto, el registro no existe.
                .then().log().all()
                .statusCode(404) // Valida que la respuesta efectivamente devuelva un código 404 : no encontrado, lo que es correcto para un ID inválido.
                .body(equalTo("{}")); // Como ese registro no existe, se valida que su cuerpo sea vacío que es correcto.
    }

}

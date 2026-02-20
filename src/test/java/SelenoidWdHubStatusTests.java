import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class SelenoidWdHubStatusTests extends TestBase {

    @Test
    public void wdHubStatusValuesTest() {
        given().
                log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value", hasKey("message"))
                .body("value", hasKey("ready"));
    }

    @Test
    public void wdHubStatusMessageTest() {
        given().
                log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"));
    }

    @Test
    public void wdHubStatusReadinessTest() {
        given().
                log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    public void wdHubStatusJsonSchemaTest() {
        given().
                log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wdHub_schema.json"));
    }

    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void negativeUnauthorizedPasswordTest() {
        given()
                .log().all()
                .auth().basic("user1", "12345")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void negativeUnauthorizedUsernameTest() {
        given()
                .log().all()
                .auth().basic("user12", "12345")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }
}

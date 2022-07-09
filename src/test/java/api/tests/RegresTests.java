package api.tests;

import api.models.MainData;
import api.models.UserData;
import org.junit.jupiter.api.Test;

import static api.tests.Specs.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegresTests {

    String
            body = "{\"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"}",
            body2 = "{\"email\": \"sydney@fife\"}",
            body3 = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }",
            body4 = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }",
            name = "morpheus",
            job = "zion resident",
            job2 = "leader",
            email = "janet.weaver@reqres.in",
            url = "https://reqres.in/#support-heading",
            text = "To keep ReqRes free, contributions towards server costs are appreciated!",
            id = "2";


    @Test
    void unsuccessfulRegisterTest() {

        given()
                .spec(request)
                .body(body2)
                .when()
                .post("/register")
                .then()
                .spec(response400);
    }

    @Test
    void successfulRegisterTest() {
        given()
                .spec(request)
                .body(body3)
                .when()
                .post("/register")
                .then()
                .spec(response200);
    }

    @Test
    void getSingleUserTest() {
        MainData mainData =
                given()
                        .spec(request)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(MainData.class);
        assertEquals(email, mainData.getData().getEmail());
        assertEquals(id, mainData.getData().getId());
        assertEquals(url, mainData.getSupport().getUrl());
        assertEquals(text, mainData.getSupport().getText());

    }

    @Test
    void updateSingleUserTest() {
        UserData userData =
                given()
                        .spec(request)
                        .body(body4)
                        .when()
                        .put("/api/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(UserData.class);
        assertEquals(name, userData.getName());
        assertEquals(job, userData.getJob());
    }

    @Test
    void createSingleUserTest() {
        UserData userData =
                given()
                        .spec(request)
                        .body(body)
                        .when()
                        .post("/api/users")
                        .then()
                        .spec(response201)
                        .extract().as(UserData.class);
        assertEquals(name, userData.getName());
        assertEquals(job2, userData.getJob());

    }

}


package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.CourierDataLombok;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi{

    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(String login, String password) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(new CourierData(login, password))
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(requestSpecification())
                .when()
                .delete(CREATE_COURIER_URI + "/" + id)
                .then();
    }
}
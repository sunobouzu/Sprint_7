package tests;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CreateCourierData;
import model.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;

public class CreateCourierTest {

    private CourierApi courierApi;
    private int  courierId;
    private CreateCourierData courierData;


    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @After
    public void cleanUp() {
        if (courierData.getLogin() != null && courierData.getPassword() != null) {
            ValidatableResponse responseCourier = courierApi.loginCourier(courierData.getLogin(),courierData.getPassword());
            courierId = responseCourier.extract().response().path("id");
            courierApi.deleteCourier(courierId);
        }
    }

    @DisplayName("Создание курьера")
    @Test
    public void courierCanBeCreatedTest() {
        courierData = CourierGenerator.getRandomCourier();
        assertNotNull("Courier data should not be null", courierData);
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.log().all();
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @DisplayName("Тест на дублирование при создании курьера")
    @Test
    public void duplicateCourierCreationTest() {
        courierData = CourierGenerator.getRandomCourier();
        courierApi.createCourier(courierData);

        ValidatableResponse response = courierApi.createCourier(courierData);
        response.assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Создание курьера без логина")
    @Test
    public void courierCannotBeCreatedWithoutLogin() {
        courierData = CourierGenerator.getRandomCourier();
        courierData.setLogin(null);
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Создание курьера без пароля")
    @Test
    public void courierCannotBeCreatedWithoutPassword() {
        courierData = CourierGenerator.getRandomCourier();
        courierData.setPassword(null);
        ValidatableResponse response = courierApi.createCourier(courierData);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
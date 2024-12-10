package tests;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.CourierGenerator;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTest {

    private static CourierApi courierApi;
    private static int courierId;
    private static CourierData courierData;


    @BeforeClass
    public static void setUp() {
        courierApi = new CourierApi();
        courierData = CourierGenerator.getRandomCourier();
        courierApi.createCourier(courierData);
    }

    @AfterClass
    public static void cleanUp() {
            courierApi.deleteCourier(courierId);
    }

    @DisplayName("Логин в систему")
    @Test
    public void courierCanLogInTest() {
        ValidatableResponse response = courierApi.loginCourier(courierData.getLogin(), courierData.getPassword());
        response.assertThat()
                .statusCode(SC_OK)
                .body("id", is(notNullValue()));
        courierId = response.extract().response().path("id");
    }

    @DisplayName("Логин курьера с некорректным паролем")
    @Test
    public void courierCannotLoginWithIncorrectCredentialsTest() {
        ValidatableResponse response = courierApi.loginCourier(courierData.getLogin(), "wrongPassword");
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Логин курьера без логина")
    @Test
    public void courierCannotLoginWithoutRequiredLogin() {
        ValidatableResponse response = courierApi.loginCourier(null, "password");
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Логин курьера без пароля")
    @Test
    public void courierCannotLoginWithoutRequiredPassword() {
        ValidatableResponse response = courierApi.loginCourier("login", null);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Логин несуществующего пользователя")
    @Test
    public void courierCannotLogInAsNonExistentUserTest() {
        ValidatableResponse response = courierApi.loginCourier("nonExistentLogin", "password");
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}

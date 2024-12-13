package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderData;
import java.util.List;
import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String GET_ORDERS_URI = "/api/v1/orders";

    @Step("Создание заказа")
    public static Response createOrder(OrderData orderData) {
        return given()
                .spec(requestSpecification())
                .body(orderData)
                .post(CREATE_ORDER_URI);
    }

    @Step("Получение списка заказов")
    public static Response getOrders(int limit, int page) {
        return given()
                .spec(requestSpecification())
                .queryParam("limit", limit)
                .queryParam("page", page)
                .get(GET_ORDERS_URI);
    }
}
package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderData;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi{

    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";
    public static final String GET_ORDERS_URI = "/api/v1/orders";
    //create Order
    @Step("Создание заказа")
    public static Response createOrder(String firstName, String lastName, String address, int metroStation, String phone,
                                       int rentTime, String deliveryDate, String comment, List<String> colors) {
        // Создаем JSON объект для заказа
        OrderData orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        return given()
                .spec(requestSpecification())
                .body(orderData)
                .post(CREATE_ORDER_URI);
    }

    //cancel Order
    @Step("Отмена заказа")
    public Response cancelOrder(String track) {
        String body = "{\"track\": \"" + track + "\"}"; // Формируем тело запроса
        return given()
                .spec(requestSpecification())
                .body(body)
                .put(CANCEL_ORDER_URI);
    }

    //get List
    @Step("Получение списка заказов")
    public Response getOrders(int limit, int page) {
        return given()
                .spec(requestSpecification())
                .queryParam("limit", limit)
                .queryParam("page", page)
                .get(GET_ORDERS_URI);
    }
}

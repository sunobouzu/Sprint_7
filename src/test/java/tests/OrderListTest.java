package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderListTest {

    @Test
    @Description("Проверяем получение списка заказов")
    public void testGetOrderList() {
        Response response = OrderApi.getOrders(10, 0);
        assertEquals(SC_OK, response.getStatusCode());
        assertTrue("Ожидается, что тело ответа содержит список заказов", response.jsonPath().getList("orders") != null);
    }
}

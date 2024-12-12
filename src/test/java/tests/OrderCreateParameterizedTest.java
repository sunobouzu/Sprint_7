package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.OrderData;
import model.OrderGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTest {

    private final OrderData order;
    private final int expectedStatusCode;
    private String track;

    public OrderCreateParameterizedTest(OrderData order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {OrderGenerator.getOrderWithColor("BLACK"), SC_CREATED},
                {OrderGenerator.getOrderWithColor("GREY"), SC_CREATED},
                {OrderGenerator.getOrderWithColor(null), SC_CREATED},
                {OrderGenerator.getOrderWithColor(Arrays.asList("BLACK", "GREY")), SC_CREATED},
        });
    }

    @Test
    @Description("Проверяем создание заказа с разными набором параметров")
    public void testCreateOrderWithParameters() {
        Response response = OrderApi.createOrder(order);

        assertEquals(expectedStatusCode, response.getStatusCode());
        track = response.jsonPath().getString("track");
        assertNotNull("Трек заказа не должен быть null", track);
    }
}

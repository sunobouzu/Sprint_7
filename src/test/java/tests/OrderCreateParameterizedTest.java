package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.OrderData;
import model.OrderGenerator;
import org.junit.After;
import org.junit.Before;
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

    public OrderCreateParameterizedTest(OrderData order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { OrderGenerator.getRandomOrder(), SC_CREATED },
                { OrderGenerator.getRandomOrder(), SC_CREATED },
                { OrderGenerator.getRandomOrder(), SC_CREATED }
        });
    }



    @After
    public void cleanUp() {
        // Удаление созданных заказов, если это необходимо
    }

    @Test
    @Description("Проверяем создание заказа с данными из генератора")
    public void testCreateOrderWithParameters() {
        Response response = OrderApi.createOrder(
                order.getFirstName(),
                order.getLastName(),
                order.getAddress(),
                order.getMetroStation(),
                order.getPhone(),
                order.getRentTime(),
                order.getDeliveryDate(),
                order.getComment(),
                order.getColor()
        );


        assertEquals(expectedStatusCode, response.getStatusCode());
        if (expectedStatusCode == SC_CREATED) {
            assertNotNull("Трек заказа не должен быть null", response.jsonPath().getString("track"));
        } else {
            assertNull("При ошибочном статусе трек заказа должен быть null", response.jsonPath().getString("track"));
        }
    }
}

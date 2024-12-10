package model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class OrderGenerator {

    @Step("Генерация случайного заказа")
    public static OrderData getRandomOrder() {
        String firstName = RandomStringUtils.randomAlphabetic(8);
        String lastName = RandomStringUtils.randomAlphabetic(8);
        String address = RandomStringUtils.randomAlphanumeric(15);
        int metroStation = 1 + (int)(Math.random() * 10);
        String phone = "+7" + RandomStringUtils.randomNumeric(10);
        int rentTime = 1 + (int)(Math.random() * 7);
        LocalDateTime currentDate = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String deliveryDate = currentDate.format(formatter);
        String comment = RandomStringUtils.randomAlphabetic(15);
        List<String> colors = Arrays.asList("BLACK", "GREY");

        return new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
    }
}

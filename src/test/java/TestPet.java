import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPet {

    private static String BASE_URL = "http://5.181.109.28:9090/api/v3";

    @Owner("Evgeny Konnov")
    @Description("Проверка удаления несуществующего питомца")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("api")
    @Test
    public void testDeleteNoneExistentPet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .delete(BASE_URL + "/pet/9999");

        String responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        assertEquals("Pet deleted", responseBody,
                "Текст ошибки не совпал с ожидаемым. Получен: " + responseBody);
    }
}

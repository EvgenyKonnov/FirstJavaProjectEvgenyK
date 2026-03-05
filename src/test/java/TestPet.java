import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPet {

    private static String BASE_URL = "http://5.181.109.28:9090/api/v3";

    @Test
    @Owner("Evgeny Konnov")
    @Description("Проверка удаления несуществующего питомца")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Pet")
    public void testDeleteNoneExistentPet() {
        Response response = step("Проверить что статус-код в ответе == 200", ()->
            given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .delete(BASE_URL + "/pet/9999")
        );

        String responseBody = response.getBody().asString();

        step("Проверить что статус-код в ответе == 200", ()->
                assertEquals(200, response.getStatusCode(),
                        "Код ответа не совпал с ожидаемым. Ответ: " + responseBody)
                );

        step("Проверить что текст в ответе 'Pet deleted'", ()->
                        assertEquals("Pet deleted", responseBody,
                                "Текст ошибки не совпал с ожидаемым. Получен: " + responseBody)
                );
    }

    @Test
    @Owner("Evgeny Konnov")
    @Description("Проверка получения данных о несуществующем питомце")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Pet")
    public void testGetNoneExistentPet() {
        Response response = step("Проверить что статус-код в ответе == 404", ()->
                given()
                    .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                    .when()
                    .get(BASE_URL + "/pet/9999")
        );

        String responseBody = response.getBody().asString();

        step("Проверить что текст в ответе 'Pet not found'", ()->
                assertEquals("Pet not found", responseBody,
                        "Текст ошибки не совпал с ожидаемым. Получен: " + responseBody)
        );

        step("Проверить что статус-код в ответе == 404", ()->
                assertEquals(404, response.getStatusCode(),
                        "Код ответа не совпал с ожидаемым. Ответ " + response.getBody().asString())
        );
    }
}

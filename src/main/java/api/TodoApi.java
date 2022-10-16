package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodoApi {

    private final Spec spec;

    public TodoApi() {
        spec = new Spec();
    }

    public Response getTodos(HashMap<String, String> params) {
        RequestSpecification specPrepare = given().spec(spec.getSpecNotAuth());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            specPrepare.queryParam(entry.getKey(), entry.getValue());
        }

        return specPrepare
                .get()
                .then()
                .spec(spec.getRespSpec())
                .extract()
                .response();
    }

    public Response getTodos() {
        return getTodos(new HashMap<>());
    }

    public Response createTodo(Object todoDto) {
        RequestSpecification specPrepare = prepareRequest(todoDto);

        return specPrepare
                .post()
                .then()
                .spec(spec.getRespSpec())
                .extract().response();
    }

    public Response editTodo(Object todoDto, Long id) {
        RequestSpecification specPrepare = prepareRequest(todoDto);

        String idStr = id != null ? "/" + id : "";

        return specPrepare
                .put(idStr)
                .then()
                .spec(spec.getRespSpec())
                .extract().response();
    }

    public Response deleteTodo(Long id, Boolean isAuth) {
        String idStr = id != null ? "/" + id : "";

        return given()
                .spec(isAuth ? spec.getSpecAuth() : spec.getSpecNotAuth())
                .delete(idStr)
                .then()
                .spec(spec.getRespSpec())
                .extract().response();
    }

    private RequestSpecification prepareRequest(Object todoDto) {
        RequestSpecification specPrepare = given().spec(spec.getSpecNotAuth());

        if (todoDto != null) specPrepare.body(todoDto);

        return specPrepare;
    }

}

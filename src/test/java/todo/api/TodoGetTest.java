package todo.api;

import io.restassured.response.Response;
import models.TodoDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoGetTest extends ApiTestsSetup {


    @ParameterizedTest(name = "Get todos positive with offset = {0}, limit = {1}")
    @MethodSource("todo.providers.Provider#providerTodoGetPositive")
    public void todosGetPositiveTest(Integer offset, Integer limit) {
        HashMap<String, String> params = new HashMap<>();
        if (offset != null) params.put("offset", offset.toString());
        if (limit != null) params.put("limit", limit.toString());

        List<TodoDto> todoDtoList = generateAndCreateTodos(4);

        Response response = todoApi.getTodos(params);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        todoHelpers.checkTodos(response, offset, limit, todoDtoList);
    }

    @ParameterizedTest(name = "Get todos negative with offset = {0}, limit = {1}")
    @MethodSource("todo.providers.Provider#providerTodoGetNegative")
    public void todosGetNegativeTest(String offset, String limit) {
        HashMap<String, String> params = new HashMap<>();
        if (offset != null) params.put("offset", offset);
        if (limit != null) params.put("limit", limit);

        generateAndCreateTodos(2);

        Response response = todoApi.getTodos(params);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    private List<TodoDto> generateAndCreateTodos(int count) {
        List<TodoDto> todoDtoList = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            todoDtoList.add(new TodoDto((long) i, CommonUtils.getLatinUniqueValueChar(10), i%2 == 0));
        }

        todoHelpers.createTodos(todoDtoList);
        return todoDtoList;
    }
}

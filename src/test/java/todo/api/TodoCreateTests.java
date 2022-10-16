package todo.api;

import models.TodoDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TodoCreateTests extends ApiTestsSetup {


    @ParameterizedTest(name = "Create todo positive. todo = {0}")
    @MethodSource("todo.providers.Provider#providerTodoPositive")
    public void todoCreatePositiveTest(TodoDto todoDto) {
        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        todoHelpers.checkTodoIsExist(todoDto);
    }

    @ParameterizedTest(name = "Create todo negative. todo = {0}")
    @MethodSource("todo.providers.Provider#providerTodoNegative")
    public void todoCreateNegativeTest(String todoDto) {
        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Create todo without body")
    public void todoCreateWithoutBodyTest() {
        todoApi.createTodo(null)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Create todo with duplicate id")
    public void todoCreateWithDuplicateIdTest() {
        TodoDto todoDto = todoHelpers.getRandomTodo();

        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        todoHelpers.checkTodoIsExist(todoDto);
    }
}

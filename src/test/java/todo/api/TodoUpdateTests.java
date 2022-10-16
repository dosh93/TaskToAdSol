package todo.api;

import models.TodoDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TodoUpdateTests extends ApiTestsSetup {

    @ParameterizedTest(name = "Update todo positive. todo = {0}")
    @MethodSource("todo.providers.Provider#providerTodoPositive")
    public void todoUpdatePositiveTest(TodoDto todoDtoUpdate) {
        TodoDto todoDto = new TodoDto(2L, "test", true);
        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        todoApi.editTodo(todoDtoUpdate, todoDto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        todoHelpers.checkTodoIsExist(todoDtoUpdate);
    }

    @ParameterizedTest(name = "Update todo negative. todo = {0}")
    @MethodSource("todo.providers.Provider#providerTodoNegative")
    public void todoUpdateNegativeTest(String todoDtoUpdate) {
        TodoDto todoDto = new TodoDto(2L, "test", true);
        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        todoApi.editTodo(todoDtoUpdate, todoDto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        todoHelpers.checkTodoIsExist(todoDto);
    }

    @Test
    @DisplayName("Update todo on non-existent id")
    public void todoUpdateOnNonExistentIdTest() {
        TodoDto todoDto = new TodoDto(2L, "test", true);
        todoApi.editTodo(todoDto, 1L)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Update todo without id")
    public void todoUpdateWithoutIdTest() {
        todoApi.editTodo(todoHelpers.getRandomTodo(), null)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Update todo without body")
    public void todoUpdateWithoutBodyTest() {
        TodoDto todoDto = todoHelpers.getRandomTodo();
        todoApi.createTodo(todoDto)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        todoApi.editTodo(null, todoDto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}

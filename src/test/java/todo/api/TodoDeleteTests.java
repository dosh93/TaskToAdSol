package todo.api;

import models.TodoDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TodoDeleteTests extends ApiTestsSetup {

    @Test
    @DisplayName("Delete todo")
    public void todoDeleteTest() {
        List<TodoDto> todoDtoList = new ArrayList<>();
        todoDtoList.add(new TodoDto(1L, "test1", true));
        todoDtoList.add(new TodoDto(2L, "test1", false));
        todoHelpers.createTodos(todoDtoList);

        todoApi.deleteTodo(todoDtoList.get(0).getId(), true)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        todoHelpers.checkTodoIsExist(todoDtoList.get(1));
        todoHelpers.checkTodoIsNotExist(todoDtoList.get(0));

        todoApi.deleteTodo(todoDtoList.get(1).getId(), true)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        todoHelpers.checkTodoIsNotExist(todoDtoList.get(0));
        todoHelpers.checkTodoIsNotExist(todoDtoList.get(1));
    }

    @Test
    @DisplayName("Delete todo on non-existent id")
    public void todoDeleteOnNonExistentIdTest() {
        todoApi.createTodo(new TodoDto(1L, "test1", true));

        todoApi.deleteTodo(2L, true)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Delete todo without id")
    public void todoDeleteWithoutIdTest() {
        todoApi.deleteTodo(null, true)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Delete todo without auth")
    public void todoDeleteWithoutAuthTest() {
        TodoDto todoDto = new TodoDto(1L, "test1", true);
        todoApi.createTodo(todoDto);

        todoApi.deleteTodo(todoDto.getId(), false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        todoHelpers.checkTodoIsExist(todoDto);
    }
}

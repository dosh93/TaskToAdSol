package steps;

import api.TodoApi;
import io.restassured.common.mapper.TypeRef;
import models.TodoDto;

import java.util.List;

public class TodoSteps {

    private TodoApi todoApi;

    public TodoSteps() {
        todoApi = new TodoApi();
    }


    public void deleteAllTodo() {
        List<TodoDto> todos = getTodos();
        todos.forEach(todoDto -> todoApi.deleteTodo(todoDto.getId(), true));
    }

    public List<TodoDto> getTodos() {
        return todoApi.getTodos().body().as(new TypeRef<>() {
        });
    }


}

package todo.helpers;

import api.TodoApi;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.TodoDto;
import steps.TodoSteps;
import utils.CommonUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TodoHelpers {

    private final TodoApi todoApi;
    private final TodoSteps todoSteps;

    public TodoHelpers() {
        todoApi = new TodoApi();
        todoSteps = new TodoSteps();
    }

    public void checkTodoIsExist(TodoDto todoDtoReference) {
        List<TodoDto> todos = todoSteps.getTodos();
        Optional<TodoDto> first = todos.stream().filter(todoDto -> Objects.equals(todoDto.getId(), todoDtoReference.getId())).findFirst();

        assertTrue(first.isPresent(), "Todo is not exist");
        assertEquals(first.get(), todoDtoReference, "Todo is not equals");
    }

    public void checkTodoIsNotExist(TodoDto todoDtoReference) {
        List<TodoDto> todos = todoSteps.getTodos();
        Optional<TodoDto> first = todos.stream().filter(todoDto -> Objects.equals(todoDto.getId(), todoDtoReference.getId())).findFirst();

        assertFalse(first.isPresent(), "Todo is exist");
    }

    public void checkTodos(Response response, Integer offset, Integer limit, List<TodoDto> todoDtoListAll) {
        List<TodoDto> todos = response.body().as(new TypeRef<>() {
        });
        Stream<TodoDto> stream = todoDtoListAll.stream();
        List<TodoDto> todoDtoListRef;

        if (limit != null && offset != null) todoDtoListRef = stream.skip(offset).limit(limit).toList();
        else if (limit == null && offset == null) todoDtoListRef = todoDtoListAll;
        else if (limit == null) todoDtoListRef = stream.skip(offset).toList();
        else todoDtoListRef = stream.limit(limit).toList();

        assertEquals(todos.size(), todoDtoListRef.size(), "Wrong size todos");
        for (TodoDto todoRef : todoDtoListRef) {
            Optional<TodoDto> first = todos.stream().filter(todoDto -> todoDto.getId().equals(todoRef.getId())).findFirst();
            assertTrue(first.isPresent(), "Todo is not exist with id = " + todoRef.getId());
            assertEquals(first.get(), todoRef, "Todo is not equals");
        }
    }

    public TodoDto getRandomTodo() {
        return new TodoDto(CommonUtils.getRandomLong(1L, 100L), CommonUtils.getLatinUniqueValueChar(10), false);
    }

    public void createTodos(List<TodoDto> todoDtoList) {
        todoDtoList.forEach(todoApi::createTodo);
    }
}

package todo.api;

import api.TodoApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import steps.TodoSteps;
import todo.helpers.TodoHelpers;

public class ApiTestsSetup {

    protected static TodoApi todoApi;
    protected static TodoSteps todoSteps;
    protected static TodoHelpers todoHelpers;

    @BeforeAll
    static void setup() {
        todoApi = new TodoApi();
        todoSteps = new TodoSteps();
        todoHelpers = new TodoHelpers();
    }

    @BeforeEach
    private void start() {
        todoSteps.deleteAllTodo();
    }
}

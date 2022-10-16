package todo.providers;

import models.TodoDto;
import org.junit.jupiter.params.provider.Arguments;
import utils.CommonUtils;

import java.util.stream.Stream;

public class Provider {

    private static Stream<Arguments> providerTodoPositive() {

        return Stream.of(
                Arguments.of(new TodoDto(1L, CommonUtils.getCyrillicAndLatinCharacters(), true)),
                Arguments.of(new TodoDto(1L, CommonUtils.getCyrillicUniqueValueChar(10), false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getLatinUniqueValueChar(10), false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getNumberUniqueValue(10), false)),
                Arguments.of(new TodoDto(1L, "", false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getLatinUniqueValueChar(1), false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getLatinUniqueValueChar(255), false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getLatinUniqueValueChar(2048), false)),
                Arguments.of(new TodoDto(1L, "DROP TABLE user;", false)),
                Arguments.of(new TodoDto(1L, "(\" ' ` | / \\ , ; : & < > ^ * ? Tab « »)", false)),
                Arguments.of(new TodoDto(1L, "\"[|]'~<!--@/*$%^&#*/()?>,.*/\\", false)),
                Arguments.of(new TodoDto(1L, "àáâãäåçèéêëìíîðñòôõöö", false)),
                Arguments.of(new TodoDto(1L, "      ", false)),
                Arguments.of(new TodoDto(1L, " ", false)),
                Arguments.of(new TodoDto(1L, " " + CommonUtils.getLatinUniqueValueChar(10) + " ", false)),
                Arguments.of(new TodoDto(1L, CommonUtils.getLatinUniqueValueChar(10) + "\n", false)),
                Arguments.of(new TodoDto(1L, "\n\b", false)),
                Arguments.of(new TodoDto(Long.MAX_VALUE, "\n\b", false))
        );
    }

    private static Stream<Arguments> providerTodoNegative() {

        return Stream.of(
                Arguments.of("{\"id\":1,\"text\":null,\"completed\":true}"),
                Arguments.of("{\"id\":1,\"completed\":true}"),
                Arguments.of("{\"id\":1,\"text\":\"test\",\"completed\":null}"),
                Arguments.of("{\"id\":1,\"text\":\"test\"}"),
                Arguments.of("{\"id\":null,\"text\":\"test\",\"completed\":true}"),
                Arguments.of("{\"text\":\"test\",\"completed\":true}"),
                Arguments.of("{\"id\":-1,\"text\":\"test\",\"completed\":true}"),
                Arguments.of("{\"id\":0,\"text\":\"test\",\"completed\":true}")
        );
    }

    private static Stream<Arguments> providerTodoGetPositive() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(1, 0),
                Arguments.of(1, 2),
                Arguments.of(null, 2),
                Arguments.of(2, null),
                Arguments.of(null, null)
        );
    }

    private static Stream<Arguments> providerTodoGetNegative() {
        return Stream.of(
                Arguments.of("null", null),
                Arguments.of("-1", null),
                Arguments.of("asd", null),
                Arguments.of(null, "null"),
                Arguments.of(null, "-1"),
                Arguments.of(null, "asd"),
                Arguments.of("0", "asd"),
                Arguments.of("asd", "1")
        );
    }
}

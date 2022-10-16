package models;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class TodoDto {
    private Long id;
    private String text;
    private boolean completed;

    public TodoDto() {
    }

    public TodoDto(Long id, String text, boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoDto todoDto = (TodoDto) o;
        return completed == todoDto.completed && Objects.equals(id, todoDto.id) && Objects.equals(text, todoDto.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, completed);
    }
}

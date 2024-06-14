package com.oop.todo.Domain.Todo.dto;

import com.oop.todo.Domain.Todo.Entity.Priority;
import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;
    private Priority priority;
    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        this.priority = entity.getPriority();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .priority(dto.getPriority())
                .done(dto.isDone()).build();
    }

    public static TodoDTO toDTO(TodoEntity entity) {
        TodoDTO dto = new TodoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDone(entity.isDone());
        dto.setPriority(entity.getPriority());
        return dto;
    }
}

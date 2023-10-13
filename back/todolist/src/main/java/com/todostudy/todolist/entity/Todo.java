package com.todostudy.todolist.entity;

import com.todostudy.todolist.dto.GetTodoListRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    private Integer todoId;
    private Integer userId;
    private String todo;

    public GetTodoListRespDto toTodoListRespDto() {
        return GetTodoListRespDto.builder()
                .todoId(todoId)
                .userId(userId)
                .todo(todo)
                .build();
    }
}

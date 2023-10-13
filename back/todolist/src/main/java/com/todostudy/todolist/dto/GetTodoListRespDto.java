package com.todostudy.todolist.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTodoListRespDto {
    private int todoId;
    private int userId;
    private String todo;
}

package com.todostudy.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Authority {
    private int authorityId;
    private int roleId;
    private int userId;
    private Role role;
}

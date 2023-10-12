package com.todostudy.todolist.repository;

import com.todostudy.todolist.entity.Todo;
import com.todostudy.todolist.exception.TodoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
    public Integer postTodo(Integer userId, String todo) throws TodoException;
}

package com.todostudy.todolist.repository;

import com.todostudy.todolist.dto.GetTodoListRespDto;
import com.todostudy.todolist.entity.Todo;
import com.todostudy.todolist.exception.TodoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    public Integer postTodo(Integer userId, String todo) throws TodoException;
    public List<Todo> getTodoListByUserId(Integer userId);
    public Integer updateTodo(GetTodoListRespDto getTodoListRespDto) throws TodoException;
    public Integer deleteTodo(Integer todoId) throws TodoException;
}

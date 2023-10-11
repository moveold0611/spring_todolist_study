package com.todostudy.todolist.repository;

import com.todostudy.todolist.entity.User;
import com.todostudy.todolist.exception.SignupException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public Integer saveUser(User user) throws SignupException;
    public User findUser(String key, String value);
    public Integer getUserCountByEmail(String email);
}

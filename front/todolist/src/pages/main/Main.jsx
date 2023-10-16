import React, { useEffect, useState } from 'react';
import jwt_decode from "jwt-decode"
import { useNavigate } from 'react-router-dom';
import { deleteTodo, getTodo, postTodo, updateTodo } from '../../apis/api/todo';
import { useQuery } from 'react-query';

function Main(props) {
    const navigate =  useNavigate();
    const [ todoInput, setTodoInput ] = useState("");
    const [ updateTodoInput, setUpdateTodoInput ] = useState("");

    const [ todoIdStatus, setTodoIdStatus] = useState(0); 

    const todoList = useQuery(["todoList"], async () => {
        try {
            const response = await getTodo();
            return response;
        } catch (error) {
            console.log(error)
        }
    }, {
        retry: 0,
        refetchOnWindowFocus: false
    });


    useEffect(()=>{
        try {
            let decoded = jwt_decode(localStorage.getItem("accessToken").substring(7))
        } catch (error) {
            console.log(error)
        }
        if(!localStorage.getItem("accessToken")) {
            navigate("/auth/signin")
        }
    }, [])

    const handleOnChange = (e) => {
        setTodoInput(e.target.value);
    }

    const handleDeleteTodo = async (todoId) => {
        console.log(todoId)
        try {
            const response = await deleteTodo(todoId);
            todoList.refetch();
        } catch (error) {
            console.log(error.response.data)
        }
    }

    const handleAddTodo = async () => {
        console.log('클릭')
        try {
            const response = await postTodo({
                content: todoInput
            });
            todoList.refetch();
        } catch (error) {
            console.log(error.response.data)
        }   
    }

    const handleupdateTodoOnChange = (e) => {
        setUpdateTodoInput(e.target.value);
    }

    const handleChangeUpdateIdStatus = (key) => {
        console.log(todoIdStatus);
        setTodoIdStatus(key);
    }

    return (
        <div>
            <div>
                <input placeholder='todolist' onChange={handleOnChange}/>
                <button onClick={handleAddTodo}>todo 추가</button>
            </div>
                <ul>
                     {/* todoList의 로딩이 끝나고, todoList값이 있을 때 가져온다 */}
                    {todoList.isLoading ? "" : todoList?.data?.data.map(todo => 
                        <li key={todo.todoId} >{todo.todo}
                        {todoIdStatus == 0 
                        ? <button key={todo.todoId} onClick={handleChangeUpdateIdStatus}>수정</button> 
                        : <button key={0} onClick={handleChangeUpdateIdStatus}>test</button>}
                        <button onClick={() => handleDeleteTodo(todo.todoId)}>삭제</button>
                        </li>
                    )} 
                </ul>
        </div>
    );
}

export default Main;
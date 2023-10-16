import instance from "../utils/instance"

const option = {
    headers: {
        Authorization: localStorage.getItem("accessToken")
    }
}

export const postTodo = async (todo) => {
    const response = await instance.post("/todo", todo, option);
    return response;
}

export const getTodo = async () => {
    const response = await instance.get("/todos", option);
    return response;
}

export const updateTodo = async (todo) => {
    const response = await instance.put("/updatetodo", todo, option)
}

export const deleteTodo = async (todoId) => {
    const response = await instance.delete(`/deletetodo/${todoId}`, option);
    return response;
}
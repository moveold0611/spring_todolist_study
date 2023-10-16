import instance from "../utils/instance"

export const signup = async (account) => {
    const response = await instance.post("/auth/signup", account);
    console.log(response)
    return response;
}

export const signin = async (emailAndPassword) => {
    const response = await instance.post("/auth/signin", emailAndPassword);
    console.log(response)
    return response;
}

export const tokenCheck = async () => {
    const response = await instance.get("/authenticated")
    console.log(response)
    return response
}
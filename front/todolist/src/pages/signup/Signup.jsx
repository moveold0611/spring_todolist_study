import axios from 'axios';
import React, { useState } from 'react';
import { signup, test } from '../../apis/api/account';
import { useNavigate } from 'react-router-dom';

function Signup(props) {
    const navigate = useNavigate();

    const emptyAccount = {
        email : "",
        password : "",
        name : ""
    }

    const [ account, setAccount ] = useState(emptyAccount);
   
    const changeAccount = (e) => {
        const { name, value } = e.target;
        setAccount({
            ...account,
            [name]: value
        })
        console.log(account);
    }

    const handleSignupSubmit = async () => {
        try {
            const response = await signup(account);
            console.log(response.data)
            navigate("/auth/signin");
        } catch (error) {
            const responseErrors = error.response.data
            const keyList = Object.keys(responseErrors)

            if(keyList.includes("email")) {
                alert(responseErrors.email)
            }else if(keyList.includes("password")) {
                alert(responseErrors.password)
            }else if(keyList.includes("name")) {
                alert(responseErrors.name)
            }else {
                alert(responseErrors.message)
            }
        }
    }

    return (
        <div>
            <h1>회원가입</h1>
            <input placeholder='email' name={"email"} onChange={changeAccount} />
            <input placeholder='password' name={"password"} onChange={changeAccount} type='password'/>
            <input placeholder='name' name={"name"} onChange={changeAccount} />
            <button onClick={handleSignupSubmit}>가입</button>
        </div>
    );
}

export default Signup;
import React, { useEffect, useState } from 'react';
import { signin } from '../../apis/api/account';
import { useNavigate } from 'react-router-dom';

function Signin(props) {

    const navigate = useNavigate();

    const EmptyEmailAndPassword = ({
        email: "",
        password: ""
    })

    const [ emailAndPassword, setEmailAndPassword ] = useState(EmptyEmailAndPassword);

    useEffect(()=>{
        if(!!localStorage.getItem("accessToken")) {
            navigate("/")
        }
    }, []);

    const handleOnChange = (e) => {
        const { name, value } = e.target;
        setEmailAndPassword({
            ...emailAndPassword,
            [name]: value
        })
        console.log(emailAndPassword)
    }

    const handleSigninClick = async () => {
        try {
            const response = await signin(emailAndPassword);
            localStorage.setItem("accessToken", "Bearer " + response.data);
            window.location.replace("/")
        } catch (error) {
            console.log(error.response.data)
        }
    }

    return (
        <div>
            <input name='email' placeholder='email' onChange={handleOnChange} />
            <input name='password' placeholder='password' type='password' onChange={handleOnChange} />
            <button onClick={handleSigninClick}>로그인</button>
        </div>
    );
}

export default Signin;
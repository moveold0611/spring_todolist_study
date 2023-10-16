import { Navigate, useLocation, useNavigate } from 'react-router-dom';
import { useQuery } from 'react-query';
import { useEffect, useState } from 'react';
import { tokenCheck } from '../apis/api/account';

function AuthRoute({ element }) {
    const navigate = useNavigate();
    const location = useLocation();
    const pathname = location.pathname;
    const permitAllPath = ["/auth"];
    const [ elementState, setElementState ] = useState(<></>);
  
    useEffect(() => {
        tokenCheck()
        .then((response) => {
            for(let path of permitAllPath) {
                if(pathname.startsWith(path)) {
                    navigate("/");
                }
            }
        }).catch((error) => {
            let flag = false;
            for(let path of permitAllPath) {
                if(pathname.startsWith(path)) {
                    flag = true;
                }
                if(!flag) {
                    navigate("/auth/signin");
                }
            }
        })
        .finally(() => {
            setElementState(element);
        });
    }, [elementState])
    
    //     window.location.replace("/"); 새로고침

    // const authenticateState
    //     = useQuery(["authenticate"], authenticate, {
    //         retry: 0,
    //         refetchOnWindowFocus: false
    //     }); // useQuery는 모두 get요청


    // if(authenticateState.isError) {
    //     for(let path of permitAllPath) {
    //         if(pathname.startsWith(path)) {
    //             return element;
    //         }
    //     }
    //     return <Navigate to={"/"}/>
    // }

    // for(let path of permitAllPath) {
    //     if(pathname.startsWith(path)) {
    //         return <Navigate to={"/"}/>
    //     }
    // }

    
    return elementState;
}

export default AuthRoute;
import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import errorReducer from "../reducers/errorReducer";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dipatch => {
    try {
        await axios.post("http://localhost:8080/api/users/register", newUser);
        history.push("/login");
        dipatch({
            type : GET_ERRORS, 
            payload :{}
        });
    } catch (error) {
        console.log({error : error});

        dipatch({
            type :GET_ERRORS,
            payload :error.response.data
        })
    }
}

export const login = LoginRequest => async dispatch => {
    try {
        const res = await axios.post("http://localhost:8080/api/users/login", LoginRequest);
        const {token} = res.data;


        localStorage.setItem("jwt-token", token)

        setJWTToken(token)
        const decode = jwt_decode(token);

        dispatch({
            type: SET_CURRENT_USER,
            payload: decode
        })

    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload:error.response.data
        })
    }

}


export const logout = () => dispatch => {
    localStorage.removeItem("jwtToken")
    setJWTToken(false);
    dispatch({
        type : SET_CURRENT_USER,
        payload : {}
    })
}
import axios from "axios";
import {GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT} from "./types";


export const createProject = (project, history) => async dispatch => {

    console.log("Create Project Action Running"); 

    try {
        const res = await axios.post("http://localhost:8080/api/project", project)
        history.push("/dashboard")
        dispatch({
            type: GET_ERRORS,
            payload:{}
        })
    } catch (err) {
        console.log("PROJECT UPDATE FAILED")
        dispatch({
            type: GET_ERRORS,
            payload:err.response.data
        })
    }
};


export const getProjects = () => async dispatch =>{

    const res = await axios.get("http://localhost:8080/api/project/all")
    
    console.log("All Projects API call")
    console.log(res.data);
    
    dispatch({
        type : GET_PROJECTS,
        payload : res.data
    });
};


export const getProject = (id, history) => async dispatch =>{
    
    try {
        const res = await axios.get(`http://localhost:8080/api/project/${id}`)
        dispatch({
            type : GET_PROJECT,
            payload : res.data
        });
    } catch (error) {
        history.push("/dashboard");
    }
};

export const deleteProject = id => async dispatch => {
    if(window.confirm("Are you Sure? this will delete data"))
        {
            const res = await axios.delete(`http://localhost:8080/api/project/${id}`)

            dispatch({
                type : DELETE_PROJECT,
                payload : id
            });
        }


}

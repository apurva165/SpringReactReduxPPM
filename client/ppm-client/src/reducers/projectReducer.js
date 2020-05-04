import {GET_PROJECTS, GET_PROJECT, DELETE_PROJECT} from "../actions/types";


const initialState = {
    projects : [], 
    project : {}
};

export default function(state  = initialState, action){

    switch(action.type) {
        case GET_PROJECTS:
            console.log("GET_PROJECTS Action called")
            return {
                ...state, 
                projects : action.payload
            };

        case GET_PROJECT:
            console.log("Get project Action called")
            return {
                ...state, 
                project: action.payload
            }
         case DELETE_PROJECT:
            console.log("Delete project Action called")
            return {
                ...state, 
                projects: state.projects.filter(project=>project.projectIdentifier !== action.payload)
                };  

        default:
            console.log("default Case Action")
            return state;
    }
}
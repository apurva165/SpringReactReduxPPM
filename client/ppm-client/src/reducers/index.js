import  {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backogReducer";
import SecurityReducer from "./SecurityReducer";

export default combineReducers({
    errors: errorReducer, 
    project : projectReducer, 
    backlog : backlogReducer, 
    security : SecurityReducer 
});
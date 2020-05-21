import React from 'react';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router , Route, Switch } from 'react-router-dom';
import AddProject from './components/Project/AddProject';
import {Provider} from "react-redux"
import store from "./store";
import UpdateProject from './components/Project/UpdateProject';
import ProjectBoard from "./components/projectBoard/ProjectBoard";
import Landing from './components/Landing';
import Register from './components/UserManagement/Register';
import Login from './components/UserManagement/Login';
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from './actions/types';
import { logout } from './actions/SecurityActions';
import SecuredRoutes from "./securityUtils/SecureRoute"
const jwtToken = localStorage.jwtToken

if(jwtToken){
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);

  store.disptch({
    type : SET_CURRENT_USER,
    payload : decoded_jwtToken
  });

  const currentTime = Date.now() / 1000;

  if(decoded_jwtToken.exp < currentTime){
    
    store.disptch(logout());
    window.location.href="/";
  }
}

function App() {
  return ( 
    <Provider store={store}>
    <Router >
    < div className = "App" >
      <Header / >
      {
        //public routes
      }
      <Route exact path='/' component={Landing}></Route>
      <Route exact path='/register' component={Register}></Route>
      <Route exact path='/login' component={Login}></Route>

      {
        //private routes
      }
      <Switch>
      <SecuredRoutes exact path='/addProject' component={AddProject}>
      </SecuredRoutes>
      <SecuredRoutes exact path='/dashboard' component={Dashboard}>
      </SecuredRoutes>
      <SecuredRoutes exact path='/updateProject/:id' component={UpdateProject}>
      </SecuredRoutes>
      <SecuredRoutes exact path='/projectBoard/:id' component={ProjectBoard}>
      </SecuredRoutes>
      </Switch>
    </div>
    </Router>
    </Provider>
    );
  }

  export default App;
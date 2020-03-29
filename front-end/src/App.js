import React from 'react';
import { Route, Switch } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'bootstrap/dist/css/bootstrap.min.css';
import Header  from './components/header';
import  LoginComponent   from './components/login';
import  DashBoard   from './components/dashboard';
import  PageNotFound   from './components/PageNotFound';
import 'react-toastify/dist/ReactToastify.css';
import SignUp from './components/signup';

const App = () =>{
    const notify = () => toast("Wow so easy !");
    return(
        <div>
            <Header/>
            
            <Switch>
                <Route exact path="/" component={LoginComponent}/>
                <Route exact path="/signup" component={SignUp}/>
                <Route exact path="/dashboard" component={DashBoard}/>
                <Route component={PageNotFound}/>
            </Switch>
        </div>
    );
}


export default App;
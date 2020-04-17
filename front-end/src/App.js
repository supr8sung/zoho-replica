import React from 'react';
import { Route, Switch ,withRouter} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css";
//import Loader from 'react-loader-spinner';
import Header  from './components/header';
import  DashBoard   from './components/dashboard';
import  PageNotFound   from './components/PageNotFound';
import Profile from './components/Profile';
import loggedOut from './components/loggedout';
//import {fetch} from './services/httpServices';
import viewprofile from './components/viewprofile';
import modifyUserDetails from './components/modifyUserDetails';
//import { UserProvider } from './services/user-context';
//import {useEffect} from 'react';


const App = (props) =>{
    return(
           
            
            <div>
                { (props.location.pathname !== '/loggedout' &&
                props.location.pathname !== '/login') ?  <Header/> : null }
                    <Switch>
                            <Route exact path="/" component={DashBoard}/>
                            <Route path="/profile" component={Profile}/>
                            <Route path="/viewprofile/:id" component={viewprofile}/>
                            <Route path="/loggedout" component={loggedOut}/>
                            <Route path="/edituser/:id" component={modifyUserDetails}/>
                            <Route component={PageNotFound}/>
                    
                    </Switch>
                </div> 
                    
                
             
    );
}


export default withRouter(App);
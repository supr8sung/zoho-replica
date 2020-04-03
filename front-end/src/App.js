import React, { useState } from 'react';
import { Route, Switch ,withRouter} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header  from './components/header';
import  DashBoard   from './components/dashboard';
import  PageNotFound   from './components/PageNotFound';
import Profile from './components/Profile';
import loggedOut from './components/loggedout';
import {fetch} from './services/httpServices';
import { UserProvider } from './services/user-context';
import {useEffect} from 'react';


const App = (props) =>{
    const [user,setUser] = useState();

    const saveDataSuccessHandler = (response) =>{
        if(response.payload && response.payload.data){
            if(!localStorage.getItem('username')){
                localStorage.setItem('username',{username: response.payload.data.fullname});
            }
            setUser({userData:response.payload.data});
        }
    }


    useEffect(()=>{
        fetch.get({
            url: '/zoho/loggedinuser',
            callbackHandler: saveDataSuccessHandler
        });
        return(() =>{
            setUser(); 
        })
    },[]);

    const redirectToLogin = () =>{
        window.location.href="http://localhost:8080/login";
    }
    
    
    return(
        <div>{
           
            (user && user.userData) ? 
            <div>
                <UserProvider value={JSON.stringify(user.userData)}>
                { props.location.pathname !== '/loggedout' ?  <Header/> : null}
                    <Switch>
                            <Route exact path="/" component={DashBoard}/>
                            <Route path="/profile" render={() => <Profile
                             profileDetails={JSON.stringify(user.userData) } />}/>
                            <Route path="/loggedout" component={loggedOut}/>
                            <Route component={PageNotFound}/>
                    
                    </Switch>
                </UserProvider>
                </div> : 
                <div className="container-fluid">
                    <h1>You have to login to continue</h1><button onClick={redirectToLogin}>Please Login</button>
                </div>
        }   
        </div>
    );
}


export default withRouter(App);
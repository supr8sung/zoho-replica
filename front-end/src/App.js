import React, { useState } from 'react';
import { Route, Switch ,withRouter} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css";
import Loader from 'react-loader-spinner';
import Header  from './components/header';
import  DashBoard   from './components/dashboard';
import  PageNotFound   from './components/PageNotFound';
import Profile from './components/Profile';
import loggedOut from './components/loggedout';
import {fetch} from './services/httpServices';
import viewprofile from './components/viewprofile';
import { UserProvider } from './services/user-context';
import {useEffect} from 'react';


const App = (props) =>{
    const [user,setUser] = useState({});
    const [loading,setLoading] = useState(false);
    const saveDataSuccessHandler = (response) =>{
        if(response.payload && response.payload.data){
            if(!localStorage.getItem('username')){
                localStorage.setItem('username',{username: response.payload.data.fullname});
            }

                setLoading(false);
                setUser({userData:response.payload.data});
            
        }
    }


    useEffect(()=>{
        setLoading(true);
        fetch.get({
            url: '/zoho/loggedinuser',
            callbackHandler: saveDataSuccessHandler
        });
        return(() =>{
            setUser(); 
        })
    },[]);

    // const redirectToLogin = () =>{
    //     setLoading(false);
    //     window.location.href="/login";
    // }
    
    
    return(
           
            (!loading && user.userData) ? 
            <div>
                <UserProvider value={JSON.stringify(user.userData)}>
                { props.location.pathname !== '/loggedout' ?  <Header/> : null}
                    <Switch>
                            <Route exact path="/" component={DashBoard}/>
                            <Route path="/profile" render={() => <Profile
                             profileDetails={JSON.stringify(user.userData) } />}/>
                            <Route path="/viewprofile/:id" component={viewprofile}/>
                            <Route path="/loggedout" component={loggedOut}/>
                            <Route component={PageNotFound}/>
                    
                    </Switch>
                </UserProvider>
                </div> : 
                <div className="spinner">
                    <Loader
                    type="ThreeDots"
                    color="#00BFFF"
                    height={100}
                    width={100}
                    timeout={10000}
                    />
                </div>
                    
                
             
    );
}


export default withRouter(App);
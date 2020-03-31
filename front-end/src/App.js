import React from 'react';
import { Route, Switch } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header  from './components/header';
import  DashBoard   from './components/dashboard';
import  PageNotFound   from './components/PageNotFound';

const App = () =>{
    return(
        <div>
            <Header/>
            
            <Switch>
                <Route exact path="/" component={DashBoard}/>
                <Route component={PageNotFound}/>
            </Switch>
        </div>
    );
}


export default App;
import React from 'react';

const loggedout = () =>{
    const redirectToLogin = () =>{
        window.location.href="http://localhost:8080/login";
    }
    return(
        <div className="container-fluid mt-100">
            <p>You Have been logged out</p>
            <button className="btn btn-outline-primary" onClick={redirectToLogin}>login</button>
        </div>
        
    );
}


export default loggedout;
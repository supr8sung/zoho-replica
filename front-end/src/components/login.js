import React from 'react';
import APP from  '../../src/constants';
import {fetch} from '../services/httpServices';
import validate from '../services/validators';
import { cloneDeep } from 'lodash'; 
import { useState } from "react";
import  useForm  from '../services/custom-hooks';
import { ToastContainer, toast } from 'react-toastify';
import { Link } from "react-router-dom";


const LoginComponent = (props) =>{

    const {
        values,
        errors,
        handleChange,
        handleSubmit,
      } = useForm(loginAccount, validate);


      function loginAccount(values){
        event.preventDefault();
        fetch.post({
            url: 'http://localhost:8080/login',
            requestBody: {
                "username": values.username,
                "password": values.password
              },
            callbackHandler: saveDataSuccessHandler
        });
    }
    function saveDataSuccessHandler(response){
        
        if(response.payload.status == 'S'){
           // notify(response.payload.message);
            props.history.push("/dashboard");
        }
    }


        return(
            
            <div className="container-fluid mt-100">
                <div className="redirect">
                    <button className="btn btn-link"> <Link to="/SignUp" >Sign Up>>></Link></button>
                </div>
                
                <ToastContainer />
               {
                   <div>
                       <form onSubmit={handleSubmit} noValidate>
                    <p>LOGIN TO ZR</p>
                    <div className="form-group">
                        <label>UserName:</label>
                        <input type="text" name="username" value={values.username || ''} className = {(errors && errors.username) ? 'input-error form-control' : 'form-control' } onChange={handleChange} id="signUpUsername" />
                    </div>
                    <div className="errorBlock">
                            <span className='error--danger'>{(errors && errors.username) ? errors.username : ''}</span>
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input type="password" name="password" value={values.password || ''} className = {(errors && errors.password) ? 'input-error form-control' : 'form-control' } onChange={handleChange}  id="signUpPassword" />
                    </div>
                    <div className="errorBlock">
                        <span className='error--danger'>{(errors && errors.password) ? errors.password : ''}</span>
                    </div>
                    <div className="checkBoxLogin">
                        <input name="remember" value={values.rememberMe || ''} type="checkbox" />
                        <label className="checkBoxLabel">Remember Me</label>
                    </div>
                    <button className="btn btn-primary" type="submit">Login</button>
                    <button className="btn btn-link"> <Link to="/SignUp" >Forgot Password?</Link></button>
                </form>
                
                   </div>
                
            } 
            </div>
                 
        );
} 


export default LoginComponent;
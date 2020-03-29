import React from 'react';
import APP from  '../../src/constants';
import {fetch} from '../services/httpServices';
import validate from '../services/validators';
import { cloneDeep } from 'lodash'; 
import { useState } from "react";
import  useForm  from '../services/custom-hooks';
import { ToastContainer, toast } from 'react-toastify';
import {useHistory }  from "react-router";
import {Link}  from "react-router-dom";
const SignUp = (props) =>{
      const {
        values,
        errors,
        handleChange,
        handleSubmit,
      } = useForm(createAccount, validate);

    const notify = (message) => toast(message);
    function createAccount(values){
        event.preventDefault();
        fetch.post({
            url: 'http://localhost:8080/zoho/signup',
            requestBody: {
                "username": values.username,
                "mobile": values.phone,
                "email": values.email,
                "password": values.password,
                "fullname": values.username,
                "company": "XEBIA",
                "role":"ADMIN"
              },
            callbackHandler: saveDataSuccessHandler
        });
    }

    // function handleResponse(response,obj){
    //     saveDataSuccessHandler(response)
    // }

    function saveDataSuccessHandler(response){
        
        if(response.payload.status == 'S'){
           // notify(response.payload.message);
            props.history.push("/");
        }
    }
    
        return(
            
            <div className="container-fluid mt-100">

                <div className="redirect">
                    <button className="btn btn-link" type="button"><Link to="/" >Login>>></Link></button>
                </div>
                <ToastContainer />
               {    
                <div>
                    <form onSubmit={handleSubmit} noValidate>
                    <p>SIGNUP ZR</p>
                    <div className="form-group">
                        <label>UserName:</label>
                        <input type="text" name="username" value={values.username || ''} className = {(errors && errors.username) ? 'input-error form-control' : 'form-control' } onChange={handleChange} id="signUpUsername" />
                    </div>
                        <div className="errorBlock">
                            <span className='error--danger'>{(errors && errors.username) ? errors.username : ''}</span>
                        </div>
                    <div className="form-group">
                        <label>Phone:</label>
                        <input type="phone" name="phone" value={values.phone || ''} className = {(errors && errors.phone) ? 'input-error form-control' : 'form-control' } onChange={handleChange} id="signUpPhone" />
                    </div>
                    <div className="errorBlock">
                            <span className='error--danger'>{(errors && errors.phone) ? errors.phone : ''}</span>
                        </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input type="email" name="email" value={values.email || ''} className = {(errors && errors.email) ? 'input-error form-control' : 'form-control' } onChange={handleChange}  id="signUpEmail" />
                    </div>
                    <div className="errorBlock">
                        <span className='error--danger'>{(errors && errors.email) ? errors.email : ''}</span>
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input type="password" name="password" value={values.password || ''} className = {(errors && errors.password) ? 'input-error form-control' : 'form-control' } onChange={handleChange}  id="signUpPassword" />
                    </div>
                    <div className="errorBlock">
                        <span className='error--danger'>{(errors && errors.password) ? errors.password : ''}</span>
                    </div>
                    <button className="btn btn-primary" type="submit">Create An Account</button>
                   
                </form>
                     
                    
                </div>

                    
            } 
            </div>
                 
        );
} 


export default SignUp;
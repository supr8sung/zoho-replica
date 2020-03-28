import React from 'react';
import APP from  '../../src/constants';
import {fetch} from '../services/httpServices';
import { validator } from '../services/validators';
import { cloneDeep } from 'lodash'; 

const LoginComponent = () =>{
    const [signUpErrors, setSignUpErrors] = React.useState({
        username: '',
        email: '',
        password:''
      });
    const [signUpValues, setSignUpValues] = React.useState({
        username: '',
        email: '',
        password:''
    });
    const [signUp,setSignUp] = React.useState(false);

    const handleSignUp = (e) => {
        event.preventDefault();
        setSignUp(!signUp);
      };
    const onChangeInputField = (value,modifiedField,event) =>{
        let validationResult = validator.handleValidation(value,signUpErrors,modifiedField,event);
        setSignUpErrors({...signUpErrors},validationResult);

        const localObj = cloneDeep( signUpValues );
        localObj[modifiedField] = value;
        setSignUpValues(localObj);
    }

   const validateForm = (errors) => {
        let valid = true;
        for (let formProps in signUpValues){
           if(!signUpValues[formProps]){
               valid = false;
               errors[formProps] = 'Required';     
           }
           if(errors[formProps]){
                valid = false;
           }         
        };
        return valid;
    }
    const createAccount = () =>{
        event.preventDefault();
        if(validateForm(signUpErrors)){
            fetch.post({
                url: 'http://localhost:8080/zoho/signup',
                requestBody: signUpValues,
                callbackHandler: saveDataSuccessHandler
            });
        }
        else{
            setSignUpErrors({...signUpErrors});
        }
    }


    const saveDataSuccessHandler = (response) =>{
        console.log(response);
    }
    
        return(

            <div className="container-fluid mt-100">
               {signUp ? 


                    <form>
                    <p>SIGNUP ZR</p>
                    <div className="form-group">
                        <label>UserName:</label>
                        <input type="text" className = {(signUpErrors && signUpErrors.username) ? 'input-error form-control' : 'form-control' } onChange={(e) => onChangeInputField(e.target.value, 'username', ['required','minlength'])} id="signUpUsername" />
                    </div>
                        <div className="errorBlock">
                            <span className='error--danger'>{(signUpErrors && signUpErrors.username) ? signUpErrors.username : ''}</span>
                        </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input type="text" className = {(signUpErrors && signUpErrors.email) ? 'input-error form-control' : 'form-control' } onChange={(e) => onChangeInputField(e.target.value, 'email', ['required','email'])}  id="signUpEmail" />
                    </div>
                    <div className="errorBlock">
                        <span className='error--danger'>{(signUpErrors && signUpErrors.email) ? signUpErrors.email : ''}</span>
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input type="password" className = {(signUpErrors && signUpErrors.password) ? 'input-error form-control' : 'form-control' } onChange={(e) => onChangeInputField(e.target.value, 'password', ['required','minlength'])} id="signUpPassword" />
                    </div>
                    <div className="errorBlock">
                        <span className='error--danger'>{(signUpErrors && signUpErrors.password) ? signUpErrors.password : ''}</span>
                    </div>
                    <button className="btn btn-primary" onClick={() => createAccount()}>Create An Account</button>
                    <button className="btn btn-link" onClick = {handleSignUp}>Login</button>
                </form>
                
                :
                <form>
                    <p>LOGIN TO ZR</p>
                    <div className="form-group">
                        <label>Email:</label>
                        <input type="text" className="form-control" id="loginEmail" />
                    </div>
                    <div className="form-group">
                        <label for="pwd">Password:</label>
                        <input type="password" className="form-control" id="loginPassword" />
                    </div>
                    <div className="checkBoxLogin">
                        <input type="checkbox" id="pwd" />
                        <label className="checkBoxLabel">Remember Me</label>
                    </div>
                    <div className="checkBoxLogin">
                        <input type="checkbox" id="pwd" />
                        <label className="checkBoxLabel">Forgot your password?</label>
                    </div>
                    <button className="btn btn-primary">Login</button>
                    <button className="btn btn-secondary">Cancel</button>
                    <button className="btn btn-link" onClick = {handleSignUp}>Sign Up</button>
                </form>

            } 
            </div>
                 
        );
} 


export default LoginComponent;
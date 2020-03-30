import React from 'react';
import {fetch} from '../services/httpServices';

const DashBoard  = () =>{


    const CheckIn = (values) => {
        event.preventDefault();
        fetch.post({
            url: 'http://localhost:8080/zoho/signup',
            requestBody: {
                "username": "supr8sung",
                "fullname": "Supreet Singh",
                "mobile": "9643496936",
                "email": "yoyobunny420@gmail.com",
                  "password":"1234",
                  "company": "XEBIA",
                  "role":"ADMIN"
              },
            callbackHandler: saveDataSuccessHandler
        });
    }

    function saveDataSuccessHandler(response){
        console.log(response);
    }

   
        return(
            <div className="container-fluid mt-100">
                <button type="submit" className="btn btn-primary" onClick={CheckIn}>CheckIn</button>
                <button type="submit" className="btn btn-primary" onClick={CheckIn}>CheckOut</button>
            </div>
            
        );
} 

export default DashBoard
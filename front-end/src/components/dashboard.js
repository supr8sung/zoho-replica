import React from 'react';
import {fetch} from '../services/httpServices';

const DashBoard  = () =>{


    const CheckIn = (values) => {
        event.preventDefault();
        fetch.post({
            url: 'http://localhost:8080/zoho/user/checkin',

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
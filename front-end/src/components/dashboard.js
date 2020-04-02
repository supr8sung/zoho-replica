import React, { useContext } from 'react';
import {fetch} from '../services/httpServices';
import UserContext from '../services/user-context';


const DashBoard  = () =>{
    const user = useContext(UserContext);
    const CheckIn = (values) => {
        event.preventDefault();
        fetch.post({
            url: '/zoho/user/checkin',

            callbackHandler: saveDataSuccessHandler
        });
    }

    

    function saveDataSuccessHandler(response){
        console.log(response);
    }

   
        return(
            user ? 
                            
                            <div className="container-fluid mt-100">
                            <button type="submit" className="btn btn-primary" onClick={CheckIn}>CheckIn</button>
                            <button type="submit" className="btn btn-primary" onClick={CheckIn}>CheckOut</button>
                        </div> : null
                        
            
            
        );
} 

export default DashBoard
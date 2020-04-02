import React, { useContext,useState } from 'react';
import {fetch} from '../services/httpServices';
import UserContext from '../services/user-context';

const DashBoard  = () =>{
    const user = useContext(UserContext);
    const [checkin,setCheckin] = useState(false)
    const checkIncheckOut = (values) => {
        event.preventDefault();
        setCheckin(!checkin)
        fetch.post({

            url: checkin?  '/zoho/user/checkout':'/zoho/user/checkin',


            callbackHandler: saveDataSuccessHandler
        });
    }




    function saveDataSuccessHandler(response){
        console.log(response);
    }

   
        return(
            user ? 
                            
                            <div className="container-fluid mt-100">
                            <button type="submit" className="btn btn-primary" onClick={()=>{checkIncheckOut(checkin)}}>{(checkin) ? "Checkout":"Checkin"}</button>

                        </div> : null
                        
            
            
        );
} 

export default DashBoard
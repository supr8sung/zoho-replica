import React, { useContext } from 'react';
import UserContext from '../services/user-context';
import useTimer from '../services/custom-hooks';

const DashBoard  = () =>{
    const user = useContext(UserContext);
    const {checkin,minutes,hours,checkIncheckOut} = useTimer(user);

    return(
        JSON.parse(user) ?               
        <div className="container-fluid mt-100">
            <button type="submit" className="btn btn-primary" onClick={()=>{checkIncheckOut(checkin)}}>{(checkin) ? "Checkout":"Checkin"}</button>
            <button className="btn btn-primary">
                <span className={checkin ? 'checkin': null}>{hours} :{minutes}</span>
            </button>
            
        </div> : null 
    );
} 

export default DashBoard
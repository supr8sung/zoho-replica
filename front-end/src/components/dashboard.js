import React, { useContext } from 'react';
import UserContext from '../services/user-context';
import useTimer from '../services/custom-hooks';

const DashBoard  = () =>{
    const user = useContext(UserContext);
    const {checkin,minutes,hours,checkIncheckOut} = useTimer(user);

    return(
        JSON.parse(user) ?    
        
        
        <div className="hero-body">
            <div className="container">
                
                    
                    <div className="columns is-centered">
                    <div className="box">
                    <div className="column checkinout">
                        
                        <button type="submit" className={checkin ? "button is-danger is-rounded" : "button is-rounded"} onClick={()=>{checkIncheckOut(checkin)}}>{(checkin) ? "CHECK-OUT":"CHECK-IN"}</button>
                        <button className="button is-primary is-rounded" >
                            <span className={checkin ? 'checkin': null}>{hours < 10 ? '0' + hours : hours} :{minutes < 10 ? '0' + minutes : minutes}</span>
                        </button>
                    </div>
                    {/* <div className="column is-half-tablet is-half-desktop is-half-widescreen">
                        
                    
                    </div> */}
                
                    </div>
                    
                </div>
            </div>
        </div> : null
    );
} 






export default DashBoard




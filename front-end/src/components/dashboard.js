import React from 'react';
import useTimer from '../services/custom-hooks';
import SignupForm from './signup';
import { withRouter } from 'react-router';
const DashBoard  = (props) =>{

    const {checkin,minutes,hours,checkIncheckOut,user} = useTimer(); 
    
    
    return( user ? 
            
                <div className="hero-body">
            <div className="container">
                    <div className="columns is-centered">
                        {
                            user.email === 'admin@gmail.com' ? <div className="box">
                            <SignupForm props={props}/>
                        </div>  : null
                        }
                            
                        {
                            user.email !== 'admin@gmail.com'? <div className="box">
                            <div className="column checkinout">
                                
                                <button type="submit" className={checkin ? "button is-danger is-rounded" : "button is-rounded"} onClick={()=>{checkIncheckOut(checkin)}}>{(checkin) ? "CHECK-OUT":"CHECK-IN"}</button>
                                <button className="button is-primary is-rounded" >
                                    <span className={checkin ? 'checkin': null}>{hours < 10 ? '0' + hours : hours} :{minutes < 10 ? '0' + minutes : minutes}</span>
                                </button>
                            </div>
                        
                            </div>: null
                        }
                    
                    
                </div>
            </div>
        </div> 
            : null
      
    );
} 






export default withRouter(DashBoard);




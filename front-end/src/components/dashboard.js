import React, { useState, useEffect } from 'react';
import useTimer from '../services/custom-hooks';
import SignupForm from './signup';
import {fetch} from '../services/httpServices';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const DashBoard  = () =>{

    const {checkin,minutes,hours,checkIncheckOut,user} = useTimer();
    const [availableReporting,setAvailableReporting] = useState([]);
    const [showManagerList,setShowManagerList] = useState(true);
    const [selectedMgr, setSelectedMgr] = useState();
    const saveDataSuccessHandler = (response) =>{
        setAvailableReporting(response.payload.data);
        setShowManagerList(true);
    }


    const notify = (msgObject) => {
        if(msgObject.status === 'S'){
            toast.warn((msgObject.message) ? msgObject.message:'success', {
                position: toast.POSITION.TOP_RIGHT
              });
        } else{
            toast.error(msgObject.message, {
                position: toast.POSITION.TOP_RIGHT
              });
        }
        
    };
    const userCreated = (id) =>{
        fetch.get({
            url: 'zoho/user/all-reporting/' + id,
            callbackHandler: saveDataSuccessHandler
        });
    }
    const assignManagerHandler = (response) =>{
        if(response.status === 'success'){
            if(response.payload.status === 'S'){
                notify({status: 'S',message:response.payload.message});
            } else{
                notify({status: 'F',message:response.payload.message});
            }
        } else{
            notify({status: 'S',message:'service error'});
        }
    }
    const assignManager = () =>{
        let payload = {
            userId: 29,
            managerId: selectedMgr
        };
        fetch.post({
            url:'zoho/user/hierarchy',
            requestBody:payload,
            callbackHandler: assignManagerHandler
        })
    }

    useEffect(() =>{
        fetch.get({
            url: 'zoho/user/all-reporting/' + 29,
            callbackHandler: saveDataSuccessHandler
        });
    },[])
   
    const handleChange = (e) =>{
        setSelectedMgr(e.target.value);
    }
    


    return( user ? 
            
                <div className="hero-body">
            <div className="container">
            <ToastContainer />
                    
                    <div className="columns is-centered">
                        {showManagerList ? null : <div className="box">
                            <SignupForm action={userCreated}/>
                        </div>}
                        
                        {showManagerList ? <div className="box">
                            <h4>Map Reporting Manager</h4>
                            <div className="field desigField">
                                <label htmlFor="designation"  className="label">select a reporting manager</label>
                                <div className="control">
                                    <div className="select designation">
                                            <select  onChange={handleChange}  id="designation">
                                                    {availableReporting.map((rep) =>
                                                        <option key={rep.id} value={rep.id}>{rep.fullname}</option>
                                                        )
                                                    };
                                                    
                                                    </select>
                                    </div>
                                </div>
                            </div>

                            
                            <button type="button" className="button is-link" onClick={assignManager}>Assign</button>
                        </div> : null}
                        
                        {
                            user.userId !== 10 ? <div className="box">
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






export default DashBoard




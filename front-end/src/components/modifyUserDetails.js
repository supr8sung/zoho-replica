import React,{useState,useEffect} from 'react';
import {fetch} from '../services/httpServices';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { withRouter } from 'react-router';
const modifyUserDetails = (props) =>{
    const [availableReporting,setAvailableReporting] = useState([]);
    const [selectedMgr, setSelectedMgr] = useState();
    const notify = (msgObject) => {
        if(msgObject.status === 'S'){
            toast.warn((msgObject.message) ? msgObject.message:'success', {
                position: toast.POSITION.TOP_RIGHT
              });
        } else{
            toast.error(msgObject.message || 'error', {
                position: toast.POSITION.TOP_RIGHT
              });
        }
        
    };
    const saveDataSuccessHandler = (response) =>{
        setAvailableReporting(response.payload.data);
    }

    const assignManagerHandler = (response) =>{
        if(response.status === 'success'){
            if(response.payload.status === 'S'){
                notify({status: 'S',message:(response.payload.message) ?
                response.payload.message: 'Manager Mapped Successfully' });
            } else{
                notify({status: 'F',message:response.payload.message});
            }
        } else{
            notify({status: 'S',message:'service error'});
        }
    }

    const assignManager = () =>{
        if(!selectedMgr){
          return  notify({status: 'F',message:'Please select a manager'});
        }
        let payload = {
            userId: props.history.location.appState.empId,
            managerId: selectedMgr
        };
        fetch.post({
            url:'/zoho/user/hierarchy',
            requestBody:payload,
            callbackHandler: assignManagerHandler
        })
    }
   


    useEffect(() =>{  
        if(props.history.location.appState)
        fetch.get({
            url: '/zoho/user/all-reporting/' + props.history.location.appState.empId,
            callbackHandler: saveDataSuccessHandler
        });
    },[]);

    const handleChange = (e) =>{
        
        setSelectedMgr(e.target.value);
    }
    return(
        <div className="hero-body">
        <div className="container">
                <div className="columns is-centered">
     <div className="box">
         
         <ToastContainer />
                    
    <h4>Map Reporting Manager</h4>
    <div className="field desigField">
        <label htmlFor="designation"  className="label">select a reporting manager</label>
        <div className="control">
            <div className="select designation">
                    <select  onChange={handleChange}  id="designation">
                            
                                <option value=''>select</option>{
                            availableReporting && availableReporting.length > 0 ? availableReporting.map((rep) =>
                                <option key={rep.id} value={rep.id}>{rep.fullname}</option>
                                )
                            :<option value=''>select</option>};
                            
                            </select>
            </div>
        </div>
    </div>

    
    <button type="button" className="button is-link" onClick={assignManager}>Assign</button>
</div> 
</div></div></div>
)
}

export default withRouter(modifyUserDetails);
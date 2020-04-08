import React from 'react';
import { withRouter } from 'react-router';

import {fetch} from '../services/httpServices';

const viewprofile = (props) =>{





    let proDetails = {};
    if(!props.location.userdetails){
        props.history.push("/");
    } else{
        proDetails = props.location.userdetails;
    }

    const saveDataSuccessHandler = (response)=>{
        console.log(response);
    }

    const handleFollow = () =>{
        fetch.post({
            url: '/zoho/user/follow',
            requestBody: proDetails.fullname,
            callbackHandler: saveDataSuccessHandler
        });
    }


    
    return(
        <div className="hero-body">
        <div className="container">
                
                <div className="columns is-centered">
                <div className="box">
                <h4 className="title is-5"><i className="fas fa-info-circle mr-10"></i>Basic Info</h4>
                <div className="column userProfile">
                    <div className="field">
                        <label className="label">UserName</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.fullname}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Email</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.email}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Company</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.company}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Mobile</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.mobile}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Followers</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.followersCount}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Following</label>
                        <div className="control has-icons-left">
                            <span>{proDetails.followingCount}</span>
                        </div>
                    </div>
                    
                </div>
                    <button className="button is-success is-rounded" onClick={() => {handleFollow()}}><i className="fas fa-plus follow"></i>Follow</button>
                </div>
                
            </div>
        </div>
    </div>
    
    
        
    );
}


export default withRouter(viewprofile);
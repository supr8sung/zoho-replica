import React, { useState,useEffect,useRef } from 'react';
import { withRouter } from 'react-router';

import {fetch} from '../services/httpServices';

const viewprofile = (props) =>{
    let proDetails = {};
    if(!props.location.userdetails){
        props.history.push("/");
    } else{
        proDetails = props.location.userdetails;
    }
    const profileDisplay = useRef(null);
    const [dpAvailable ] = useState((proDetails.photo) ? true : false); 
    
    let [followers,setFollowers] = useState(proDetails.followersCount);
    let [following] = useState(proDetails.followingCount);
    const [followFlag, setFollowFlag] = useState(true);
    

    const getLoggedInUser = (response) =>{
        if(response.payload.status === 'S'){
            let userFollowing =  response.payload.data.following;
            if(Array.isArray(userFollowing) && userFollowing.length > 0){
                if(userFollowing.indexOf(proDetails.fullname) === -1){
                    setFollowFlag(true);
                } else{
                    setFollowFlag(false);
                }
            }  
        }
    }

    useEffect(() =>{
        fetch.get({
            url: '/zoho/loggedinuser',
            callbackHandler: getLoggedInUser
          }); 
        if(profileDisplay.current){
            profileDisplay.current.src = 'data:image/jpg;base64,' + proDetails.photo;
        }
    },[]);


    
    const saveDataSuccessHandler = (response)=>{
            if(response.payload.status === 'S'){
                if(followFlag){
                    followers++;
                } else{
                    followers--;
                }
                setFollowers(followers);
                setFollowFlag(!followFlag);
            }
            
    }

    const handleFollow = () =>{
        fetch.post({
            url: followFlag ? '/zoho/user/follow/' + proDetails.fullname :
            '/zoho/user/unfollow/' + proDetails.fullname ,
            callbackHandler: saveDataSuccessHandler
        });
    }


    
    return(
        <div className="hero-body">
        <div className="container">
                
                <div className="columns">
                <div className="displayPic">
                                
                                { 
                                   dpAvailable ? <div id="proImg">
                                 <img ref={profileDisplay} id="profileImage" src="" alt="profile"/>
                                 </div>  :  
                                 <div><i className="fas fa-user-circle profilePic"></i></div>
                                }
                         </div>
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
                            <span>{followers}</span>
                        </div>
                    </div>
                    <div className="field">
                        <label className="label">Following</label>
                        <div className="control has-icons-left">
                            <span>{following}</span>
                        </div>
                    </div>
                    
                </div>
                    <button className={ followFlag ? "button is-success is-rounded" : "button is-danger is-rounded"} onClick={() => {handleFollow()}}><i className={followFlag ? 'fas fa-plus follow' :'fas fa-minus follow'}></i>{followFlag ? 'Follow' : 'Unfollow'}</button>
                </div>
                
            </div>
        </div>
    </div>
    
    
        
    );
}


export default withRouter(viewprofile);
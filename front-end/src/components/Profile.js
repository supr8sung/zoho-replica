import React, { useRef, useState, useEffect } from 'react';
import {fetch} from '../services/httpServices';

const Profile = () =>{
   const myRef = useRef(null);
   const oPass = useRef(null);
   const nPass = useRef(null);
   const profileDisplay = useRef(null);
    const [proDetails,setProdetails] = useState();
    const [dpAvailable,setDpAvailable ] = useState();
    const [isoldpasserror,setIsoldpasserror] = useState(false);
    const [isnewpasserror,setIsnewpasserror] = useState(false);
    let oldPass ,newPass;
    const  changePassword = () =>{
        if(oPass.current){
            oPass.current.value = '';
        }
        if(nPass.current){nPass.current.value= '';}
        setIsnewpasserror(false);
        setIsoldpasserror(false);
        myRef.current.classList.add('is-active');
    }


    const getUserDetailsHandler = (response) =>{
        if(response.payload.status === 'S' && response.payload.data){
            let details = response.payload.data;
            setProdetails({...details});
            if(response.payload.data.photo){
                setDpAvailable(true);
                if(profileDisplay.current){
                    profileDisplay.current.src = 'data:image/jpg;base64,' + response.payload.data.photo;
                }
            } else{
                setDpAvailable(false);
                if(profileDisplay.current){
                    profileDisplay.current.src = 'data:image/jpg;base64,';
                }
            }
        }
    }

    useEffect(() =>{
        fetch.get({
            url: '/zoho/loggedinuser',
            callbackHandler: getUserDetailsHandler
        });
    },[]);
    


    const closeModal = () =>{
        myRef.current.classList.remove('is-active');
    }
    const uploadPicSuccessHandler = (response) =>{
        if(response.payload.status === 'S'){
            setDpAvailable(true);
            profileDisplay.current.src = 'data:image/jpg;base64,' + response.payload.data;
        }
        
    }

    const deleteSuccessHandler = (response) =>{
        if(response.payload.status === 'S'){
            setDpAvailable(false);
        }
    }
    const deletePic = (e) =>{
        fetch.post({
            url:'zoho/user/photo/delete',
            callbackHandler: deleteSuccessHandler
        })
    }


    
    const uploadPic = (e) =>{
        e.preventDefault();
        if(e.currentTarget.files && e.currentTarget.files[0])
        fetch.postUpload({
            url: 'zoho/user/photo/upload',
            requestBody: e.currentTarget.files[0],
            callbackHandler: uploadPicSuccessHandler
        });
    }
    const onChangePassword = (e, password) =>{
        if(e.target.value){
            if(password === 'O'){
                oldPass = e.target.value;
               
                setIsoldpasserror(false);
             }
             if(password === 'N'){
                 newPass = e.target.value;
                 setIsnewpasserror(false);
             }
        }
    }


    const logoutResponseHandler = (response) =>{
        if(response.payload.status === 'S'){
            window.location.href="/login";
        }
       
    }
    const saveDataSuccessHandler = (response) =>{
        console.log(response);
        myRef.current.classList.remove('is-active');
        fetch.get({
            url: '/logout',
            callbackHandler: logoutResponseHandler
        });
    }
    const submitPassword = () =>{
        if(oldPass && newPass){
            let payload = {
                "oldPassword":oldPass,
                "newPassword": newPass
            }
            fetch.post({
                url: 'zoho/user/change-password',
                requestBody: payload,
                callbackHandler: saveDataSuccessHandler
            });
        } else{
            if(!oldPass){
                setIsoldpasserror(true);
            }
            if(!newPass){
                setIsnewpasserror(true);
            } 
        }
        
    }

    
    return(
        <div className="hero-body">
            <div className="container">
            <div ref={myRef} className="modal">
            <div className="modal-background"></div>
            <div className="modal-card">
                <header className="modal-card-head">
                <p className="modal-card-title">Change Password</p>
                <button className="delete"  onClick={closeModal} aria-label="close"></button>
                </header>
                <section className="modal-card-body">
                <input className="input resetPass" ref={oPass}  type="password" onChange={(e) => {onChangePassword(e,'O')}} placeholder="Old Password" />
                <div>
                  { isoldpasserror  ?  <span className="err-msg">Required</span> : null }
                </div>
                <input className="input resetPass" type="password" ref={nPass}  onChange={(e) => {onChangePassword(e,'N')}} placeholder="New Password" />
                <div>
                  { isnewpasserror  ?  <span className="err-msg">Required</span> : null }
                </div>
                </section>
                <footer className="modal-card-foot">
                <button className="button is-success" onClick={() => submitPassword()}>Save changes</button>
                <button className="button" onClick={closeModal} aria-label="close">Cancel</button>
                </footer>
            </div>
            </div>
                <div className="columns">
                        <div className="displayPic">
                                
                               { 
                                  dpAvailable ? <div id="proImg">
                                <img ref={profileDisplay} id="profileImage" src="" alt="profile"/>
                                </div>  :  
                                <div><i className="fas fa-user-circle profilePic"></i></div>
                               }
                              
                            
                            <div className="profileActions" id="aImg">
                            <label htmlFor="profilePic" className="uploadPic"><i className="fas fa-edit actions"></i></label>
                            <input type="file" id="profilePic" onChange={(e) => {uploadPic(e)}}  className="DP"/>
                            <i className="fas fa-trash-alt uploadPic actions" onClick={(e) => {deletePic(e)}} ></i>
                                {/* <button className="button uploadPic">Upload Profile Picture</button> */}
                            </div> 
                            
                           
                        </div>
                    <div className="box">
                    <h4 className="title is-5"><i className="fas fa-info-circle mr-10"></i>Basic Info</h4>
                   { proDetails ? <div className="column userProfile">
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
                        <button className="button is-success is-rounded" onClick={() => {changePassword()}}><i className="fas fa-user-secret follow"></i>Change password</button>
                    </div> : null} 
                
                    </div>
                    
                </div>
            </div>
        </div>
        
        
        
    );
}


export default Profile;
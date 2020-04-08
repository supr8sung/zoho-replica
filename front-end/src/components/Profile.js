import React, { useRef, useState } from 'react';
import {fetch} from '../services/httpServices';

const Profile = (props) =>{
   const myRef = useRef(null);
   const oPass = useRef(null);
   const nPass = useRef(null);
    const proDetails = JSON.parse(props.profileDetails);
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

    const closeModal = () =>{
        myRef.current.classList.remove('is-active');
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


    const logoutResponseHandler = () =>{
        window.location.href="/login";
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
            <div ref={myRef} class="modal">
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
                        <button className="button is-success is-rounded" onClick={() => {changePassword()}}><i className="fas fa-user-secret follow"></i>Change password</button>
                    </div>
                
                    </div>
                    
                </div>
            </div>
        </div>
        
        
        
    );
}


export default Profile;
import React from 'react';

const Profile = (props) =>{
    const proDetails = JSON.parse(props.profileDetails)
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
                
                    </div>
                    
                </div>
            </div>
        </div>
        
        
        
    );
}


export default Profile;
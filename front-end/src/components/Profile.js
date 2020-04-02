import React from 'react';
import { Link } from 'react-router-dom';

const Profile = (props) =>{
    return(
        <div>
           
        <div className="container-fluid mt-100">
        <button className="btn btn-dark"><Link to="/">Back to Home</Link></button>
            <h3>profile</h3>
            <div className="form-group">
                <label>NAME:</label>
                <span>{props.profileDetails.user.fullname}</span>
            </div>

            <div className="form-group">
                <label>EMAIL:</label>
                <span>{props.profileDetails.user.email}</span>
            </div>

            <div className="form-group">
                <label>COMPANY:</label>
                <span>{props.profileDetails.user.company}</span>
            </div>

            <div className="form-group">
                <label>MOBILE:</label>
                <span>{props.profileDetails.user.mobile}</span>
            </div>

            <div className="form-group">
                <label>FOLLOWING:</label>
                <span>{props.profileDetails.user.followersCount}</span>
            </div>

            <div className="form-group">
                <label>FOLLOWERS:</label>
                <span>{props.profileDetails.user.followingCount}</span>
            </div>
     
     </div>
        </div>
        
        
    );
}


export default Profile;
import React from 'react';
import { Link } from 'react-router-dom';

const Profile = (props) =>{
    const proDetails = JSON.parse(props.profileDetails)
    return(
        <div>
           
        <div className="container-fluid mt-100">
        <button className="btn btn-dark"><Link to="/">Back to Home</Link></button>
            <h3>profile</h3>
            <div className="form-group">
                <label>NAME:</label>
                <span>{proDetails.fullname}</span>
            </div>

            <div className="form-group">
                <label>EMAIL:</label>
                <span>{proDetails.email}</span>
            </div>

            <div className="form-group">
                <label>COMPANY:</label>
                <span>{proDetails.company}</span>
            </div>

            <div className="form-group">
                <label>MOBILE:</label>
                <span>{proDetails.mobile}</span>
            </div>

            <div className="form-group">
                <label>FOLLOWING:</label>
                <span>{proDetails.followersCount}</span>
            </div>

            <div className="form-group">
                <label>FOLLOWERS:</label>
                <span>{proDetails.followingCount}</span>
            </div>
     
     </div>
        </div>
        
        
    );
}


export default Profile;
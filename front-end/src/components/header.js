import React from 'react';
import logo from '../../public/favicon.ico';
import {Link} from 'react-router-dom';
import {fetch} from '../services/httpServices';
import { withRouter } from "react-router-dom";
import { UserConsumer } from '../services/user-context';


class Header extends React.Component{
    
    // constructor(props){
    //     super(props);
    // };
    componentDidMount() {
        const user = this.context;
    
        console.log(user);
      }
    handleLogout (){
        fetch.get({
            url: '/logout',

            callbackHandler: (response) =>{
                this.saveDataSuccessHandler(response,this.props.history);
            }
            
        });
    }
    saveDataSuccessHandler = (response,route) =>{
            route.push('/loggedout');
            localStorage.removeItem('username');
            console.log(response);
    }

    render(){
        return(
            <UserConsumer>

        {props => {
          return   <div className="headerNav">
          <nav className="headerNavbar">
              <div className="navbarTitle">
                  
                  <img height="24" alt="zoho" src={logo}/>
              </div>
              {/* <div className="navBarSearch">
                  <input type="text" className="form-control"
                  />
              </div> */}
            {
                props  ? <div>
                          <div>
                <Link to="/profile">Go To Profile</Link>
            </div>
            {props}
            <div>
                <button  onClick={() => {this.handleLogout()}}>Logout</button>
            </div>
                    </div> : null
            }  
          </nav>
      </div> 
        }}
              
            </UserConsumer>
            
        );
    }
} 

export default withRouter(Header);
import React from 'react';
import {Link} from 'react-router-dom';
import {fetch} from '../services/httpServices';
import { withRouter } from "react-router-dom";
import { UserConsumer } from '../services/user-context';


class Header extends React.Component{
    // componentDidMount() {
    //     const user = this.context;
    //   }
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
          return  <div>
                <section className="hero is-light is-medium">
                <div className="hero-head">
          <nav className="navbar">
              <div className="container">
                  <div className="navbar-brand">
                      <a className="navbar-item">
                          ZOHO
                      </a>
                      <span className="navbar-burger burger" data-target="navbarMenuHeroA">
              <span></span>
              <span></span>
              <span></span>
            </span>
                  </div>
                  {props  ?
                  
                    
                  <div id="navbarMenuHeroA" className="navbar-menu">
                  <div className="navbar-end searchParent">
                            <input className="input" type="text" placeholder="Search Employee" />
                            <i className="fa fa-search searchEmployee" aria-hidden="true"></i>
                        </div>
                      <div className="navbar-end">
                          <Link  className="navbar-item" to="/"><i className="fas fa-home"></i></Link>
                          <Link to="/profile" className="navbar-item ">
                                <i className="fas fa-user-circle"></i>
                          </Link>
                          
                          <a className="navbar-item" onClick={() => {this.handleLogout()}}><i className="fas fa-power-off"></i>
                          </a>
                      </div>
                  </div> : null}
              </div>
                </nav>
            </div>
          </section>
          
          </div>  
          
        }}
              
            </UserConsumer>
            
        );
    }
} 

export default withRouter(Header);
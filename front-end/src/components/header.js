import React from 'react';
import {Link} from 'react-router-dom';
import {fetch} from '../services/httpServices';

import { withRouter } from 'react-router';

class Header extends React.Component{
    constructor(props){
        super(props);
        this.myRef = React.createRef();
        this.filteredOptions = [];
        this.showAutoCompleteOptions = false;
        this.loggedInuser = {};
    }

   getUserDetailsHandler = (response) =>{
        if(response.payload.status === 'S' && response.payload.data){
            let details = response.payload.data;
            this.setState({loggedInuser: {...details}});
        }
    }
    
    componentDidMount(){
        fetch.get({
            url: '/zoho/loggedinuser',
            callbackHandler: (response) =>{
                this.getUserDetailsHandler(response)
            }
        });
    }

    searchUsers = (e) =>{
        if(e.target.value && e.target.value.length > 2){
            this.myRef.current.classList.add('searchUsers');
            fetch.get({
                url: '/zoho/user/search/' + e.target.value,
                callbackHandler: this.searchSuccessHandler
            });   
        }   else{
            this.myRef.current.classList.remove('searchUsers');
            this.showAutoCompleteOptions = false;
            this.filteredOptions = [];
            this.setState([...this.filteredOptions]);
        }
    }

    searchSuccessHandler = (response) => {
        if(response.payload.data){
            this.showAutoCompleteOptions = true;
            this.filteredOptions = response.payload.data;
            this.setState([...this.filteredOptions]);
        } else{
            this.showAutoCompleteOptions = false;
            this.filteredOptions = [];

        }
        
    }

    viewSearchProfile = (id) => {
        const loggedInuser = this.state.loggedInuser;
        if(loggedInuser.email === 'admin@gmail.com'){
            //this.props.history.push("/");
            this.props.history.push({pathname:'/edituser/'+ id,appState:{empId: id}});
            this.showAutoCompleteOptions = false;
            this.myRef.current.value = '';
            return;
        }
        if(loggedInuser.userId === id){
            this.props.history.push({pathname:'/profile',profileDetails: loggedInuser});
            this.showAutoCompleteOptions = false;
            this.myRef.current.value = '';
        } else{
            fetch.get({
                url: '/zoho/user/view/' + id,
                callbackHandler: (response) =>{
                    this.searchProfileSuccessHandler(response,this.props.history,loggedInuser)
                } 
            })
        }
        
    }

    searchProfileSuccessHandler = (response,route,loggedUser) => {
        this.showAutoCompleteOptions = false;
        let user = response.payload.data;
        this.myRef.current.value = '';
        route.push({pathname:'/viewprofile/'+ user.fullname,userdetails: user});
        
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
            window.location.href="/login";
            localStorage.removeItem('username');
    }

    render(){
        return(


            <div>
                <section className="hero is-light is-medium">
                <div className="hero-head">
          <nav className="navbar">
              <div className="container">
                  <div className="navbar-brand">
                      <Link to="/" className="navbar-item">
                          ZOHO
                      </Link>
                      <span className="navbar-burger burger" data-target="navbarMenuHeroA">
              <span></span>
              <span></span>
              <span></span>
            </span>
                  </div>
                  <div id="navbarMenuHeroA" className="navbar-menu">
                  <div className="navbar-end searchParent">
                            <input className="input" ref={this.myRef} type="text" onChange={(e) => {this.searchUsers(e)}} placeholder="Search Employee" />
                            <i className="fa fa-search searchEmployee" aria-hidden="true"></i>
                              {
                                this.showAutoCompleteOptions ?  <ul className="autoSearch">{this.filteredOptions.map((item) =>
                                    <li onClick={() => {this.viewSearchProfile(item.id)}} key={item.id}>
                                       { item.photo ? <div id="searchInline">
                                 <img id="searchProfileImage" src={"data:image/jpg;base64," + item.photo} alt="profile" />
                                 </div>  :  
                                 <div className="searchInline"><i className="fas fa-user-circle searchProfilePic"></i></div>
                                }
                                       <span className="userlist">{item.fullname}</span> </li>
                                 )}</ul> : null
                              }  
                        </div>
                      <div className="navbar-end">
                          <Link  className="navbar-item" to="/"><i className="fas fa-home"></i></Link>
                          <Link to="/profile" className="navbar-item ">
                                <i className="fas fa-user-circle"></i>
                          </Link>
                          
                          <a className="navbar-item" onClick={() => {this.handleLogout()}}><i className="fas fa-power-off"></i>
                          </a>
                      </div>
                  </div> 
              </div>
                </nav>
            </div>
          </section>
          
          </div>  
            
        );
    }
} 

export default withRouter(Header);
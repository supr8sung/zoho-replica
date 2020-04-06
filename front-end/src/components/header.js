import React from 'react';
import {Link} from 'react-router-dom';
import {fetch} from '../services/httpServices';
import { withRouter } from "react-router-dom";
import { UserConsumer } from '../services/user-context';


class Header extends React.Component{
    // componentDidMount() {
    //     const user = this.context;
    //   }
    constructor(props){
        super(props);
        this.users = [
            {userid: 2,username:'kishore'},
            {userid: 3,username:'supreet'},
            {userid: 4,username: 'sai Kishore'},
            {userid: 5,username: 'messi'},
            {userid: 6,username: 'Xabi'},
            {userid: 7,username: 'Mbappe'},
            {userid: 8,username: 'Ronaldo'},
            {userid: 9,username: 'Company'},
            {userid: 10,username: 'Neymar'},
            {userid: 11,username: 'suprit'},
        ];
        this.filteredOptions = [];
        this.showAutoCompleteOptions = false;
    }
    

    searchUsers = (e) =>{
        if(e.target.value && e.target.value.length > 2){
            fetch.get({
                url: '/zoho/user/search/' + e.target.value,
                callbackHandler: this.searchSuccessHandler
            });   
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

    handleLogout (){
        fetch.get({
            url: '/logout',

            callbackHandler: (response) =>{
                this.saveDataSuccessHandler(response,this.props.history);
            }
            
        });
    }
    saveDataSuccessHandler = (response,route) =>{
            window.location.href="http://localhost:8080/login";
            localStorage.removeItem('username');
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
                            <input className="input" type="text" onChange={(e) => {this.searchUsers(e)}} placeholder="Search Employee" />
                            <i className="fa fa-search searchEmployee" aria-hidden="true"></i>
                              {
                                this.showAutoCompleteOptions ?  <ul className="autoSearch">{this.filteredOptions.map((item) =>
                                    <li key={item.id}>{item.fullname}</li>
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
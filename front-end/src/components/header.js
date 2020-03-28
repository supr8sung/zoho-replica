import React from 'react';
import logo from '../../public/favicon.ico';

class Header extends React.Component{

    render(){
        return(
            <div className="headerNav">
                <nav className="headerNavbar">
                    <div className="navbarTitle">
                        
                        <img height="24" src={logo}/>
                    </div>
                    {/* <div className="navBarSearch">
                        <input type="text" className="form-control"
                        />
                    </div> */}
                </nav>
            </div>
        );
    }
} 

export default Header
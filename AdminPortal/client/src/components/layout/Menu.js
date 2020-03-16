import React from 'react';
import {Link} from 'react-router-dom';
import { NavLink } from 'react-router-dom';

const Menu = () => {
    return (
        <nav className="nav-wrapper grey darken-3">
            <div className="container">
                <Link to='/' className="brand-logo">Admin Portal</Link>
                <ul className="right">
                    <li><NavLink to='/signup'>Signup</NavLink></li>
                    <li><NavLink to='/signin'>Login</NavLink></li>
                    <li><NavLink to='/reports'>Reports</NavLink></li>
                    <li><NavLink to='/posts'>Posts</NavLink></li>
                    <li><NavLink to='/'>Log Out</NavLink></li>     
                </ul>                       
            </div>
        </nav>
    )
}

export default Menu;
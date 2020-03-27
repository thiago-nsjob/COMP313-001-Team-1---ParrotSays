import React from 'react';
import { Link } from 'react-router-dom';

const Menu = () => {
    return (
        <nav className="navbar navbar-expand-sm fixed-top navbar-dark" style={{background:"#D81B60"}}>
            <a className="navbar-brand" href="/">
                <img alt="logo" className="card" src="/images/ic_launcher_mini.png"></img>
            </a>

            <ul className="navbar-nav">
                <li className="nav-item">
                    <Link className="nav-link" to='/'>Home</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to='/signup'>Signup</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to='/signin'>Login</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to='/reports'>Reports</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to='/posts'>Posts</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to='/'>Log Out</Link>
                </li>
            </ul>
        </nav>
    )
}

export default Menu;
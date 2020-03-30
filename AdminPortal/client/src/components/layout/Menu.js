import React from "react";
import { NavLink,useHistory } from "react-router-dom";
import authHelper from "../auth/auth-helper";

const Menu = () => {

  var isAuthenticated = authHelper.isAuthenticated();
  let history = useHistory();
 // console.log("isAuthenticated");
  //console.log(isAuthenticated);

  const signOut = (e) => {
        e.preventDefault();
        history.push('/signin')
        authHelper.signOut(()=>console.log("user is signed out!"));
  };
  
  return (
    <nav
      className="navbar navbar-expand-lg fixed-top navbar-dark"
      style={{ background: "#D81B60" }}
    >
      <div className="container">
        <a className="navbar-brand" href="/">
          <img
            alt="logo"
            className="card"
            src="/images/ic_launcher_mini.png"
          ></img>
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">

            <li className="nav-item">
              <NavLink
                exact
                activeClassName="active"
                className="nav-link"
                to="/"
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item" style={{ display: isAuthenticated ? 'block' : 'none'}}>
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/signup"
              >
                Signup
              </NavLink>
            </li>
            <li className="nav-item" style={{ display: !isAuthenticated ? 'block' : 'none'}}>
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/signin"
              >
                Login
              </NavLink>
            </li>
            <li className="nav-item" style={{ display: isAuthenticated ? 'block' : 'none'}}>
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/reports"
              >
                Reports
              </NavLink>
            </li>
            <li className="nav-item" style={{ display: isAuthenticated ? 'block' : 'none'}}>
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/posts"
              >
                Posts
              </NavLink>
            </li>
            <li className="nav-item" style={{ display: isAuthenticated ? 'block' : 'none'}}>          
                <NavLink
                    className="nav-link"
                    activeClassName="active"
                    to="/signin"
                  >
                <div onClick={(e)=> signOut(e)}>Logout</div>   
                </NavLink>           
            </li>
            <li className="nav-item">
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/about"
              >
                About
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Menu;

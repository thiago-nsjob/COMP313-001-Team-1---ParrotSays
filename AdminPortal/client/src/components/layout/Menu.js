import React from "react";
import { NavLink, useHistory } from "react-router-dom";
import authHelper from "../auth/auth-helper";

const Menu = () => {
  var isAuthenticated = authHelper.isAuthenticated();
  let history = useHistory();

  const signOut = (e) => {
    e.preventDefault();
    history.push("/signin");
    authHelper.signOut(() => console.log("user is signed out!"));
  };

  return (
    <div className="">
      <nav
        className="navbar navbar-expand-lg navbar-dark"
        style={{ background: "#D81B60" }}
      >
        <div className="d-inline-flex">
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
                  <h3> Home</h3>
                </NavLink>
              </li>
              <li
                className="nav-item"
                style={{ display: isAuthenticated ? "block" : "none" }}
              >
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/signup"
                >
                  <h3>Signup</h3>
                </NavLink>
              </li>
              <li
                className="nav-item"
                style={{ display: !isAuthenticated ? "block" : "none" }}
              >
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/signin"
                >
                  <h3>Login</h3>
                </NavLink>
              </li>
              <li
                className="nav-item"
                style={{ display: isAuthenticated ? "block" : "none" }}
              >
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/reports"
                >
                  <h3>Reports</h3>
                </NavLink>
              </li>
              <li
                className="nav-item"
                style={{ display: isAuthenticated ? "block" : "none" }}
              >
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/posts"
                >
                  <h3>Posts</h3>
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/about"
                >
                  <h3> About</h3>
                </NavLink>
              </li>
              <li
                className="nav-item"
                style={{ display: isAuthenticated ? "block" : "none" }}
              >
                <NavLink
                  className="nav-link"
                  activeClassName="active"
                  to="/signin"
                >
                  <div onClick={(e) => signOut(e)}>
                    <h3>Logout</h3>
                  </div>
                </NavLink>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default Menu;

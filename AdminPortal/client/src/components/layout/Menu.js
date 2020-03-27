import React from "react";
import { Link } from "react-router-dom";

const Menu = () => {
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
            <Link className="nav-link active" to="/">
              Home
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/signup">
              Signup
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/signin">
              Login
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/reports">
              Reports
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/posts">
              Posts
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/">
              Log Out
            </Link>
          </li>
        </ul>
      </div>
      </div>
    </nav>
  );
};

export default Menu;

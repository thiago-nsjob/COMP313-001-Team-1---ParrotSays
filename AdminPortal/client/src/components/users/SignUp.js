import React, { useState, useEffect} from "react";

import authHelper from './../auth/auth-helper';
import { useHistory } from "react-router-dom";
import user from './api-user';

function SignUp() {
  const [username,setUsername] = useState();
  const [password,setPassword] = useState();
  const [confirmPassword,setConfirmPassword] = useState();
  const [email,setEmail] = useState();
  let history = useHistory();

  const signup = async (e) => {
    e.preventDefault();
    //console.log(this.state);

    if(confirmPassword !== password)
    {
      // throw error here
      alert('it is not same confirm password and password');
    }
    else
    if(authHelper.isAuthenticated){
      var createUserDto = {
        email: email,
        password: password,
        username: username
      };
      //console.log(createUserDto);
      try {
        await user.createUser(createUserDto,authHelper.isAuthenticated().token)
        .then((data) => {
          alert('User is created successfully');
          history.push('/');
        });
      } catch (error) {
         console.log(error);
      }
     
    }
    else{
      history.push('/signin');
    }
    

  };
  
    return (
      <div className="container d-flex" style={{marginTop: "125px"}}>
        <div className="card">
          <div className="card-header">
            <h2>Create User</h2>
          </div>

          <form onSubmit={signup}>
            <div className="card-body">
              <table>
              <tbody>
                <tr>
                  <td>
                    <label htmlFor="email">Email</label>
                  </td>
                  <td>
                    <input
                      type="text"
                      name="email"
                      id="email"
                      onChange={e => setEmail(e.target.value)}
                      className="form-control"
                    ></input>
                  </td>
                </tr>
                <tr>
                  <td>
                    <label htmlFor="username">UserName</label>
                  </td>
                  <td>
                    <input
                      type="text"
                      name="username"
                      id="username"
                      onChange={e => setUsername(e.target.value)}
                      className="form-control"
                    ></input>
                  </td>
                </tr>

                <tr>
                  <td>
                    <label htmlFor="password">Password</label>
                  </td>
                  <td>
                    <input
                      type="password"
                      name="password"
                      id="password"
                      onChange={e => setPassword(e.target.value)}
                      className="form-control"
                    ></input>
                  </td>
                </tr>

                <tr>
                  <td>
                    <label htmlFor="confirmPassword">Confirm Password</label>
                  </td>
                  <td>
                    <input
                      type="password"
                      name="confirmPassword"
                      id="confirmPassword"
                      onChange={e => setConfirmPassword(e.target.value)}
                      className="form-control"
                    ></input>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
            <div className="card-footer" align="right">
              <a className="btn btn-danger" href="/">
                Cancel
              </a>
              <button className="btn btn-success" type="submit" style={{marginLeft: "15px"}}>
                Create User
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  
}

export default SignUp;

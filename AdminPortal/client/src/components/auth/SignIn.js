import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';

import auth from './../auth/auth-helper'
import {signin} from './api-auth.js'


function SignIn(){
 
   //state variable for the screen, admin or user
   const [screen, setScreen] = useState('auth');
   //store input field data, user name and password
   const [username, setUsername] = useState();
   const [password, setPassword] = useState();
   const [error, setError] = useState('');
   const [redirect, setRedirect] = useState(false);
   
   const styles = {
    width: "500px",
    paddingTop: "50px"
  };
  //send username and password to the server
  // for initial authentication
  const login = async () => {
    console.log('calling auth');
    //console.log(username)
    try {
      const user = {
        username: username || undefined,
        password: password || undefined
      }
      
      console.log(user.username + user.password);

      //call authentication helper
      signin(user).then((data) => {
        if (data.error) {
             setError(data.error)
        } else {
          console.log('success')
          auth.authenticate(data, () => {
            setRedirect(true);
          })
        }
      })

    } catch (e) { //print the error
      console.log(e);
    }
  
  };

  //runs the first time the view is rendered
  //to check if user is signed in
  useEffect(() => {
    //readCookie();
  }, []); //only the first render

    return (
      
      <div className="container" style={styles}>
        { redirect ? <Redirect to='/reports'></Redirect>:
          <div>            
            <h5 className="grey-text text-darken-3">Sign In</h5>
            <br />
            <div className="input-field">
              <label htmlFor="username">Username <br /></label>
              <input type="text" id='username' onChange={e => setUsername(e.target.value)} />
            </div>
            <div className="input-field">
              <label htmlFor="password">Password <br/></label>
              <input type="password" id='password' onChange={e => setPassword(e.target.value)} />
            </div>
            <div className="input-field">
              <button className="btn pink lighten-1 z-depth-0" onClick={login}>Login</button>
            </div>          
          </div>
        }
       </div>
        
       
    
  )
}

export default SignIn
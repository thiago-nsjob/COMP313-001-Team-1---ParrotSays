import React, { useState, useEffect } from "react";
import { Redirect } from "react-router-dom";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import authHelper from "./../auth/auth-helper";

function SignIn() {
  //state variable for the screen, admin or user
  const [screen, setScreen] = useState("auth");
  //store input field data, user name and password
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [hasError, setHasError] = useState(false);
  const [redirect, setRedirect] = useState(false);

  const styles = {
    width: "500px",
    paddingTop: "50px",
  };
  //send username and password to the server
  // for initial authentication
  const login = async () => {
    console.log("calling auth");
    //console.log(username)
    try {
      const user = {
        username: username || undefined,
        password: password || undefined,
      };

      console.log(user.username + user.password);

      //call authentication helper
      authHelper.signIn(user).then((data) => {
        if (!data) {
          setHasError(true);
        } else {
          console.log("logged in successfuly");
          authHelper.authenticate(data, () => {
            setRedirect(true);
          });
        }
      });
    } catch (e) {
      //print the error
      console.log(e);
    }
  };

  //runs the first time the view is rendered
  //to check if user is signed in
  useEffect(() => {
    //readCookie();
  }, [hasError]); //only the first render

  return (
    <div className="container d-flex" style={{ marginTop: "125px" }}>
      {redirect ? (
        <Redirect to="/reports"></Redirect>
      ) : (
        <div className="container">
          <div className="card">
            <div className="card-header">
              <h2>Sign In</h2>
            </div>
            <div className="card-body">
              <table>
                <tbody>
                  <tr>
                    <td>
                      <label htmlFor="userName">User Name</label>
                    </td>
                    <td>
                      <input
                        id="userName"
                        type="text"
                        name="userName"
                        className="form-control"
                        onChange={(e) => setUsername(e.target.value)}
                      ></input>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <label htmlFor="password">Password</label>
                    </td>
                    <td>
                      <input
                        id="password"
                        type="password"
                        name="password"
                        className="form-control"
                        onChange={(e) => setPassword(e.target.value)}
                      ></input>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div className="card-footer" align="right">
              <a className="btn btn-danger" href="/">
                Reset
              </a>
              <button
                className="btn btn-success"
                type="button"
                onClick={login}
                style={{ marginLeft: "15px" }}
              >
                Sign In
              </button>
            </div>
          </div>
          <Alert show={hasError} variant="danger" className="mt-5">
            <Alert.Heading>Ops!</Alert.Heading>
            <p>
              Login attempt failed. Please review your credentials and try again
            </p>
            <hr />
            <div className="d-flex justify-content-end">
              <Button
                onClick={() => setHasError(false)}
                variant="outline-success"
              >
                Close
              </Button>
            </div>
          </Alert>
        </div>
      )}
    </div>
  );
}

export default SignIn;

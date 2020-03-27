import React, { Component } from "react";

class SignUp extends Component {
  state = {
    email: "",
    password: "",
    firstName: "",
    lastName: ""
  };
  handleChange = e => {
    this.setState({
      [e.target.id]: e.target.value
    });
  };
  handleSubmit = e => {
    e.preventDefault();
    console.log(this.state);
  };
  render() {
    return (
      <div className="container d-flex" style={{marginTop: "125px"}}>
        <div className="card">
          <div className="card-header">
            <h2>Create User</h2>
          </div>

          <form onSubmit={this.handleSubmit}>
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
                      onChange={this.handleChange}
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
                      onChange={this.handleChange}
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
                      onChange={this.handleChange}
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
}

export default SignUp;

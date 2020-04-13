import React, { useState } from "react";
import auth from "./../auth/auth-helper";
import { createReport } from "./api-report.js";
import { useHistory } from "react-router-dom";

function ReportCreate(props) {

  let defaultReportText = "";
  const [description, setDescription] = useState("");

  if (props.location.state && props.location.state.report) {
    defaultReportText = props.location.state.report;
  }

  let history = useHistory();
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (auth.isAuthenticated) {
      await createReport(auth.isAuthenticated().token, {
        description: description,
      }).then((data) => {
        console.log(data);
        history.push("/reports");
      });
    }
  };

  return (
    <div className="container d-flex" style={{ marginTop: "125px" }}>
      <div className="card shadow p-3">
        <div className="card-header">
          <h2>Create Report</h2>
        </div>

        <div className="card-footer">
          <strong>Description</strong>
          <form onSubmit={handleSubmit}>
            <table className="table">
              <tbody>
                <tr>
                  <td colSpan="2">
                    <textarea
                      name="Description"
                      className="form-control"
                      rows="10"
                      cols="50"
                      defaultValue={defaultReportText}
                      onChange={(e) => setDescription(e.target.value)}
                    >
                    </textarea>
                    <br></br>
                  </td>
                </tr>
              </tbody>
            </table>
            <div align="right">
              <input className="btn btn-danger" type="reset" value="Cancel" />
              <input
                className="btn btn-success ml-3"
                type="submit"
                value="Save"
              ></input>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default ReportCreate;

import React, { useState, useEffect } from "react";
import auth from "./../auth/auth-helper";
import { createReport } from "./api-report.js";
import { useParams, useHistory } from "react-router-dom";
import GoogleMapReact from "google-map-react";

function ReportCreate() {
  const [report, setReport] = useState({});
  const [solution, setSolution] = useState("");
  const [statusCode, setStatusCode] = useState("");
  const [error, setError] = useState(false);
  const AnyReactComponent = ({ text }) => <div>{text}</div>;

  let history = useHistory();
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (auth.isAuthenticated) {
      await createReport(auth.isAuthenticated().token, {
        solution: solution,
        statusCode: statusCode,
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
          <h2>Crate Report</h2>
        </div>

        <div className="card-body">
          <table>
            <tr>
              <td>
                <b>
                  <label for="ReportId">Report Id </label>
                </b>
              </td>
              <td>{report.reportId}</td>
            </tr>
            <tr>
              <td>
                <b>
                  <label for="Description">Description </label>
                </b>
              </td>
              <td>{report.description}</td>
            </tr>
            <tr>
              <td>
                <b>
                  <label for="DateTimeReport">Time Report </label>
                </b>
              </td>
              <td>
                {new Intl.DateTimeFormat("en-US", {
                  year: "numeric",
                  month: "short",
                  day: "2-digit",
                  hour: "2-digit",
                  minute: "2-digit",
                }).format(report.DateTimeReport)}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <b>Map Location </b>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div id="googleMap" style={{ width: "500px", height: "380px" }}>
                  <GoogleMapReact
                    bootstrapURLKeys={{
                      key: "AIzaSyAUMfy5t49e288JgHAguruAMDcpzC_iRbc&amp",
                    }}
                    defaultCenter={{ lat: -10.91111, lng: -37.07167 }}
                    center={{ lat: report.latitude, lng: report.longitude }}
                    defaultZoom={19}
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <b>
                  <label for="Picture">Picture </label>
                </b>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <img
                  src={`data:image/jpeg;base64,${report.picture}`}
                  style={{ width: "500px" }}
                  alt="No picture available"
                />
              </td>
            </tr>
          </table>
        </div>

        <div class="card-footer">
          <strong>Add/Edit a Solution</strong>
          <form onSubmit={handleSubmit}>
            <table class="table">
              <tbody>
                <tr>
                  <td colspan="2">
                    <textarea
                      name="Solution"
                      class="form-control"
                      rows="10"
                      cols="50"
                      onChange={(e) => setSolution(e.target.value)}
                    ></textarea>
                    <br></br>
                  </td>
                </tr>
                <tr>
                  <td>Status</td>
                  <td>
                    <select
                      class="form-control"
                      id="sel1"
                      name="StatusCode"
                      onChange={(e) => setStatusCode(e.target.value)}
                    >
                      <option value="0">Opened</option>
                      <option value="1">Investigation Requested</option>
                      <option value="2">Investigation Returned</option>
                      <option value="3">Solution Requested</option>
                      <option value="4">Solved</option>
                    </select>
                  </td>
                </tr>
              </tbody>
            </table>
            <div align="right">
              <input class="btn btn-danger" type="reset" value="Cancel" />
              <input class="btn btn-success" type="submit" value="Save"></input>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default ReportCreate;

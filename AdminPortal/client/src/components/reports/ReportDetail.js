import React, { useState, useEffect } from "react";
import auth from "./../auth/auth-helper";
import { getReportById, updateReport } from "./api-report.js";
import { useParams, useHistory } from "react-router-dom";
import GoogleMapReact from "google-map-react";

function ReportDetail() {
  const [report, setReport] = useState({});
  const [solution, setSolution] = useState("");
  const [statusCode, setStatusCode] = useState("");
 
  let { id } = useParams();
  let history = useHistory();
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (auth.isAuthenticated) {
      console.log({ solution: solution, statusCode: statusCode });
      //alert('hi');

      await updateReport(auth.isAuthenticated().token, id, {
        solution: solution,
        statusCode: statusCode,
      }).then((data) => {
        console.log(data);
        history.push("/reports");
      });
    }
  };
  //console.log('id: '+id);
  useEffect(() => {
    console.log({ solution: solution, statusCode: statusCode });
    const fetchData = async () => {
      if (auth.isAuthenticated) {
        //console.log('list report '+auth.isAuthenticated().token);

        try {
          getReportById(auth.isAuthenticated().token, id).then((data) => {
            if (data) {
              setReport(data);
              //console.log(data);
            }
          });
        } catch (e) {
          console.log(e);
        }
      }
    };
    fetchData();
  }, []);

  return (
  <div className="container d-flex" style={{ marginTop: "125px" }}>
      <div className="card shadow p-3">
        <div className="card-header">
          <h2>Report Details</h2>
        </div>

        <div className="card-body">
          <table>
            <tbody>
              <tr>
                <td>
                  <b>
                    <label htmlFor="ReportId">Report Id </label>
                  </b>
                </td>
                <td>{report.reportId}</td>
              </tr>
              <tr>
                <td>
                  <b>
                    <label htmlFor="Description">Description </label>
                  </b>
                </td>
                <td>{report.description}</td>
              </tr>
              <tr>
                <td>
                  <b>
                    <label htmlFor="DateTimeReport">Time Report </label>
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
                <td colSpan="2">
                  <b>Map Location </b>
                </td>
              </tr>
              <tr>
                <td colSpan="2">
                  <div
                    id="googleMap"
                    style={{ width: "500px", height: "380px" }}
                  >
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
                <td colSpan="2">
                  <b>
                    <label htmlFor="Picture">Picture </label>
                  </b>
                </td>
              </tr>
              <tr>
                <td colSpan="2">
                  <img
                    src={`data:image/jpeg;base64,${report.picture}`}
                    alt="No Available"
                    style={{ width: "500px" }}
                  />
                </td>
              </tr>
            </tbody>{" "}
          </table>
        </div>

        <div className="card-footer">
          <strong>Add/Edit a Solution</strong>
          <form onSubmit={handleSubmit}>
            <table className="table">
              <tbody>
                <tr>
                  <td colSpan="2">
                    <textarea
                      name="Solution"
                      className="form-control"
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
                      className="form-control"
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

export default ReportDetail;

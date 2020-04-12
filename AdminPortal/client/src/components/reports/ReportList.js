import React, { useState, useEffect } from "react";
import axios from "axios";
import auth from "./../auth/auth-helper";
import { useHistory, Link } from "react-router-dom";
import { getAllReport, deleteReport } from "./api-report.js";

function ReportList() {
  const [data, setData] = useState([]);
  const [error, setError] = useState(false);

  const handleDelete = async (e, id) => {
    e.preventDefault();
    try {
      await deleteReport(auth.isAuthenticated().token, id);
      console.log("delete");
      window.location.reload(false);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      if (auth.isAuthenticated) {
        //console.log('list report '+auth.isAuthenticated().token);

        try {
          getAllReport(auth.isAuthenticated().token).then((data) => {
            if (data) {
              setData(data);
            }
          });
        } catch (e) {
          console.log(e);
        }
      }
    };
    fetchData();
  }, []);

  const renderSwitch = (param) => {
    switch (param) {
      case 0:
        return "Opened";
      case 1:
        return "Investigation Requested";
      case 2:
        return "Investigation Returned";
      case 3:
        return "Solution Requested";
      case 4:
        return "Solved";
    }
  };

  return (
    <div className="container" style={{ marginTop: "125px" }}>
      <div className="card">
        <div className="card-header">
          <h3 className="pull-left">List of Reports</h3>{" "}
          <div className="pull-right">
            <Link
              className="btn btn-primary"
              to="/report/create"
              style={{
                background: "rgb(216, 27, 96)",
                borderColor: "#df4242",
                fontWeight: "bold",
              }}
            >
              Create Report
            </Link>
          </div>
        </div>
        <div className="card-body">
          <table className="table table-striped">
            <thead className="text-white" style={{ background: "#D81B60" }}>
              <tr>
                <th>Report Id</th>
                <th>Description</th>
                <th>Latitude</th>
                <th>Longtitude</th>
                <th>Time</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {data.map((value) => (
                <tr key={value.reportId}>
                  <td>{value.reportId}</td>
                  <td>{value.description}</td>
                  <td>{value.latitude}</td>
                  <td>{value.longitude}</td>
                  <td>
                    {new Intl.DateTimeFormat("en-US", {
                      year: "numeric",
                      month: "short",
                      day: "2-digit",
                      hour: "2-digit",
                      minute: "2-digit",
                    }).format(value.DateTimeReport)}
                  </td>
                  <td>{renderSwitch(value.statusCode)}</td>
                  <td>
                    <a href={"/report/edit/" + value.reportId}>
                      <i className="fa fa-edit text-body"></i>
                    </a>{" "}
                    |{" "}
                    <a
                      href="#"
                      onClick={(e) => handleDelete(e, value.reportId)}
                    >
                      <i className="fa fa-trash text-body"></i>
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default ReportList;

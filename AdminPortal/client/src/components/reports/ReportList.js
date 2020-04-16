import React, { useState, useEffect } from "react";
import auth from "./../auth/auth-helper";
import { Link } from "react-router-dom";
import { getAllReport, deleteReport, sendNotification } from "./api-report.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

function ReportList() {
  const [data, setData] = useState([]);
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

  const handleNotify = async (e, message) => {
    e.preventDefault();
    try {
      await sendNotification(message);
      console.log("delete");
      setShowMessageSent(true);
    } catch (e) {
      console.log(e);
    }
  };
  const [showMessageSent, setShowMessageSent] = useState(false);

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
  }, [showMessageSent]);

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
      default: 
      return "Solved";
    }
  };

  return (
    <div className="container">
      <Alert
        show={showMessageSent}
        variant="success"
        className="mt-2"
        style={{ maxWidth: "30em" }}
      >
        <Alert.Heading>
          <FontAwesomeIcon className="" icon={faPaperPlane} color="green" />
        </Alert.Heading>
        <p>Notification sent!</p>
        <hr />
        <div className="d-flex justify-content-end">
          <Button
            onClick={() => setShowMessageSent(false)}
            variant="outline-success"
          >
            OK
          </Button>
        </div>
      </Alert>
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
                        <i className=" m-1 fa fa-edit text-body"></i>
                      </a>

                      <a
                        href="#"
                        onClick={(e) => handleDelete(e, value.reportId)}
                      >
                        <i className=" m-1 fa fa-trash text-body"></i>
                      </a>
                      <a
                        href="#"
                        onClick={(e) =>
                          handleNotify(
                            e,
                            "ParroSays Alert - " + value.description
                          )
                        }
                      >
                        <i className="m-1 fa fa-paper-plane text-body"></i>
                      </a>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReportList;

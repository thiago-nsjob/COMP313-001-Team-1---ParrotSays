import axios from "axios";

const POST_API_ROOT = process.env.REACT_APP_POSTS_API;
//API Manager for Reports
const getAllReport = (token) => {
  //console.log({username,password});
  return axios
    .get("/reports/getall", { headers: { Authorization: `Bearer ${token}` } })
    .then((response) => {
      return response.data;
    })
    .catch((err) => console.log(err));
};

//get by id
const getReportById = (token, id) => {
  return axios
    .get("/reports/getreport/" + id, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((err) => console.log(err));
};

//update status code and solution on report
const updateReport = (token, id, report) => {
  console.log(report);

  return axios
    .put("/reports/updatereport/" + id, report, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((err) => console.log(err));
};

//delete
const deleteReport = (token, id) => {
  //console.log(report);
  try {
    axios.delete("/reports/delreport/" + id, {
      headers: { Authorization: `Bearer ${token}` },
    });
    //console.log(response);
  } catch (err) {
    console.log(err);
  }
};

//update status code and solution on report
const createReport = (token, report) => {
  return axios
    .post("/reports/addreport", report, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((err) => console.log(err));
};

//update status code and solution on report
const sendNotification = (message) => {
  return axios
    .post(POST_API_ROOT + "/notification", { message: message }, {})
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((err) => console.log(err));
};

export {
  getAllReport,
  getReportById,
  updateReport,
  deleteReport,
  createReport,
  sendNotification,
};

import axios from "axios";
//API Manager for Reports
const getAllReport = (token) => {
  //console.log({username,password});
  return axios
    .get("http://parrotsays.tk:8167/api/reports/getall", { headers: { Authorization: `Bearer ${token}` } })
    .then((response) => {
      return response.data;
    })
    .catch((err) => console.log(err));
};

//get by id
const getReportById = (token, id) => {
  return axios
    .get("http://parrotsays.tk:8167/api/reports/getreport/" + id, {
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
    .put("http://parrotsays.tk:8167/api/reports/updatereport/" + id, report, {
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
    axios.delete("http://parrotsays.tk:8167/api/reports/delreport/" + id, {
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
    .post("http://parrotsays.tk:8167/api/reports/addreport", report, {
      headers: { Authorization: `Bearer ${token}` },
    })
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
};

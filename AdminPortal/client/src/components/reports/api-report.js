import axios from 'axios';
//API Manager for Reports
const getAllReport = (token) => {
    //console.log({username,password});
    return  axios.get('/reports/getall',{ headers: {"Authorization" : `Bearer ${token}`} })
                .then((response) => {
                    //console.log(response);
                    return response.data;
                })
                .catch((err) => console.log(err))   
}

//get by id
const getReportById = (token,id) => {

    return axios.get('/reports/getreport/'+ id,{ headers: {"Authorization" : `Bearer ${token}`} }).then((response) => {
        console.log(response)
        return response.data;
    }).catch((err) => console.log(err)) 


}

//edit 

//delete


  
export {
    getAllReport,getReportById
}
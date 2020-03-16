import axios from 'axios';
//API Manager for Reports
const getAllReport = (token) => {
    //console.log({username,password});
    return  axios.get('/reports/getall',{ headers: {"Authorization" : `Bearer ${token}`} })
                .then((response) => {return response.data})
                .catch((err) => console.log(err))   
}

//get by id


//edit 

//delete


  
export {
    getAllReport
}
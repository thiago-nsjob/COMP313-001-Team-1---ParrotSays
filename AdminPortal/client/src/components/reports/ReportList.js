import React, { useState, useEffect } from 'react';
<<<<<<< HEAD
 import axios from 'axios';
=======
import axios from 'axios';
>>>>>>> ee321bcc3e5d040ed7ad2e29418da7eb9be2fc8f
import auth from './../auth/auth-helper';
import {getAllReport} from './api-report.js';

function ReportList(){
    
    const [data, setData] = useState([]);
    const [error, setError] = useState(false);
    const [redirect, setRedirect] = useState(false);
    
    useEffect(() => {
        const fetchData = async () => {
                if(auth.isAuthenticated){
                    //console.log('list report '+auth.isAuthenticated().token);
                    try{
                        getAllReport(auth.isAuthenticated().token).then((data)=>{
                            if(data.error)
                            {
                                console.log(error);
                            }
                            else {
                                //console.log(data);
                                setData(data);
                            }
                        });
                    } catch(e) {
                        console.log(e);
                    }
                }
          };  
        fetchData();
      }, []);
    
      return (
        <div className="container" style={{marginTop: "125px"}}>
            <div className="card">
            <div className="card-header"><h3>List of Reports</h3></div>
            <div className="card-body">
            <table className="table table-striped">
            <thead class="text-white" style={{background:"#D81B60"}}>
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
                    {data.map((value) =>      
                            <tr key={value.reportId}>
                                <td>{value.reportId}</td>
                                <td>{value.description}</td>
                                <td>{value.latitude}</td>
                                <td>{value.longitude}</td>
                                <td>{value.dateTimeReport}</td>
                                <td>{value.statusCode}</td>
                                <td><a href={'/edit?id='+ value.reportId}><i className="fa fa-edit text-body"></i></a> | <a href={'/delete?id=' + value.reportId}><i className="fa fa-trash text-body"></i></a></td>
                            </tr>       
                    )}   
                </tbody>
            </table>
            </div>
           
            </div>
          
        </div>

      
    );

    
}




export default ReportList;
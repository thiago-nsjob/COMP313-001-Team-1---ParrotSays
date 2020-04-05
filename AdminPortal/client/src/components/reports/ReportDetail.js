import React, { useState, useEffect } from 'react';
import auth from './../auth/auth-helper';
import {getReportById} from './api-report.js';
import {useParams} from "react-router-dom";


function ReportDetail(){

    const [report, setReport] = useState({});
    const [error, setError] = useState(false);
    const [redirect, setRedirect] = useState(false);
    let { id } = useParams();

    const onSubmit = (event) =>{

    }
    //console.log('id: '+id);
    useEffect(() => {
       
        const fetchData = async () => {
                if(auth.isAuthenticated){
                    //console.log('list report '+auth.isAuthenticated().token);
                    
                    try{
                        getReportById(auth.isAuthenticated().token,id).then((data)=>{
                                if(data)
                                {
                                    setReport(data);
                                    //console.log(data);
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
        <div className="container d-flex" style={{marginTop: "125px"}}>
            <div className="card shadow p-3">
                <div className="card-header">
                    <h2>Report Details</h2>
                </div>
                
                <div className="card-body">
                
                    <table className="table table-striped">  
                    <tr>  
	                    <td><b><label for="ReportId">Report Id </label></b></td>  
                        <td>{report.reportId}</td>
	                </tr> 
                    <tr>  
	                    <td><b><label for="Description">Description </label></b></td>  
	                    <td>{report.description}</td>
	                </tr>  
                    <tr>  
	                    <td><b><label for="DateTimeReport">Time Report </label></b></td>  
                        <td>{report.DateTimeReport}</td>  
	                </tr>
                    <tr>
	                	<td colspan="2">
	                		<b>Map Location </b>
	                	</td>
	                </tr>
                    <tr>
		                <td colspan="2">
		                	<div id="googleMap" style={{width:"500px",height:"380px"}}></div>	                	
		                </td>
	                </tr>
                    <tr>
	                	<td colspan="2">
	                		<b><label for="Picture">Picture </label></b>
	                	</td>
	                </tr>
	                <tr>
	                	<td colspan="2">
	                		<img src={report.picture} style={{width:"500px"}} alt="No Picture"/>
	                	</td>
	                </tr>
                        
                    </table>  
                </div>
                
                
                <div class="card-footer">
                <strong>Add/Edit a Solution</strong>
	            <form action="saveSolution" onSubmit={onSubmit} method="post">  
	            
				  	<table class="table">
				  		
						<tbody>
							<tr>
								<td colspan="2">
									<input type="hidden" name="ReportId" value={report.reportId}></input>
									Solution
								</td>
							</tr>
							<tr>
								<td colspan="2">
			                    	 <textarea name="Solution" class="form-control" rows="10" cols="50" ></textarea> 
			                    	 <br></br>
			                    </td>
                    		</tr>  
                    		<tr>
                    			<td>Status</td>
                    			<td> 
		                    		<select class="form-control" id="sel1" name="StatusCode">
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
						<input class="btn btn-danger" type="reset" value="Cancel"/>
						<input class="btn btn-success" type="submit" value="Save"></input>
		  			</div>
				</form> 
                </div>
            </div> 
      </div>
    );
    
    
}

export default ReportDetail;
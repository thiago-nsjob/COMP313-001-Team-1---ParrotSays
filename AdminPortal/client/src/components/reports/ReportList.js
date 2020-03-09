import React, { Component } from 'react';

class ReportList extends Component{
    
render() {

    const elements = [
        {
            id:1,
            description: 'Alvin',
            latitude: 'Eclair',
            longtitude: '0.87',
            time: 'Dec 02,2019 - 17:10',
            status: 2
        },
        {
            id:2,
            description: 'Kevin',
            latitude: 'Aclair',
            longtitude: '$0.87',
            time: 'Feb 23,2020 - 15:12',
            status: 3
        }
    ];

    const items = elements.map((value) =>      
            <tr key={value.id}>
                <td>{value.id}</td>
                <td>{value.description}</td>
                <td>{value.latitude}</td>
                <td>{value.longtitude}</td>
                <td>{value.time}</td>
                <td>{value.status}</td>
                <td><a href={'/edit?id='+ value.id}>Edit</a> | <a href={'/delete?id=' + value.id}>Delete</a></td>
            </tr>       
    );

    return (
        <div class="container">
            <div class="card">
            <div class="card-header"><h3>List of Reports</h3></div>
            <div class="card-body">
            <table class="table table-striped">
                <thead>
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
                    {items}   
                </tbody>
            </table>
            </div>
           
            </div>
          
        </div>

      
    )

    }
}




export default ReportList;
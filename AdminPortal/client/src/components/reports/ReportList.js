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
                <td><a>Edit</a> | <a>Delete</a></td>
            </tr>       
    );

    return (
        <table className="striped">
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
    )

    }
}




export default ReportList;
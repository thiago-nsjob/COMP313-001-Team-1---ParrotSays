import React, {Component} from 'react';

class About extends Component {
    render(){
      return(
        <div className="container" style={{marginTop: "125px"}}>
        <div className="jumbotron">
        <img src="/images/ic_launcher_foreground.png" alt="logo" className="card rounded"/>
   
          <h2>About</h2>      
          Developed by Team 1:<br />
          Julio Vinicius A. de Carvalho<br/>
              Thiago Silva<br/>
              Eduardo Santana<br/>
              Ydelma Rangel<br/>
              Andrea de la Isla<br/>
              Quang Trung Trinh
           
              <br/>
          <p>For Software Development Project 2 (COMP313).</p>
        </div>
             
      </div>
      ) 
    }
}

export default About;
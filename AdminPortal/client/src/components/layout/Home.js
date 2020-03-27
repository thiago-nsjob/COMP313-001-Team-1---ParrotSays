import React, {Component} from 'react';

class Home extends Component {
    render(){
      return(
        <div>
          	<br/>
            <br/>
            <div className="container" style={{marginTop: "125px"}}>
              <div className="jumbotron">
                <img src="/images/ic_launcher_foreground.png" alt="logo" className="card"></img>
                <h2>Welcome to <b>Parrot Says</b></h2>      
                <p>Please, select your option at the menu.</p>
              </div>
            </div>
        </div>
      ) 
    }
}

export default Home;
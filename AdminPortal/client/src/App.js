import React from 'react';
import {BrowserRouter, Switch,Route} from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Home from './components/home/Home';
import SignIn from './components/auth/SignIn';
import SignUp from './components/auth/SignUp';

function App() {
  return (
    <BrowserRouter>
      <div className="App">    
        <Navbar />
        <Switch>
          <Route exact path ='/' component={Home}/>
          <Route path = '/signin' component={SignIn}/>
          <Route path = '/signup' component={SignUp}/>
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;

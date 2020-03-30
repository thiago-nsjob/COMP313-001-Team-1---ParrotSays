import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Menu from './components/layout/Menu';
import Home from './components/layout/Home';
import SignUp from './components/users/SignUp';
import ReportList from './components/reports/ReportList';
import ReportDetail from './components/reports/ReportDetail';
import PostList from './components/posts/PostList';
import SignIn from './components/users/SignIn';

function App() {
 
  return (
    <BrowserRouter>
      <div className="App">    
        <Menu />
        <Switch>
          <Route exact path ='/' component={Home}/>
          <Route path = '/signin' component={SignIn}/>
          <Route path = '/signup' component={SignUp}/>
          <Route path = '/reports' component={ReportList}/>
          <Route path = '/posts' component={PostList}/>
          <Route path = '/edit' component={ReportDetail} />
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;

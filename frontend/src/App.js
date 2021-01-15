import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import IndexPage from "./pages/IndexPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import PharmacyPage from "./pages/PharmacyPage";


export default class App extends React.Component {
  constructor () {
    super();
    this.state = {
      userRole : "",
      username : "",
    }
  };

  render() {
    const role = "Admin";
    return (
        <BrowserRouter>
          <Switch>
            <Route exact path="/"  component={IndexPage} role={role}/>
            <Route path="/patient-profile" component={PatientProfilePage} role={role}/>
            <Route path="/pharmacy"  component={PharmacyPage} role={role}/>
          </Switch>
        </BrowserRouter>
    );
  }

  updateUserData = (role, username) => {
    this.setState({
      userRole : role,
      username : username
    })
  }

}

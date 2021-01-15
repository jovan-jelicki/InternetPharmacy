import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import IndexPage from "./pages/IndexPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import PharmacyPage from "./pages/PharmacyPage";
import ReviewedClients from "./pages/ReviewedClients";
import VacationRequest from "./pages/VacationRequest";
import DermatologistHomePage from "./pages/DermatologistHomePage";


export default class App extends React.Component {
  constructor () {
    super();
    this.state = {
      userRole : "",
      username : "",
      Id : ""
    }
  };

  render() {
    const role = "Admin";
    const Id = this.state.Id;
    return (
        <BrowserRouter>
          <Switch>
            <Route exact path="/"  render={(props) => <IndexPage {...props} role={role} /> } />
            <Route path="/patient-profile" render={(props) => <PatientProfilePage {...props} role={role} /> } />
            <Route path="/pharmacy"  component={PharmacyPage} role={role}/>
            <Route path="/reviewClients"  render={(props) => <ReviewedClients {...props} role={role} Id={Id}/> } />
            <Route path="/vacationRequest" render={(props) => <VacationRequest {...props} role={role} Id={Id}/> } />
            <Route path="/dermatologistHomePage"  render={(props) => <DermatologistHomePage {...props} role={role} Id={Id}/> } />
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

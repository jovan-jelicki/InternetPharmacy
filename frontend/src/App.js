import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import IndexPage from "./pages/IndexPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import PharmacyPage from "./pages/PharmacyPage";
import ReviewedClients from "./pages/ReviewedClients";
import VacationRequest from "./pages/VacationRequest";
import DermatologistHomePage from "./pages/DermatologistHomePage";
import PharmacistProfilePage from "./pages/PharmacistProfilePage";
import PharmacistHomePage from "./pages/PharmacistHomePage";
import PatientHomePage from "./pages/PatientHomePage";
import PharmacistWorkingHours from "./pages/PharmacistWorkingHours";
import Registration from "./pages/Registration";
import PharmacyAdminProfilePage from "./pages/PharmacyAdminProfilePage";


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
    document.title = "Internet Pharmacy"
    return (
        <BrowserRouter>
          <Switch>
            <Route exact path="/"  render={(props) => <IndexPage {...props} role={role} /> } />
            <Route path="/patient-profile" render={(props) => <PatientProfilePage {...props} role={role} /> } />
            <Route path="/pharmacy-admin-profile" render={(props) => <PharmacyAdminProfilePage {...props} role={role} /> } />
            <Route path="/pharmacy"  component={PharmacyPage} role={role}/>
            <Route path="/reviewClients"  render={(props) => <ReviewedClients {...props} role={role} Id={Id}/> } />
            <Route path="/vacationRequest" render={(props) => <VacationRequest {...props} role={role} Id={Id}/> } />
            <Route path='/patient-home' render={(props) => <PatientHomePage {...props} role={role} Id={Id}/> }/>
            <Route path="/dermatologistHomePage"  render={(props) => <DermatologistHomePage {...props} role={role} Id={Id}/> } />
            <Route path="/pharmacistHomePage"  render={(props) => <PharmacistHomePage {...props} role={role} Id={Id}/> } />
            <Route path="/registration"  component={Registration} role={role}/>

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

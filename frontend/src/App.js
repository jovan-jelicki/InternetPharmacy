import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import IndexPage from "./pages/IndexPage";
import PatientProfilePage from "./pages/patient/PatientProfilePage";
import PharmacyPage from "./pages/PharmacyPage";
import ReviewedClients from "./pages/ReviewedClients";
import VacationRequest from "./pages/VacationRequest";
import PatientHomePage from "./pages/patient/PatientHomePage";
import DermatologistHomePage from "./pages/Dermatologist/DermatologistHomePage";
import PharmacistProfilePage from "./pages/Pharmacist/PharmacistProfilePage";
import PharmacistHomePage from "./pages/Pharmacist/PharmacistHomePage";
import PharmacistWorkingHours from "./pages/Pharmacist/PharmacistWorkingHours";
import Registration from "./pages/Registration";
import {SystemAdminHomePage} from "./pages/SystemAdminHomePage";
import PharmacyAdminProfilePage from "./pages/PharmacyAdminProfilePage";
import SupplierHomePage from "./pages/SupplierHomePage";
import CreateNewOffer from "./components/Supplier/CreateNewOffer";
import PatientCounselScheduling from "./pages/patient/PatientCounselScheduling";
import RegistrationConfirmation from "./pages/RegistrationConfirmation";
import PatientScheduledAppointments from "./pages/patient/PatientScheduledAppointments";
import PatientPreviousAppointments from "./pages/patient/PatientPreviousAppointments";
import PatientGradingPage from "./pages/patient/PatientGradingPage"
import PatientPharmacyPromotions from "./pages/patient/PatientPharmacyPromotions"
import CreateCoplaint from "./components/CreateCoplaint"
import PatientEPrescriptionPage from './pages/patient/PatientEPrescriptionPage'
import PatientReservationsPage from './pages/patient/PatientReservationsPage'

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
            <Route path='/patient-home-promo' render={(props) => <PatientPharmacyPromotions {...props} role={role} Id={Id}/> }/>
            <Route path="/dermatologistHomePage"  render={(props) => <DermatologistHomePage {...props} role={role} Id={Id}/> } />
            <Route path="/pharmacistHomePage"  render={(props) => <PharmacistHomePage {...props} role={role} Id={Id}/> } />
            <Route path="/patient-counsel-schedule"  render={(props) => <PatientCounselScheduling {...props} role={role} Id={Id}/> } />
            <Route path="/registration"  component={Registration} role={role}/>
            <Route path="/systemAdmin"  render={(props) => <SystemAdminHomePage {...props} role={role} Id={Id}/> }/>
            <Route path="/supplierHomePage"  render={(props) => <SupplierHomePage {...props} role={role} Id={Id}/> }/>
            <Route path="/createNew" component={CreateNewOffer} role={role}/>
            <Route path="/confirmRegistration" component={RegistrationConfirmation} role={role}/>
            <Route path="/scheduled-appointments" component={PatientScheduledAppointments} role={role}/>
            <Route path="/scheduled-appointments-history" component={PatientPreviousAppointments} role={role}/>
            <Route path="/grading" component={PatientGradingPage} role={role}/>
            <Route path="/complaint" component={CreateCoplaint} role={role}/>
            <Route path="/patient-eprescription" component={PatientEPrescriptionPage} role={role}/>
            <Route path="/patient-reservations" component={PatientReservationsPage} role={role}/>
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

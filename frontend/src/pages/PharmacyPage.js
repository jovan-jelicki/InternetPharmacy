import React from 'react';
import Promotions from "../components/pharmacy/Promotions";
import PharmacyEmployees from "../components/pharmacy/PharmacyEmployees";
import PharmacyMedications from "../components/pharmacy/PharmacyMedications";
import PharmacyVacationsRequests from "../components/pharmacy/PharmacyVacationsRequests";
import PharmacyMedicationOrders from "../components/pharmacy/PharmacyMedicationOrders";
import PharmacyMedicationQueries from "../components/pharmacy/PharmacyMedicationQueries";
import PharmacyDescription from "../components/pharmacy/PharmacyDescription";
import PriceList from "../components/pharmacy/PriceList";
import AppointmentsList from "../components/pharmacy/AppointmentsList";
import PharmacyProfile from "../components/pharmacy/PharmacyProfile";
import axios from "axios";
import PharmacyCharts from "../components/pharmacy/PharmacyCharts";
import AuthentificationService from "../helpers/AuthentificationService";
import HelperService from "../helpers/HelperService";

//pristupas sa  this.props.location.state.user.email
export default class PharmacyPage extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            pharmacy : {
                id : 0,
                name : "",
                address : {
                    street : "",
                    town : "",
                    country : "",
                    latitude : 51.507351,
                    longitude : -0.127758
                },
                description : "",
                medicationQuantity : [],
                grade : 0
            },
            navbar : "description",
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : sessionStorage.getItem("pharmacyId") || this.props.location.state.pharmacyId

    }
    }

    async componentDidMount() {
        this.validateUser();
        //await this.fetchPharmacyId();
        await this.fetchPharmacy();
    }


    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.pharmacy.name}</h1>
                </div>
                <br/>

                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="description" onClick={this.handleChange}>Description</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="employees">Dermatologists & pharmacists</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="appointments">Dermatologist appointments</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medications" onClick={this.handleChange}>Medications</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="vacationRequests" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Vacation requests</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="promotions" onClick={this.handleChange}>Actions & Promotions</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medicationOrders" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Orders & Offers</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medicationQueries" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Medication queries</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="priceList" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Price list</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="editPharmacyProfile" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Edit pharmacy profile</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="charts" onClick={this.handleChange}
                           style={this.state.user.type === 'ROLE_pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Charts</a>
                    </li>
                </ul>
                {this.renderNavbar()}
            </div>
        );
    }


    handleChange = (event) => {
        const target = event.target;
        const name = target.name;

        this.setState({
            navbar : name
        });
    }

    renderNavbar = () => {
        if (this.state.navbar === "promotions")
            return (
                <Promotions pharmacy = {this.state.pharmacy} />
            );
        else if (this.state.navbar === 'description')
            return (
                <PharmacyDescription pharmacy = {this.state.pharmacy } />
            )
        else if (this.state.navbar === 'editPharmacyProfile')
            return (
                <PharmacyProfile pharmacy = {this.state.pharmacy} triggerPharmacyDataChange={this.fetchPharmacy}/>
            )
        else if (this.state.navbar === 'appointments')
            return (
                <AppointmentsList pharmacy = {this.state.pharmacy } />
            )
        else if (this.state.navbar === 'priceList')
            return (
                <PriceList pharmacy = {this.state.pharmacy } mode = "showCurrentPriceLists"/>
            )
        else if (this.state.navbar === "employees")
            return (
                <PharmacyEmployees pharmacyId = {this.state.pharmacy.id}/>
            );
        else if (this.state.navbar === "medications")
            return (
                <PharmacyMedications pharmacy ={this.state.pharmacy} />
            );
        else if (this.state.navbar === 'vacationRequests')
            return (
              <PharmacyVacationsRequests pharmacy ={this.state.pharmacy} />
            );
        else if (this.state.navbar === 'medicationOrders')
            return (
                <PharmacyMedicationOrders pharmacy ={this.state.pharmacy} showContent = 'listOrders'/>
            );
        else if (this.state.navbar === 'medicationQueries')
            return (
                <PharmacyMedicationQueries pharmacy ={this.state.pharmacy}/>
            );
        else if (this.state.navbar === 'charts')
            return (
                <PharmacyCharts pharmacy ={this.state.pharmacy}/>
            );
    }

    fetchPharmacy = async () => {
        await axios.get(HelperService.getPath("/api/pharmacy/" + this.state.pharmacyId),
        {
                headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                this.setState({
                    pharmacy : res.data
                })
            })
    }

    fetchPharmacyId = async () => {
        await axios.get(HelperService.getPath("/api/pharmacyAdmin/getPharmacyAdminPharmacy/" + this.state.user.id),
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    pharmacyId : res.data
                })
            });
    }

    validateUser = () => {
        if (!AuthentificationService.isLoggedIn() || (this.state.user.type !== 'ROLE_pharmacyAdmin' & this.state.user.type !== 'ROLE_patient'))
            this.props.history.push({
                pathname: "/unauthorized"
            });
    }
}

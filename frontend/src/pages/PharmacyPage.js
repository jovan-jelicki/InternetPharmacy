import React from 'react';
import Promotions from "../components/pharmacy/Promotions";
import PharmacyEmployees from "../components/pharmacy/PharmacyEmployees";
import PharmacyMedications from "../components/pharmacy/PharmacyMedications";
import PharmacyVacationsRequests from "../components/pharmacy/PharmacyVacationsRequests";
import PharmacyMedicationOrders from "../components/pharmacy/PharmacyMedicationOrders";
import PharmacyMedicationQueries from "../components/pharmacy/PharmacyMedicationQueries";
import PharmacyDescription from "../components/pharmacy/PharmacyDescription";
import PriceList from "../components/pharmacy/PriceList";
import PharmacyReports from "../components/pharmacy/PharmacyReports";
import AppointmentsList from "../components/pharmacy/AppointmentsList";
import PharmacyProfile from "../components/pharmacy/PharmacyProfile";
import axios from "axios";
import PharmacyCharts from "../components/pharmacy/PharmacyCharts";


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
            userType : "pharmacyAdmin"

        }
    }

    componentDidMount() {
       this.fetchPharmacy();
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
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="employees">Dermatolozi & farmaceuti</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="appointments">Pregledi dermatologa</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medications" onClick={this.handleChange}>Lekovi</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="vacationRequests" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Godisnji odmori</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="promotions" onClick={this.handleChange}>Akcije & promocije</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medicationOrders" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Narudzbenice & ponude</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medicationQueries" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Upiti nad lekovima</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="priceList" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Price list</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="editPharmacyProfile" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Edit pharmacy profile</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="charts" onClick={this.handleChange} style={this.state.userType === 'pharmacyAdmin' ? {display : 'block'} : {display : 'none'}}>Charts</a>
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
                <Promotions/>
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
                <PharmacyMedications/>
            );
        else if (this.state.navbar === 'vacationRequests')
            return (
              <PharmacyVacationsRequests />
            );
        else if (this.state.navbar === 'medicationOrders')
            return (
                <PharmacyMedicationOrders showContent = 'listOrders'/>
            );
        else if (this.state.navbar === 'medicationQueries')
            return (
                <PharmacyMedicationQueries />
            );
        else if (this.state.navbar === 'charts')
            return (
                <PharmacyCharts />
            );
    }

    fetchPharmacy = () => {
        axios.get("http://localhost:8080/api/pharmacy/1")
            .then((res) => {
                this.setState({
                    pharmacy : res.data
                })
            })
    }
}

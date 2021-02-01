import React from "react";
import PharmacyRegistration from "../components/PharmacyRegistration";
import DermatologistRegistration from "../components/DermatologistRegistration";
import SupplierRegistration from "../components/SupplierRegistration";
import AddNewMedication from "../components/AddNewMedication";
import Complaints from "../components/Complaints";
import LoyaltyProgram from "../components/LoyaltyProgram";
import PharmacyAdminRegistration from "../components/PharmacyAdminRegistration";
import SystemAdminVacationRequestListing from "../components/SystemAdminVacationRequestListing";

export class ISAdminHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state= {
            pharmacyAdmin:{
                firstName:'',
                lastName:'',
            },
            navbar : "",
        }
    }
    componentDidMount() {
        let  pharmacyAdmin= {
            firstName: 'Snezana',
            lastName: 'Bulajic',
        }
        this.setState({
            pharmacyAdmin : pharmacyAdmin
        })
    }
    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.pharmacyAdmin.firstName +" " +this.state.pharmacyAdmin.lastName }</h1>
                </div>

                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="pharmacy">Pharmacy registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="dermatologist" onClick={this.handleChange}>Dermatologist registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="supplier" onClick={this.handleChange}>Supplier registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medication" onClick={this.handleChange}>Add medication</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="complaint" onClick={this.handleChange}>Complaint</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="loyalty" onClick={this.handleChange}>Loyalty program</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="pharmacyAdmin" onClick={this.handleChange}>Add new pharmacy admin</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="dermatologistVacationRequests" onClick={this.handleChange}>Dermatologist vacation requests</a>
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
        if (this.state.navbar === "pharmacy")
            return (
                <PharmacyRegistration/>
            );
        else if (this.state.navbar === "dermatologist")
            return (
                <DermatologistRegistration/>
            );
        else if (this.state.navbar === "supplier")
            return (
                <SupplierRegistration/>
            );
        else if (this.state.navbar === "medication")
            return (
                <AddNewMedication/>
            );
        else if (this.state.navbar === "complaint")
            return (
                <Complaints/>
            );
        else if (this.state.navbar === "loyalty")
            return (
                <LoyaltyProgram/>
            );
        else if (this.state.navbar === "pharmacyAdmin")
            return (
                <PharmacyAdminRegistration/>
            );
        else if (this.state.navbar === "dermatologistVacationRequests")
            return (
                <SystemAdminVacationRequestListing/>
            );
    }
}
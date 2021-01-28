import React from "react";
import PharmacyRegistration from "../components/PharmacyRegistration";
import DermatologistRegistration from "../components/DermatologistRegistration";
import SupplierRegistration from "../components/SupplierRegistration";
import AddNewMedication from "../components/AddNewMedication";
import Complaints from "../components/Complaints";
import LoyaltyProgram from "../components/LoyaltyProgram";
import PharmacyAdminRegistration from "../components/PharmacyAdminRegistration";
import MedicationOrdersForSupplier from "../components/Supplier/MedicationOrdersForSupplier";
import MedicationOrdersList from "../components/pharmacy/MedicationOrdersList";
import MedicationOffers from "../components/pharmacy/MedicationOffers";
import CreateOrder from "../components/pharmacy/CreateOrder";
import CreateNewOffer from "../components/Supplier/CreateNewOffer";
import SupplierMedicationOffers from "../components/Supplier/SupplierMedicationOffers";
import SupplierProfile from "../components/Supplier/SupplierProfile";

export default class SupplierHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state= {
            supplier:{
                firstName:'',
                lastName:'',
            },
            navbar : "",
            showContent:""
        }
    }
    componentDidMount() {
        let  supplier= {
            firstName: 'Manja',
            lastName: 'Babic',
        }
        this.setState({
            supplier : supplier
        })
    }

    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.supplier.firstName +" " +this.state.supplier.lastName }</h1>
                </div>

                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="order">Medication orders</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="offer" onClick={this.handleChange}>My medication offers</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="profile" onClick={this.handleChange}>Profile</a>
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
        if (this.state.navbar === "order")
            return (
                <MedicationOrdersForSupplier/>
            );
        else if (this.state.navbar === "offer")
            return (
                <SupplierMedicationOffers/>
            );
        else if (this.state.navbar === "profile")
            return (
                <SupplierProfile/>
            );

    }



}
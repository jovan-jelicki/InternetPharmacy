import React from 'react';
import Promotions from "../components/pharmacy/Promotions";
import PharmacyEmployees from "../components/pharmacy/PharmacyEmployees";
import PharmacyMedications from "../components/pharmacy/PharmacyMedications";
import PharmacyVacationsRequests from "../components/pharmacy/PharmacyVacationsRequests";
import PharmacyMedicationOrders from "../components/pharmacy/PharmacyMedicationOrders";
import PharmacyMedicationQueries from "../components/pharmacy/PharmacyMedicationQueries";
import PharmacyDescription from "../components/pharmacy/PharmacyDescription";


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
                dermatologist : [],
                pharmacist : [],
                medicationQuantity : [],
                medicationReservation : [],
                grade : 0
            },
            navbar : "description",
            userType : "pharmacyAdmin"

        }
    }

    componentDidMount() {
        let pharmacy = {
            id: 0,
            name: "Jankovic",
            grade : 3.89,
            address: {
                street: "Gunduliceva 1A",
                town: "Novi Sad",
                country: "Serbia",
                longitude: -0.118092,
                latitude: 51.509865
            },
            description: "Apoteka za sve!",
            dermatologist: [
                {
                    id: 0,
                    firstName: "Marko",
                    lastName: "Markovic",
                    userType: "dermatologist"
                }
            ],
            pharmacist: [
                {
                    id: 0,
                    firstName: "Dragana",
                    lastName: "Markovic",
                    userType: "dermatologist"
                }
            ],
        }
        this.setState({
            pharmacy : pharmacy
        })
    }




    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.pharmacy.name}</h1>
                    <p className="lead">{this.state.pharmacy.description}</p>
                    <p className="lead">{'ocena apoteke : '+ this.state.pharmacy.grade}</p>
                </div>


                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="description" onClick={this.handleChange}>Description</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="employees">Dermatolozi & farmaceuti</a>
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
    }
}

import React from 'react';
import Promotions from "../components/pharmacy/Promotions";
import PharmacyEmployees from "../components/pharmacy/PharmacyEmployees";
import PharmacyMedications from "../components/pharmacy/PharmacyMedications";
import PharmacyVacationsRequests from "../components/pharmacy/PharmacyVacationsRequests";
import PharmacyMedicationOrders from "../components/pharmacy/PharmacyMedicationOrders";
import PharmacyMedicationQueries from "../components/pharmacy/PharmacyMedicationQueries";
import {GoogleApiWrapper} from "google-maps-react";
import {PharmacyPage} from "../pages/PharmacyPage";

export class  PharmacyLayout extends React.Component {

    render() {
        const Page = () => this.props.children;

        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.pharmacy.name}</h1>
                    <p className="lead">{this.state.pharmacy.description}</p>
                    <p className="lead">{'ocena apoteke : '+ this.state.pharmacy.grade}</p>
                </div>
                {/*<Map*/}
                {/*    google={this.props.google}*/}
                {/*    zoom={14}*/}
                {/*    style={mapStyles}*/}
                {/*    initialCenter={*/}
                {/*        {*/}
                {/*            lat: this.state.pharmacy.address.latitude,*/}
                {/*            lng: this.state.pharmacy.address.longitude*/}
                {/*        }*/}
                {/*    }*/}
                {/*>*/}
                {/*    <Marker*/}
                {/*        onClick={this.onMarkerClick}*/}
                {/*        name={this.state.pharmacy.address.street}*/}
                {/*    />*/}
                {/*    <InfoWindow*/}
                {/*        marker={this.state.activeMarker}*/}
                {/*        visible={this.state.showingInfoWindow}*/}
                {/*        onClose={this.onClose}*/}
                {/*     >*/}
                {/*        <div>*/}
                {/*            <h4>{this.state.selectedPlace.name}</h4>*/}
                {/*        </div>*/}
                {/*    </InfoWindow>*/}
                {/*</Map>*/}


                <ul className="nav justify-content-center">
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
                <Page/>
            </div>
        );
    }

    onMarkerClick = (props, marker, e) =>
        this.setState({
            selectedPlace: props,
            activeMarker: marker,
            showingInfoWindow: true
        });

    onClose = props => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            });
        }
    };

    renderEmployees = (event) => {
        event.preventDefault();
        this.setState({
            navbar : "employees"
        })
    }


    renderMedications = (event) => {
        event.preventDefault();
        this.setState({
            navbar : "medications"
        })
    }


    renderAppoinments = (event) => {
        event.preventDefault();
        this.setState({
            navbar : "appointments"
        })
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
                <PharmacyMedicationOrders />
            );
        else if (this.state.navbar === 'medicationQueries')
            return (
                <PharmacyMedicationQueries />
            );
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY'
})(PharmacyLayout)
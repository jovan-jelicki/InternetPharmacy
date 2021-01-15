import React from 'react';
import { Map, GoogleApiWrapper, InfoWindow, Marker} from 'google-maps-react';
import Promotions from "../components/Promotions";
import PharmacyEmployees from "../components/PharmacyEmployees";
import PharmacyMedications from "../components/PharmacyMedications";

const mapStyles = {
    width: '50%',
    height: '50%'
};

export class PharmacyPage extends React.Component{
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
            },

            showingInfoWindow: false,  // Hides or shows the InfoWindow
            activeMarker: {},          // Shows the active marker upon click
            selectedPlace: {},
            navbar : ""

        }
    }

    componentDidMount() {
        let pharmacy = {
            id: 0,
            name: "Jankovic",
            address: {
                street: "Gunduliceva 1A",
                town: "Novi Sad",
                country: "Serbia"
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
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="employees">Dermatolozi i farmaceuti</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medications" onClick={this.handleChange}>Lekovi</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="appointments" onClick={this.handleChange}>Slobodni termini</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="promotions" onClick={this.handleChange}>Akcije i promocije</a>
                    </li>
                </ul>
                {this.renderNavbar()}
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
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY'
})(PharmacyPage);
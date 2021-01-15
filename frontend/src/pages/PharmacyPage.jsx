import React from 'react';
import { Map, GoogleApiWrapper, InfoWindow, Marker} from 'google-maps-react';
import Promotions from "../component/Promotions";

const mapStyles = {
    width: '10%',
    height: '10%'
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
                    country : ""
                },
                description : "",
                dermatologist : [],
                pharmacist : [],
                medicationQuantity : [],
                medicationReservation : [],
            },

            showingInfoWindow: false,  // Hides or shows the InfoWindow
            activeMarker: {},          // Shows the active marker upon click
            selectedPlace: {}

        }
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

    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.pharmacy.name}Naslov</h1>
                    <p className="lead">{this.state.pharmacy.description}Opis</p>
                </div>
                <Map
                    google={this.props.google}
                    zoom={14}
                    style={mapStyles}
                    initialCenter={
                        {
                            lat: -1.2884,
                            lng: 36.8233
                        }
                    }
                >
                    <Marker
                        onClick={this.onMarkerClick}
                        name={this.state.pharmacy.address.street + 'ulica'}
                    />
                    <InfoWindow
                        marker={this.state.activeMarker}
                        visible={this.state.showingInfoWindow}
                        onClose={this.onClose}
                     >
                        <div>
                            <h4>{this.state.selectedPlace.name}</h4>
                        </div>
                    </InfoWindow>
                </Map>


                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link active" href="#">Dermatolozi i farmaceuti</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#">Lekovi</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#">Slobodni termini</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#">Akcije i promocije</a>
                    </li>
                </ul>


                <Promotions/>
            </div>
        );
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY'
})(PharmacyPage);
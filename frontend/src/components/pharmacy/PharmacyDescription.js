import React from 'react';
import { Map, GoogleApiWrapper, InfoWindow, Marker} from 'google-maps-react';
import {PharmacyPage} from "../../pages/PharmacyPage";

const mapStyles = {
    width: '50%',
    height: '50%'
};


export class PharmacyDescription extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userType : 'pharmacyAdmin',
            pharmacy : this.props.pharmacy,
            showingInfoWindow: false,  // Hides or shows the InfoWindow
            activeMarker: {},          // Shows the active marker upon click
            selectedPlace: {},
        }
    }


    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-12">
                            <div className="card">
                                <h5 className="card-header">
                                    {this.state.pharmacy.description}
                                </h5>
                                <div className="card-body">
                                    <p className="card-text">
                                        Address : {this.state.pharmacy.address.street} <br/>
                                        Grade : {this.state.pharmacy.grade} <br/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <Map
                    google={this.props.google}
                    zoom={14}
                    style={mapStyles}
                    initialCenter={
                        {
                            lat: this.state.pharmacy.address.latitude,
                            lng: this.state.pharmacy.address.longitude
                        }
                    }
                >
                    <Marker
                        onClick={this.onMarkerClick}
                        name={this.state.pharmacy.address.street}
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
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY'
})(PharmacyDescription)
import React from 'react';
import { Map, GoogleApiWrapper, InfoWindow, Marker} from 'google-maps-react';
import {PharmacyPage} from "../../pages/PharmacyPage";
import StarRatings from "react-star-ratings";
import PharmacyAdminService from "../../PharmacyAdminService";
import axios from "axios";

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
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp
        });
        this.fetchPharmacy();
    }


    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-12">
                            <div className="card">
                                <h5 className="card-header">
                                    {"About"}
                                </h5>
                                <div className="card-body">
                                    <p className="card-text">
                                        Address : {this.state.pharmacy.address.street} <br/>
                                        Grade :  <span style={{marginLeft : '1rem'}}> </span>
                                        <StarRatings
                                            starDimension={'15px'}
                                            rating={this.state.pharmacy.grade}
                                            starRatedColor='gold'
                                            numberOfStars={5}
                                        />
                                        <br/>
                                        Description : {this.state.pharmacy.description} <br/>
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

    fetchPharmacy = async () => {
        await axios.get("http://localhost:8080/api/pharmacy/" + this.state.pharmacyId,
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
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY'
})(PharmacyDescription)
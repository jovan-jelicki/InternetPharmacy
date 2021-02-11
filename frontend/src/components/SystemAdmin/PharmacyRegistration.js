import React from "react";
import {Button, Col, Container, Form} from "react-bootstrap";
import Script from 'react-load-script';
import Select from "react-select";
import axios from "axios";
import HelperService from "../../helpers/HelperService";


export default class PharmacyRegistration extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pharmacy: {
                name: '',
                description: '',
                address: {
                    street: "",
                    town: "",
                    country: "",
                    latitude: 51.507351,
                    longitude: -0.127758
                },
            },addressPom: {
                street: "",
                town: "",
                country: "",
                latitude: 51.507351,
                longitude: -0.127758
            },
            selectedOption: null,
            numberOfRows: 1,
            streetP: "",
            townP: "",
            countryP: "",
            pharmacyAdmins: [],
            admin:"",
            errors: {
                pharmacy: {
                    name: 'Enter name',
                    description: 'Enter description',
                    address: 'Choose address',
                   // pharmacyAdmin: 'Select admin',
                },
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

        }
    }

    handleInputChange = (event) => {

        const {name, value} = event.target;

        const pharmacy = this.state.pharmacy;
        pharmacy[name] = value;

        console.log(this.state.pharmacy)
        this.setState({pharmacy});
        this.validationErrorMessage(event);
    }

    handleScriptLoad = () => {
        var input = document.getElementById('street');
        var options = {
            types: ['geocode'] //this should work !
        };
        const google = window.google

        this.street = new google.maps.places.Autocomplete(input, options);
        this.street.addListener('place_changed', this.handlePlaceSelect);
    }

    handlePlaceSelect = () => {
        const addressObject = this.street.getPlace();
        try {
            //console.log( addressObject.address_components)
            const address = addressObject.address_components;
            if (address) {
                if (this.setAddressParams(address)) {
                    this.setState(
                        {
                            addressPom: {
                                street: this.state.streetP,
                                town: this.state.townP,
                                country: this.state.countryP,
                                latitude: addressObject.geometry.location.lat(),
                                longitude: addressObject.geometry.location.lng(),

                            },
                        }
                    );
                    this.state.pharmacy.address=this.state.addressPom;
                }
            } else {
                this.addressErrors(false)
            }
        } catch {
            this.addressErrors(false)
        }
    }

    setAddressParams = (address) => {
        var street, number, city, country, completeAddress, i;
        for (i = 0; i < address.length; i++) {
            if (address[i].types == "route") {
                street = address[i].long_name;
            } else if (address[i].types == "street_numbe") {
                number = address[i].long_name;
            } else if (address[i].types[0] == "locality") {
                city = address[i].long_name;
            } else if (address[i].types[0] == "country") {
                country = address[i].long_name;
            }
        }
        completeAddress = street + " " + number + " " + city + " " + country;

        if (street == undefined || street == "" || city == undefined || city == "" || country == undefined || country == "") {
            this.addressErrors(false)
            return false;
        } else {
            this.addressErrors(true)
            this.state.townP = city;
            this.state.countryP = country;
            if (number == undefined)
                this.state.streetP = street;
            else
                this.state.streetP = street + number;
        }
        return true;
    }

    selectErrors = (value) => {
        let errors = this.state.errors;
        errors.pharmacy.pharmacyAdmin = value.length < 1 ? 'Select pharmacy admin' : '';
        this.setState({errors});
    }

    addressErrors = (bool) => {
        let errors = this.state.errors;
        if (bool == false) {
            //.log("Nisam dobro prosla")
            errors.pharmacy.address = 'Please choose valid address';
        } else {
            //console.log("DObro sam prosla ");
            errors.pharmacy.address = "";
        }
        this.setState({errors});
    }

    validationErrorMessage = (event) => {
        const {name, value} = event.target;
        let errors = this.state.errors;
        switch (name) {
            case 'name':
                errors.pharmacy.name = value.length < 1 ? 'Enter Name' : '';
                break;
            case 'description':
                errors.pharmacy.description = value.length < 1 ? 'Enter Description' : '';
                break;
            default:
                break;
        }

        this.setState({errors});
    }

    submitForm = async (event) => {
        this.setState({submitted: true});
        const pharmacy = this.state.pharmacy;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
            console.log(this.state.pharmacy)
            this.sendParams()
        } else {
            console.log('Invalid Form')
        }
    }
    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.pharmacy).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }

    async sendParams() {
       // const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacy/save"
        //    : 'http://localhost:8080/api/pharmacy/save';
        axios
            .post(HelperService.getPath('/api/pharmacy/save'), {
                'id':'',
                'name': this.state.pharmacy.name,
                'description' : this.state.pharmacy.description,
                'address' : {
                        'street': this.state.pharmacy.address.street,
                        'town': this.state.pharmacy.address.town,
                        'country': this.state.pharmacy.address.country,
                        'latitude': this.state.pharmacy.address.latitude,
                        'longitude': this.state.pharmacy.address.longitude
                    },
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert("Successfully registered!");

            }).catch(() => {
            alert("Pharmacy was not registered successfully!")
        })

    }


    render() {
        const {selectedOption} = this.state;

        return (
            <Container style={{ background: 'rgb(232, 244, 248 )',color: 'rgb(0, 92, 230)', }}>
                <h3 style={({marginTop: '5rem', textAlignVertical: "center", textAlign: "center"})}>Pharmacy registration</h3>
                <div className="row"
                     style={{marginTop: '3rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.name} id="name" name="name" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Pharmacy name"/>
                        {this.state.submitted && this.state.errors.pharmacy.name.length > 0 && <span className="text-danger">{this.state.errors.pharmacy.name}</span>}
                    </div>

                </div>

                <div className="row" style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Address</label>
                    <div className="col-sm-3 mb-2">
                        <Script type="text/javascript" url="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY&libraries=places" onLoad={this.handleScriptLoad}/>
                        <input type="text" id="street" placeholder="Enter Address" value={this.query}/>
                        {this.state.submitted && this.state.errors.pharmacy.address.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Description</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.description} name="description" onChange={(e) => {
                            this.handleInputChange(e)
                        }} className="form-control" placeholder="Description"/>
                        {this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&
                        <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>

                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <div className="row">
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div className="col-sm-4">
                            <Button variant="primary" onClick={this.submitForm}>Done</Button>
                        </div>
                    </div>
                </div>
            </Container>
        );
    }
}

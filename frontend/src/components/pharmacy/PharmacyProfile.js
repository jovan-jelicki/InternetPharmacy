import React from "react";
import {Button, Container} from "react-bootstrap";
import Script from 'react-load-script';
import Select from "react-select";
import axios from "axios";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";

const options = [
    { value: 'Marko', label: 'Marko Markovic' },
    { value: 'Zoran', label: 'Zoran Petrovic' },
    { value: 'Ana', label: 'Ana Matic' },
];

export default class PharmacyProfile extends React.Component {
    constructor() {
        super();
        this.state = {
            pharmacy: {
                id : -1,
                name: '',
                description: '',
                address: {
                    street: "",
                    town: "",
                    country: "",
                    latitude: 51.507351,
                    longitude: -0.127758
                },
                dermatologistCost : 0,
                pharmacistCost : 0
            },
            selectedOption: null,
            numberOfRows: 1,
            streetP: "",
            townP: "",
            countryP: "",
            errors: {
                pharmacy: {
                    name: '',
                    description: '',
                    address: '',
                    dermatologistCost: '',
                    pharmacistCost: '',
                },
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp,
            pharmacy : {
                ...this.state.pharmacy,
                id : temp
            }
        })
        this.fetchPharmacy();
    }



    render() {
        const {selectedOption} = this.state;

        return (
            <Container style={{
                background: 'rgb(232, 244, 248 )',
                color: 'rgb(0, 92, 230)',
            }}>
                <h3 style={({marginTop: '5rem', textAlignVertical: "center", textAlign: "center"})}>Edit pharmacy profile</h3>
                <div className="row"
                     style={{marginTop: '3rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.name} name="name" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Pharmacy name"/>
                        {this.state.submitted && this.state.errors.pharmacy.name.length > 0 && <span className="text-danger">{this.state.errors.pharmacy.name}</span>}
                    </div>

                </div>
                <div className="row" style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Address</label>
                    <div className="col-sm-3 mb-2">
                        <Script type="text/javascript" url="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY&libraries=places" onLoad={this.handleScriptLoad}/>
                        <input type="text" id="street" placeholder={this.state.pharmacy.address.street + ", " + this.state.pharmacy.address.town + ", " + this.state.pharmacy.address.contry} value={this.query}/>
                        {this.state.submitted && this.state.errors.pharmacy.address.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Description</label>
                    <div className="col-sm-3 mb-2">
                        <textarea rows={9} value={this.state.pharmacy.description} name="description" onChange={(e) => {
                            this.handleInputChange(e)
                        }} className="form-control" placeholder="Description"/>
                        {this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&
                        <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>

                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Dermatologist appointment price</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.dermatologistCost} name="dermatologistCost" onChange={(e) => {
                            this.handleInputChange(e)
                        }} className="form-control" placeholder="price"/>
                        {this.state.submitted && this.state.errors.pharmacy.dermatologistCost.length > 0 &&
                        <span className="text-danger">{this.state.errors.pharmacy.dermatologistCost}</span>}
                    </div>
                </div>

                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Pharmacist appointment price</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.pharmacistCost} name="pharmacistCost" onChange={(e) => {
                            this.handleInputChange(e)
                        }} className="form-control" placeholder="price"/>
                        {this.state.submitted && this.state.errors.pharmacy.pharmacistCost.length > 0 &&
                        <span className="text-danger">{this.state.errors.pharmacy.pharmacistCost}</span>}
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

    // this.setState({
    //                   medicationOffer : {
    //                       ...this.state.medicationOffer,
    //                       shippingDate : date
    //                   }
    //               }
    // );
    handlePlaceSelect = () => {
        const addressObject = this.street.getPlace();
        try {
            //console.log( addressObject.address_components)
            const address = addressObject.address_components;
            if (address) {
                if (this.setAddressParams(address)) {
                    this.setState(
                        {
                            pharmacy: {
                                ...this.state.pharmacy,
                                address: {
                                    street: this.state.streetP,
                                    town: this.state.townP,
                                    country: this.state.countryP,
                                    latitude: addressObject.geometry.location.lat(),
                                    longitude: addressObject.geometry.location.lng()
                                }
                            },
                        }
                    );
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

    addressErrors = (bool) => {
        let errors = this.state.errors;
        if (bool === false) {
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
            case 'pharmacistCost':
                errors.pharmacy.pharmacistCost = value.length < 1 ? 'Enter pharmacist cost' : '';
                break;
            case 'dermatologistCost':
                errors.pharmacy.dermatologistCost = value.length < 1 ? 'Enter dermatologist cost' : '';
                break;
            default:
                break;
        }

        this.setState({errors});
    }

    submitForm = async (event) => {
        await this.setState({
            submitted: true,
        });
        let pharmacy = this.state.pharmacy;
        pharmacy.pharmacistCost = parseInt(this.state.pharmacy.pharmacistCost);
        pharmacy.dermatologistCost = parseInt(this.state.pharmacy.dermatologistCost);
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            //console.info('Valid Form')
            console.log(pharmacy);
            await axios.put(HelperService.getPath("/api/pharmacy/editPharmacyProfile"), pharmacy, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then(() => {
                    alert("Pharmacy edited successfully!");
                    this.props.triggerPharmacyDataChange();
                }
            ).catch(() => {
                alert("Pharmacy did not edit!");
            });
            this.fetchPharmacy();

        } else {
            console.log('Invalid Form')
        }
    }
    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.pharmacy).forEach(item => {
            //console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }

    fetchPharmacy = () => {
        axios.get(HelperService.getPath("/api/pharmacy/" + this.state.pharmacyId), {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        }).then(res => {
            this.setState({
                pharmacy : res.data
            })
        })
    }
}

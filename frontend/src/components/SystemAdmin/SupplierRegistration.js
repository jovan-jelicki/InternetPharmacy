import React from "react";
import Script from "react-load-script";
import {Button, FormControl} from "react-bootstrap";
import axios from "axios";
import HelperService from "../../helpers/HelperService";

export default class SupplierRegistration extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            supplier: {
                email: '',
                password: '',
                firstName: '',
                lastName: '',
                address: {
                    street: "",
                    town: "",
                    country: "",
                    latitude: 51.507351,
                    longitude: -0.127758
                },
                telephone: '',
                rePassword : '',
            },
            addressPom: {
                street: "",
                town: "",
                country: "",
                latitude: 51.507351,
                longitude: -0.127758
            },
            errors:{
                supplier: {
                    email: 'Enter email',
                    password: 'Enter password',
                    firstName: 'Enter First name',
                    lastName: 'Enter Last name',
                    address:'Enter address',
                    telephone: 'Enter Telephone',
                    rePassword : 'Repeat password'

                }
            },
            userLog : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            validForm: false,
            submitted: false,
        }
    }

    async sendParams() {
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/suppliers/save"
            : 'http://localhost:8080/api/suppliers/save';
        axios
            .post(HelperService.getPath('/api/suppliers/save'), {
                'id':'',
                'firstName' : this.state.supplier.firstName,
                'lastName' : this.state.supplier.lastName,
                'userType' : 5,
                'credentials' : {
                    'email' : this.state.supplier.email,
                    'password' : this.state.supplier.password
                },
                'contact' : {
                    'phoneNumber' : this.state.supplier.telephone,
                    'address' : {
                        'street' : this.state.supplier.address.street,
                        'town' : this.state.supplier.address.town,
                        'country' : this.state.supplier.address.country,
                        'latitude' : this.state.supplier.address.latitude,
                        'longitude' : this.state.supplier.address.longitude
                    }
                },
                'approvedAccount':false

            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.userLog.jwtToken
                }
            })
            .then(res => {
                alert("Successfully registered!");

            }).catch(() => {
            alert("Supplier was not registered successfully!")
        })

    }
    submitForm = async (event) => {
        this.setState({ submitted: true });
        const supplier = this.state.supplier;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
            console.log(this.state.supplier)
            this.sendParams();
        } else {
            console.log('Invalid Form')
        }
    }


    handleInputChange = (event) => {
        const { name, value } = event.target;
        const supplier = this.state.supplier;
        supplier[name] = value;

        this.setState({ supplier });
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
                    this.state.supplier.address=this.state.addressPom;
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
        if (bool == false) {
            //.log("Nisam dobro prosla")
            errors.supplier.address = 'Please choose valid address';
        } else {
            //console.log("DObro sam prosla ");
            errors.supplier.address = "";
        }
        this.setState({errors});
    }



    render() {
        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center"})}>Supplier registration</h3>
                </div>

                <div className="row" style={{marginTop: '3rem', marginLeft:'20rem',display: 'flex', justifyContent: 'center', alignItems: 'center'}} >
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.supplier.firstName} name="firstName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="First Name" />
                        { this.state.submitted && this.state.errors.supplier.firstName.length > 0 &&  <span className="text-danger">{this.state.errors.supplier.firstName}</span>}

                    </div>
                    <div className="col-sm-3 mb-2" >
                        <input type="text" value={this.state.supplier.lastName} name="lastName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Last Name" />
                        { this.state.submitted && this.state.errors.supplier.lastName.length > 0 &&  <span className="text-danger">{this.state.errors.supplier.lastName}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Email</label>
                    <div className="col-sm-6 mb-2">
                        <input type="email" value={this.state.supplier.email} name="email" onChange={(e) => { this.handleInputChange(e)} }className="form-control" id="email" placeholder="example@gmail.com" />
                        { this.state.submitted && this.state.errors.supplier.email.length > 0 && <span className="text-danger">{this.state.errors.supplier.email}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Tel</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.supplier.telephone} name="telephone" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="telephone" placeholder="+3810640333489" />
                        { this.state.submitted && this.state.errors.supplier.telephone.length > 0 && <span className="text-danger">{this.state.errors.supplier.telephone}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Address</label>
                    <div className="col-sm-6 mb-2">
                        <Script type="text/javascript" url="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY&libraries=places" onLoad={this.handleScriptLoad}/>
                        <input type="text" id="street" placeholder="Enter Address" value={this.query}/>
                        {this.state.submitted && this.state.errors.supplier.address.length > 0 &&  <span className="text-danger">{this.state.errors.supplier.address}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label className="col-sm-2 col-form-label">Password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="password" type="password" placeholder="Password"  value={this.state.supplier.password} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.supplier.password.length > 0 &&  <span className="text-danger">{this.state.errors.supplier.password}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Repeat password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="rePassword" type="password" placeholder="Repeat new Password" value={this.state.supplier.rePassword} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.supplier.rePassword.length > 0 &&  <span className="text-danger">{this.state.errors.supplier.rePassword}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem'}}>
                    <div className="col-sm-5 mb-2">
                    </div>
                    <div className="col-sm-4">
                        <Button variant="primary" onClick={this.submitForm} >Submit</Button>
                    </div>
                </div>

            </div>
        );
    }



    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;

        switch (name) {
            case 'firstName':
                errors.supplier.firstName = value.length < 1 ? 'Enter First Name' : '';
                break;
            case 'lastName':
                errors.supplier.lastName = value.length < 1 ? 'Enter Last Name' : '';
                break;
            case 'email':
                errors.supplier.email = this.isValidEmail(value) ? '' : 'Email is not valid!';
                break;
            case 'telephone':
                errors.supplier.telephone = this.isValidTelephone(value) ? 'Enter valid telephone number' : '';
                break;
            case 'password':
                errors.supplier.password = value.length < 1 ? 'Enter Password' : '';
                break;
            case 'rePassword':
                errors.supplier.rePassword = this.isValidPassword(value) ? '' : 'This password must match the previous';
                break;
            default:
                break;
        }

        this.setState({ errors });
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.supplier).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }
    isValidEmail = (value) => {
        return !(value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,64}$/i.test(value))
    }

    isValidTelephone = (value) => {
        return !(value && /^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[0-9]{2,3}[-\s\./0-9]*$/i.test(value))
    }

    isValidPassword = (value) => {
        if(this.state.supplier.password !== this.state.supplier.rePassword) {
            return false;
        }else{
            return  true
        }
    }
}
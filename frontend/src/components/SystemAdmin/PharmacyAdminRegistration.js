import React from "react";
import {Button, Col, Container, Form, FormControl} from "react-bootstrap";
import "../../App.css";
import Script from "react-load-script";
import axios from "axios";
import Dropdown from "react-dropdown";
import HelperService from "../../helpers/HelperService";

export default class PharmacyAdminRegistration extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
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
                rePassword: '',
                pharmacy:{
                    id:''
                }
            },
            errors: {
                user: {
                    'email': 'Enter email',
                    'password': 'Enter password',
                    'firstName': 'Enter First name',
                    'lastName': 'Enter Last name',
                    'address': 'Enter address',
                    'telephone': 'Enter Telephone',
                    'rePassword': 'Repeat password'
                }
            },
            validForm: false,
            submitted: false,
            pharmacys:[],
            selectedPharmacy:{
                id:''
            },
            localUser : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}


        }
    }
    async componentDidMount() {
        await this.fetchPharmacy();
    }

    fetchPharmacy(){
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacy"
          //  : 'http://localhost:8080/api/pharmacy';

         axios.get(HelperService.getPath('/api/pharmacy'),{
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + this.state.localUser.jwtToken
            }
        }).then(res => {
            this.setState({
                pharmacys : res.data
            });
        })
        console.log(this.state.pharmacys)    }

    async sendParams() {
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacyAdmin/saveAdminPharmacy"
        //    : 'http://localhost:8080/api/pharmacyAdmin/saveAdminPharmacy';

        axios
            .post(HelperService.getPath('/api/pharmacyAdmin/saveAdminPharmacy'), {
                'id':'',
                'firstName' : this.state.user.firstName,
                'lastName' : this.state.user.lastName,
                'userType' : 2,
                'credentials' : {
                    'email' : this.state.user.email,
                    'password' : this.state.user.password
                },
                'contact' : {
                    'phoneNumber' : this.state.user.telephone,
                    'address' : {
                        'street' : this.state.user.address.street,
                        'town' : this.state.user.address.town,
                        'country' : this.state.user.address.country,
                        'latitude' : this.state.user.address.latitude,
                        'longitude' : this.state.user.address.longitude
                    }
                },
                'approvedAccount':false,
                'pharmacyId': this.state.user.pharmacy.id

            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.localUser.jwtToken
                }
            })
            .then(res => {
                alert("Successfully registered!");

            }).catch(() => {
            alert("Dermatologist was not registered successfully!")
        })

    }

    handlePharmacySelected=(event) => {

        const target = event.target;

        let value = event.target.value;
        console.log(value)
        this.setState({
            selectedPharmacy :{
                id:value
        }
        })
        this.state.user.pharmacy.id=value;
        console.log(this.state.user.pharmacy.id)
        this.validationErrorMessage(event)

    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        const user = this.state.user;
        user[name] = value;

        this.setState({ user });
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
                    this.state.user.address=this.state.addressPom;
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
            errors.user.address = 'Please choose valid address';
        } else {
            //console.log("DObro sam prosla ");
            errors.user.address = "";
        }
        this.setState({errors});
    }
    render() {
        return (

            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center"})}>Pharmacy admin registration</h3>
                </div>

                <div className="row" style={{marginTop: '3rem', marginLeft:'20rem',display: 'flex', justifyContent: 'center', alignItems: 'center'}} >
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.user.firstName} name="firstName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="First Name" />
                        { this.state.submitted && this.state.errors.user.firstName.length > 0 &&  <span className="text-danger">{this.state.errors.user.firstName}</span>}

                    </div>
                    <div className="col-sm-3 mb-2" >
                        <input type="text" value={this.state.lastName} name="lastName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Last Name" />
                        { this.state.submitted && this.state.errors.user.lastName.length > 0 &&  <span className="text-danger">{this.state.errors.user.lastName}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Email</label>
                    <div className="col-sm-6 mb-2">
                        <input type="email" value={this.state.user.email} name="email" onChange={(e) => { this.handleInputChange(e)} }className="form-control" id="email" placeholder="example@gmail.com" />
                        { this.state.submitted && this.state.errors.user.email.length > 0 && <span className="text-danger">{this.state.errors.user.email}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Tel</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.user.telephone} name="telephone" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="telephone" placeholder="+3810640333489" />
                        { this.state.submitted && this.state.errors.user.telephone.length > 0 && <span className="text-danger">{this.state.errors.user.telephone}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Address</label>
                    <div className="col-sm-6 mb-2">
                        <Script type="text/javascript" url="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY&libraries=places" onLoad={this.handleScriptLoad}/>
                        <input type="text" id="street" placeholder="Enter Address" value={this.query}/>
                        {this.state.submitted && this.state.errors.user.address.length > 0 &&  <span className="text-danger">{this.state.errors.user.address}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label className="col-sm-2 col-form-label">Password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="password" type="password" placeholder="Password"  value={this.state.user.password} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.user.password.length > 0 &&  <span className="text-danger">{this.state.errors.user.password}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Repeat password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="rePassword" type="password" placeholder="Repeat new Password" value={this.state.user.rePassword} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.user.rePassword.length > 0 &&  <span className="text-danger">{this.state.errors.user.rePassword}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row" style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Pharmacy</label>
                    <div className="col-sm-3 mb-2">
                        <Col>
                            <Form.Control placeholder="Medication" as={"select"} value={this.state.pharmacys.id} onChange={(e)=>{this.handlePharmacySelected(e)}}>
                            <option disabled={true} selected="selected">Choose</option>
                            {this.state.pharmacys.map(pharmacy =>
                                <option key={pharmacy.id} value={pharmacy.id}>{pharmacy.name}</option>
                            )}
                        </Form.Control>

                        </Col>
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
    submitForm = async (event) => {
        this.setState({ submitted: true });
        const user = this.state.user;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
            console.log(this.state.user)
             this.sendParams();
        } else {
            console.log('Invalid Form')
        }
    }

    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;

        switch (name) {
            case 'firstName':
                errors.user.firstName = value.length < 1 ? 'Enter First Name' : '';
                break;
            case 'lastName':
                errors.user.lastName = value.length < 1 ? 'Enter Last Name' : '';
                break;
            case 'email':
                errors.user.email = this.isValidEmail(value) ? '' : 'Email is not valid!';
                break;
            case 'telephone':
                errors.user.telephone = this.isValidTelephone(value) ? 'Enter valid telephone number' : '';
                break;
            case 'country':
                errors.user.country = value.length < 1 ?  'Enter Country' : '';
                break;
            case 'city':
                errors.user.city = value.length < 1 ?  'Enter City' : '';
                break;
            case 'password':
                errors.user.password = value.length < 1 ? 'Enter Password' : '';
                break;
            case 'rePassword':
                errors.user.rePassword = this.isValidPassword(value) ? '' : 'This password must match the previous';
                break;
            default:
                break;
        }

        this.setState({ errors });
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.user).forEach(item => {
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
        if(this.state.user.password !== this.state.user.rePassword) {
            return false;
        }else{
            return  true
        }
    }
}
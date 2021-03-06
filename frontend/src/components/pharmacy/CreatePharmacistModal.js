import React from "react";
import {Button, Col, Form, Modal, Table, Grid, FormControl, Row} from "react-bootstrap";
import "../../App.css";
import TimePicker from "react-time-picker";
import axios from "axios";
import moment from "moment";
import HelperService from "../../helpers/HelperService";



export default class CreatePharmacistModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                'email': '',
                'password': '',
                'firstName': '',
                'lastName': '',
                'country': '',
                'city': '',
                'street' : '',
                'telephone': '',
                'rePassword' : '',
                'startShift' : '',
                'endShift' : ''
            },
            userLocalStorageData : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            errors:{
                user: {
                    'email': 'Enter email',
                    'password': 'Enter password',
                    'firstName': 'Enter First name',
                    'lastName': 'Enter Last name',
                    'country': 'Enter Country',
                    'city': 'Enter City',
                    'street' : 'Enter Street',
                    'telephone': 'Enter Telephone',
                    'rePassword' : 'Repeat password'
                }
            },
            validForm: false,
            submitted: false,
            pharmacyId : this.props.pharmacyId


        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.isValidPassword = this.isValidPassword.bind(this);

    }

    componentDidMount() {
        // this.fetchPharmacyId();
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        const user = this.state.user;
        user[name] = value;

        this.setState({ user });
        this.validationErrorMessage(event);
    }

    render() {
        return (
            <div >
                <div className="row">
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.user.firstName} name="firstName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="First Name" />
                        { this.state.submitted && this.state.errors.user.firstName.length > 0 &&  <span className="text-danger">{this.state.errors.user.firstName}</span>}

                    </div>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.lastName} name="lastName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Last Name" />
                        { this.state.submitted && this.state.errors.user.lastName.length > 0 &&  <span className="text-danger">{this.state.errors.user.lastName}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row">
                    <label  className="col-sm-2 col-form-label">Email</label>
                    <div className="col-sm-6 mb-2">
                        <input type="email" value={this.state.user.email} name="email" onChange={(e) => { this.handleInputChange(e)} }className="form-control" id="email" placeholder="example@gmail.com" />
                        { this.state.submitted && this.state.errors.user.email.length > 0 && <span className="text-danger">{this.state.errors.user.email}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row">
                    <label  className="col-sm-2 col-form-label">Tel</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.user.telephone} name="telephone" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="telephone" placeholder="+3810640333489" />
                        { this.state.submitted && this.state.errors.user.telephone.length > 0 && <span className="text-danger">{this.state.errors.user.telephone}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row">
                    <label  className="col-sm-2 col-form-label">Street</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.user.street} name="street" onChange={(e) => { this.handleInputChange(e)} } className="form-control" id="street" placeholder="Enter street" />
                        { this.state.submitted && this.state.errors.user.street.length > 0 &&  <span className="text-danger">{this.state.errors.user.street}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row">
                    <label  className="col-sm-2 col-form-label">City</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.user.city} name="city" onChange={(e) => { this.handleInputChange(e)} } className="form-control" id="city" placeholder="Enter city" />
                        { this.state.submitted && this.state.errors.user.city.length > 0 &&  <span className="text-danger">{this.state.errors.user.city}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row">
                    <label  className="col-sm-2 col-form-label">Country</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.user.country} name="country" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="country" placeholder="Enter country" />
                        { this.state.submitted && this.state.errors.user.country.length > 0 &&  <span className="text-danger">{this.state.errors.user.country}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>


                <div className="row">
                    <label className="col-sm-2 col-form-label">Password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="password" type="password" placeholder="Password"  value={this.state.user.password} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.user.password.length > 0 &&  <span className="text-danger">{this.state.errors.user.password}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row">
                    <label  className="col-sm-2 col-form-label">Repeat password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="rePassword" type="password" placeholder="Repeat new Password" value={this.state.user.rePassword} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.user.rePassword.length > 0 &&  <span className="text-danger">{this.state.errors.user.rePassword}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <hr className="mt-2 mb-3"/>

                <div className="row">
                    <div style={({ marginLeft: '1rem' })}>
                        <label style={({ marginRight: '1rem' })}>Select start of work time : </label>
                        <TimePicker  name="startShift" disableClock={true}  format="h a" value={this.state.user.startShift} onChange={this.setStartShift}/>

                    </div>
                </div>
                <div className="row">
                    <div style={({ marginLeft: '1rem' })}>
                        <label style={({ marginRight: '1rem' })}>Select end of work time : </label>
                        <TimePicker  name="endShift" disableClock={true}  format="h a" value={this.state.user.endShift} onChange={this.setEndShift}/>

                    </div>
                </div>


                <hr className="mt-2 mb-3"/>

                <br/>
                <div className="row">
                    <div className="col-sm-5 mb-2">
                    </div>
                    <div >
                        <Button variant="primary" onClick={this.submitForm} style={({ marginRight: '1rem' })}>Submit</Button>
                        <Button variant="secondary" onClick={this.closeModal} >Close</Button>

                    </div>
                </div>

            </div>
        );
    }

    validateWorkingHoursDifference = (startTime, endTime) => {
        let start = moment(startTime + ":00", "HH:mm:ss a");
        let end = moment(endTime + ":00", "HH:mm:ss a");
        let duration = moment.duration(end.diff(start));

        return Math.abs(parseInt(duration.asMinutes())) < 120;
    }

    submitForm = async (event) => {
        this.setState({ submitted: true });
        const user = this.state.user;
        event.preventDefault();
        if (this.validateWorkingHoursDifference(this.state.user.startShift, this.state.user.endShift)) {
            alert("Pharmacist should work at least 2 hours.");
            return;
        }
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form');
            console.log(this.state.user);

            await axios.post(HelperService.getPath('/api/pharmacist/createNewPharmacist'), {
                firstName: this.state.user.firstName,
                lastName: this.state.user.lastName,
                active : Boolean(true),
                credentials: {
                    email: this.state.user.email,
                    password: this.state.user.password
                },
                contact: {
                    phoneNumber: this.state.user.telephone,
                    address: {
                        town: this.state.user.city,
                        street: this.state.user.street,
                        country: this.state.user.country,
                        latitude: 42,
                        longitude: 34
                    }
                },
                workingHours: {
                        period: {
                            periodStart: "2017-01-01 " + this.state.user.startShift + ":00",
                            periodEnd: "2017-01-01 " + this.state.user.endShift + ":00"
                        },
                        pharmacy : {
                            id : this.state.pharmacyId
                        }
                    }

            }, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.userLocalStorageData.jwtToken
                    }
                });

            this.closeModal();
            this.props.fetchPharmacists();
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
            case 'street':
                errors.user.street = value.length < 1 ?  'Enter Street' : '';
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
        return this.state.user.password === this.state.user.rePassword;
    }

    closeModal = () => {
        this.props.closeModal();
    }

    setStartShift =(date) => {
        const user = this.state.user;
        user['startShift'] = date;
        this.setState({ user });
    }

    setEndShift =(date) => {
        const user = this.state.user;
        user['endShift'] = date;
        this.setState({ user });
    }

    fetchPharmacyId = () => {
        axios.get(HelperService.getPath("/api/pharmacyAdmin/getPharmacyAdminPharmacy/" + this.state.userLocalStorageData.id),
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.userLocalStorageData.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    pharmacyId : res.data
                })
            });
    }
}
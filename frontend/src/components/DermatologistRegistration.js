import React from "react";
import {Button, Container, FormControl} from "react-bootstrap";

export default class DermatologistRegistration extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
                dermatologist: {
                    email: '',
                    password: '',
                    firstName: '',
                    lastName: '',
                    country: '',
                    city: '',
                    telephone: '',
                    rePassword : '',
                    workingHours:''
                },
                errors:{
                    dermatologist: {
                        email: 'Enter email',
                        password: 'Enter password',
                        firstName: 'Enter First name',
                        lastName: 'Enter Last name',
                        country: 'Enter Country',
                        city :'Enter City',
                        telephone: 'Enter Telephone',
                        rePassword : 'Repeat password',
                        workingHours:'Working hours error'

                    }
                },
                validForm: false,
                submitted: false,
        }
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        const dermatologist = this.state.dermatologist;
        dermatologist[name] = value;

        this.setState({ dermatologist });
        this.validationErrorMessage(event);
    }


    render() {
        return (
            <Container style={{
                background: 'rgb(232, 244, 248 )',
                color: 'rgb(0, 92, 230)',

            }}>
                <div className="container">
                    <h1 style={({ textAlignVertical: "center", textAlign: "center"})} className="display-4">Dermatologist registration</h1>
                </div>

                <div className="row" style={{marginTop: '3rem', marginLeft:'20rem',display: 'flex', justifyContent: 'center', alignItems: 'center'}} >
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.dermatologist.firstName} name="firstName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="First Name" />
                        { this.state.submitted && this.state.errors.dermatologist.firstName.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.firstName}</span>}

                    </div>
                    <div className="col-sm-3 mb-2" >
                        <input type="text" value={this.state.lastName} name="lastName" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Last Name" />
                        { this.state.submitted && this.state.errors.dermatologist.lastName.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.lastName}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Email</label>
                    <div className="col-sm-6 mb-2">
                        <input type="email" value={this.state.dermatologist.email} name="email" onChange={(e) => { this.handleInputChange(e)} }className="form-control" id="email" placeholder="example@gmail.com" />
                        { this.state.submitted && this.state.errors.dermatologist.email.length > 0 && <span className="text-danger">{this.state.errors.dermatologist.email}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Tel</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.dermatologist.telephone} name="telephone" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="telephone" placeholder="+3810640333489" />
                        { this.state.submitted && this.state.errors.dermatologist.telephone.length > 0 && <span className="text-danger">{this.state.errors.dermatologist.telephone}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Country</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.dermatologist.country} name="country" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="country" placeholder="Enter country" />
                        { this.state.submitted && this.state.errors.dermatologist.country.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.country}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">City</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.dermatologist.city} name="city" onChange={(e) => { this.handleInputChange(e)} } className="form-control" id="city" placeholder="Enter city" />
                        { this.state.submitted && this.state.errors.dermatologist.city.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.city}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label className="col-sm-2 col-form-label">Password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="password" type="password" placeholder="Password"  value={this.state.dermatologist.password} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.dermatologist.password.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.password}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Repeat password</label>
                    <div className="col-sm-6 mb-2">
                        <FormControl name="rePassword" type="password" placeholder="Repeat new Password" value={this.state.dermatologist.rePassword} onChange={(e) => { this.handleInputChange(e)} }/>
                        { this.state.submitted && this.state.errors.dermatologist.rePassword.length > 0 &&  <span className="text-danger">{this.state.errors.dermatologist.rePassword}</span>}

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

            </Container>
        );
    }

    submitForm = async (event) => {
        this.setState({ submitted: true });
        const user = this.state.user;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
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
import React from "react";
import {Alert, Button, Col, Form, Modal} from "react-bootstrap";
import axios from "axios";


export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user:null,
            password: '',
            email: '',
            errors: {
                email: 'Enter Email.',
                password: 'Enter Password.'
            },
            submitted: false,
            error: false,
            errorText:'The email address or password that you have entered does not match any account. Please try again.'
        }
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleSubmit(event) {
        alert('A name was submitted: ' + this.state.value);
        event.preventDefault();

    }

    handleInputChange(event) {
        const { name, value } = event.target;
        this.setState({ [name]: value });
        this.validationErrorMessage(event);
    }
    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal,
            password: '',
            email: '',
        });
    }
    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;
        switch (name) {
            case 'email':
                errors.email = this.isValidEmail(value) ? '' : 'Wrong email format. Please check and try again.';
                break;
            case 'password':
                errors.password = value.length < 1 ? 'Enter Password' : '';
                break;
            default:
                break;
        }
        this.setState({ errors });
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }
    isValidEmail = (value) => {
        return !(value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,64}$/i.test(value))
    }

    async sendData() {
        //console.log(this.state.email)
        //console.log(this.state.password)
        axios
            .post('http://localhost:8080/auth/users', {
                'email':this.state.email,
                'password':this.state.password
            })
            .then(res => {
                this.setState({
                    user:res.data
                })
                this.redirect()
            })
            .catch(
                this.state.error = true
            );

    }

    redirect=()=>{
        let type=this.state.user.type
        console.log(type)
        if(type=="ROLE_patient"){ //ROLE_patient
            window.location = '/patient-profile';
        }else if(type=="ROLE_dermatologist"){ //ROLE_dermatologist
            window.location = '/dermatologistHomePage';
        }else if(type=="ROLE_pharmacist"){ //ROLE_pharmacist
            window.location = '/pharmacistHomePage';
        }else if(type=="ROLE_pharmacyAdmin"){ //ROLE_pharmacyAdmin
            window.location = '/pharmacy-admin-profile';
        }else if(type=="ROLE_supplier"){ //ROLE_supplier
            window.location = '/supplierHomePage';
        }else if(type=="ROLE_systemAdmin"){ //ROLE_systemAdmin
            window.location = '/systemAdmin';
        }

    }


    loginForm = async (event) => {
        this.state.error = false
        this.setState({ submitted: true });
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.log('Valid Form')
            this.sendData();
        } else {
            console.log('Invalid Form')
        }
    }

    render() {
        return (
            <div className="App">
                <Button type="button" className="btn btn-secondary"  onClick={this.handleModal}>Login</Button>

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Login</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={this.handleSubmit}>
                            <Form.Group size="lg" controlId="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control autoFocus name="email" placeholder="email"  type="email" value={this.state.email} onChange={(e) => { this.handleInputChange(e)} } />
                                { this.state.submitted && this.state.errors.email.length > 0 &&  <span className="text-danger">{this.state.errors.email}</span>}
                            </Form.Group>
                            <Form.Group size="lg" controlId="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password"  name="password" placeholder="password"  value={this.state.password} onChange={(e) => { this.handleInputChange(e)} } />
                                { this.state.submitted && this.state.errors.password.length > 0 &&  <span className="text-danger">{this.state.errors.password}</span>}

                                {this.state.error &&  <span className="text-danger">{this.state.errorText}</span>}

                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}  >
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.loginForm}>
                            Ok
                        </Button>
                    </Modal.Footer>
                </Modal>



            </div>

        );
    }


}
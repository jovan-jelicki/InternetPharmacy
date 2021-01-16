import React from "react";
import {Button, Col, Form, Modal} from "react-bootstrap";


export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'password': '',
            'email': '',
            errors: {
                'email': 'Enter Email!',
                'password': 'Enter Password!'
            },
            submitted: false
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
    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal,
            password: '',
            'email': '',
        });
    }
    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;
        switch (name) {
            case 'email':
                errors.email = this.isValidEmail(value) ? '' : 'Email is not valid!';
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

    loginForm = async (event) => {
        this.setState({ submitted: true });
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.log('Valid Form')
        } else {
            console.log('Invalid Form')
        }
    }

}
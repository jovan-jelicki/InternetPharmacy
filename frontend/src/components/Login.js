import React from "react";
import {Button, Col, Form, Modal} from "react-bootstrap";


export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'password': '',
            'email': '',
        }
        this.handleChange = this.handleChange.bind(this);
    }

    handleSubmit(event) {
        alert('A name was submitted: ' + this.state.value);
        event.preventDefault();

    }

    handleChange(event) {
        this.setState({value: event.target.value});
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
                                <Form.Control autoFocus  placeholder="email"  type="email" value={this.state.email} onChange={text => this.setState({email : text.target.value})}/>
                            </Form.Group>
                            <Form.Group size="lg" controlId="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="password"  value={this.state.password} onChange={text => this.setState({password : text.target.value})}/>
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.handleModal}>
                            Save Changes
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
}
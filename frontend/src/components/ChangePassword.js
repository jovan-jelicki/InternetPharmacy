import React from "react";
import {Col, Container, FormControl, Row} from "react-bootstrap";

class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'repErr' : ''
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.validatePassword = this.validatePassword.bind(this);
    }

    handleInputChange(event) {
        this.props.onChange(event);
        this.validatePassword(event);
    }

    validatePassword(event) {
        let repErr = ''
        let val = event.target.value;
        let newPass = this.props.pass[1]

        if (event.target.name === 'repPass')
            if(val !== newPass.substr(0, Math.min(val.length, newPass.length)) ||
                (val.trim() === '' && newPass.trim() !== '')) {
                repErr = 'This password must match the previous';
                this.props.disable(true);
            }
            else
                this.props.disable(false);

        this.setState({
            'repErr': repErr
        })
    }

    render() {
        return (
            <Container>
                <h2 className="pt-4 pb-3">Change Password</h2>
                <Row className="m-2">
                    <FormControl name="oldPass" type="password" placeholder="Password"
                                 value={this.props.pass[0]} onChange={this.handleInputChange}/>
                </Row>
                <Row className="m-2">
                    <FormControl name="newPass" type="password" placeholder="New Password"
                                 value={this.props.pass[1]} onChange={this.handleInputChange}/>
                </Row>
                <Row className="m-2">
                    <FormControl name="repPass" type="password" placeholder="Repeat new Password"
                                 value={this.props.pass[2]} onChange={this.handleInputChange}/>
                    <label className="text-danger">{this.state.repErr}</label>
                </Row>
            </Container>
        )
    }
}

export default ChangePassword;
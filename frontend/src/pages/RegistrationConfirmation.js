import React from "react";
import {Link} from "react-router-dom";
import {Alert, Button} from "react-bootstrap";



export default class RegistrationConfirmation extends React.Component{
    constructor(props) {
        super(props)
    }

    confirmation=()=>{
        localStorage.setItem("confirmed", JSON.stringify(true));
        window.opener = null;
        window.open('', '_self');
        window.close();
    }

    render() {
        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <Alert variant="success">
                    <Alert.Heading>Hey, nice to see you</Alert.Heading>
                    <p>
                        Click on the "Confirm" button and go to registration page to complete your registration.
                    </p>
                </Alert>
                <Button variant="success" onClick={this.confirmation} >
                             Confirm!
                </Button>
            </div>
        );
    }
}
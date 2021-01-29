import React from "react";
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";



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
            <div className="App">
                <h1>Potvrda registracije korisnika</h1>

                <Button variant="primary" onClick={this.confirmation} >
                             Confirm!
                </Button>
            </div>
        );
    }
}
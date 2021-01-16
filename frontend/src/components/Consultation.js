import React from "react";
import {Button, Col, Container, Row} from "react-bootstrap";

export default class Consultation extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
          <Container>
              <br/>
              {this.getPatientsInfo()}
              <Button onClick={() => this.finishAppointment()}> Finish </Button>
          </Container>
        );
    }

    getPatientsInfo = () => {
        return (
            <div>
                <label style={{fontSize : 20, marginLeft : 55}} > Information about patient :  </label>
                <Row>
                    <label style={{fontSize : 20}} className="col-sm-2 col-form-label">  <b> First name: </b></label>
                    <label style={{fontSize : 20}} className="col-sm-2 col-form-label"> {this.props.appointment.patient.firstName} </label>
                </Row>
                <Row>
                    <label style={{fontSize : 20}} className="col-sm-2 col-form-label">  <b> Last name: </b></label>
                    <label style={{fontSize : 20}} className="col-sm-2 col-form-label"> {this.props.appointment.patient.lastName} </label>
                </Row>
            </div>
    )
    }

    finishAppointment = () => {
        this.props.renderParent(false);
        localStorage.clear();
    }
}
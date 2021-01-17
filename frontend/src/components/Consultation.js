import React from "react";
import {Button, Col, Container, FormControl, Row} from "react-bootstrap";

export default class Consultation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            report : "",
            medication : { name :  "Choose medication ..." },
            dateStartTherapy : {},
            dateEndTherapy : {},
            medications : [{
                Id: 0,
                name: "aspirin",
                type: "antibiotic",
                dose: 20,
                medicationShape: "capsule",
                manufacturer: "hemofarm",
                medicationIssue: "withPrescription",
                note: "Be careful",
                ingredient: [{
                    Id: 0,
                    name: "paracetamol"
                },
                    {
                        Id: 1,
                        name: "pera"
                    }],
                sideEffect: [{
                    Id: 0,
                    name: "osip"
                }]
            },
                {
                Id : 0,
                name : "BS",
                type : "probotic",
                dose : 20,
                medicationShape : "capsule",
                manufacturer : "hemofarm",
                medicationIssue : "withPrescription",
                note : "Be careful",
                ingredient :[ {
                    Id : 0,
                    name : "paracetamol"
                },
                    {
                        Id : 1,
                        name : "pera"
                    }],
                sideEffect : [{
                    Id : 0,
                    name : "osip"
                }]

            }]
        }
    }

    render() {
        return (
          <Container>
              <br/>
              {this.getPatientsInfo()}
              <br/>
              <label  style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}}> Report </label>
              <textarea className="form-control"  rows="5" cols="10" placeholder={"Type report..."} name="report" onChange={this.handleInputChange} value={this.state.report} ></textarea>
              <br/>
              <label style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}}> Choose medication </label>

              {this.drugs()}
              <Button onClick={() => this.finishAppointment()}> Finish </Button>
          </Container>
        );
    }

    drugs = () => {
        const Drugs = this.state.medications.map((medication, index) =>
             <option value={medication.name} key={index}> {medication.name} </option>
        );
        return(
            <div>
                <select value={this.state.medication.name}  onChange={this.chooseMedication}>
                    <option disabled> Choose medication ...</option>
                    {Drugs}
                </select>
            </div>
        )
    }
    chooseMedication = (event) => {
        this.setState({
            medication: this.state.medications.filter(m => m.name === event.target.value)[0]
        })
    }
    handleInputChange = (event) => {
        const target = event.target;
        this.setState({
            [target.name] : target.value
        })
    }

    getPatientsInfo = () => {
        return (
            <div>
                <label style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}} > Information about patient :  </label>
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
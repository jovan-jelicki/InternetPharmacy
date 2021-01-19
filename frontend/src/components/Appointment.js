import React from "react";
import {Button, Col, Container, FormControl, Row} from "react-bootstrap";
import ChooseTherapy from "./ChooseTherapy";
import ScheduleByDateTime from "./ScheduleByDateTime";

export default class Appointment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            scheduleNewAppointment : false,
            report : "",
            medication : { name :  "Choose medication ..." },
            dateStartTherapy : "",
            dateEndTherapy : "",
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
        const ButtonSchedule = "Schedule new appointment";
        return (
          <Container>
              <br/>

              <Row >
                <Col>
                  {this.getPatientsInfo()}
                </Col>
                <Col>
                    <Button variant="primary" onClick={this.showScheduling} style={{ height : 40, marginTop : 10, float : "right"}} > {ButtonSchedule} </Button> <br/>
                    <br/>
                      {this.state.scheduleNewAppointment && <ScheduleByDateTime appointment={this.props.appointment}/>}
                </Col>
              </Row>

              <br/>
              <label  style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}}> Report </label>
              <textarea className="form-control"  rows="5" cols="10" placeholder={"Type report..."} name="report" onChange={this.handleInputChange} value={this.state.report} />
              <br/>
              <label style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}}> Choose therapy </label>

              {<ChooseTherapy dateEndTherapy={this.state.dateEndTherapy} dateStartTherapy={this.state.dateStartTherapy}  setStartDate={this.setStartDate} setEndDate={this.setEndDate} medications={this.state.medications} medication={this.state.medication} chooseMedication={this.chooseMedication} removeMedication={this.removeMedication}/>}

              <Button onClick={() => this.finishAppointment()}> Finish appointment </Button>
          </Container>
        );
    }

    getPatientsInfo = () => {
        return (
            <div>
                <label style={{fontSize : 20, marginLeft : 55, textDecoration : "underline"}} > Information about patient :  </label>
                <Row>
                    <label style={{fontSize : 20}} >  <b> First name: </b></label>
                    <label style={{fontSize : 20 , marginLeft: 40}} > {this.props.appointment.patient.firstName} </label>
                </Row>
                <Row>
                    <label style={{fontSize : 20}} >  <b> Last name: </b></label>
                    <label style={{fontSize : 20, marginLeft: 40}} > {this.props.appointment.patient.lastName} </label>
                </Row>
            </div>
        )
    }



    showScheduling =() => {
        this.setState({
            scheduleNewAppointment : !this.state.scheduleNewAppointment
        })
    }

    setStartDate = (date) => {
        this.setState({
            dateStartTherapy : date
        })
    }
    setEndDate = (date) => {
        this.setState({
            dateEndTherapy : date
        })
    }

    chooseMedication = (event) => {
        this.setState({
            medication: this.state.medications.filter(m => m.name === event.target.value)[0]
        })
    }

    removeMedication = () => {
        this.setState({
            medication : {}
        })
    }
    handleInputChange = (event) => {
        const target = event.target;
        this.setState({
            [target.name] : target.value
        })
    }



    finishAppointment = () => {
        this.props.renderParent(false);
        localStorage.clear();
    }
}
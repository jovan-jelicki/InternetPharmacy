import React from "react";
import {Button, Col, Container, FormControl, Row} from "react-bootstrap";
import ChooseTherapy from "./ChooseTherapy";
import ScheduleByDateTime from "./ScheduleByDateTime";
import axios from "axios";

export default class Appointment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            scheduleNewAppointment : false,
            report : "",
            medication : { name :  "Choose medication ..." },
            dateStartTherapy : "",
            dateEndTherapy : "",
            medications : []
        }
    }

    componentDidMount() {
        axios
            .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/medications/getMedicationsForPatient/' + this.props.appointment.patientId)
            .then(res => {
                this.setState({
                    medications : res.data
                })
            })
            .catch(res => alert("Cant get medications!"));
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

              {<ChooseTherapy createEPrescription={this.createEPrescription} dateEndTherapy={this.state.dateEndTherapy} dateStartTherapy={this.state.dateStartTherapy}  setStartDate={this.setStartDate} setEndDate={this.setEndDate} medications={this.state.medications} medication={this.state.medication} chooseMedication={this.chooseMedication} removeMedication={this.removeMedication}/>}

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
                    <label style={{fontSize : 20 , marginLeft: 40}} > {this.props.appointment.firstName} </label>
                </Row>
                <Row>
                    <label style={{fontSize : 20}} >  <b> Last name: </b></label>
                    <label style={{fontSize : 20, marginLeft: 40}} > {this.props.appointment.lastName} </label>
                </Row>
            </div>
        )
    }


    createEPrescription = () => {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/eprescriptions/', {
                'pharmacyId' : this.props.appointment.pharmacyId,
                'prescription' : {
                    'patient' : {id : this.props.appointment.patientId},
                    'medicationQuantity' : [{
                        medication: {id : this.state.medication.id},
                        quantity : 2
                    }
                    ]
                }
            })
            .then(res => alert("Uspeo si!"))
            .catch(es => alert("Nisi uspeo!"));
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
            medication : { name :  "Choose medication ..." }
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
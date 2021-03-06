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
            medications : [],
            eprescription : {},
            therapy : {},
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }

    componentDidMount() {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/medications/getMedicationsForPatient/"
            : 'http://localhost:8080/api/medications/getMedicationsForPatient/';
        axios
            .get(path + this.props.appointment.patientId,
                {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
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
              <label  style={{fontSize : 20, textDecoration : "underline"}}> Report </label>
              <textarea className="form-control"  rows="5" cols="5" placeholder={"Type report..."} name="report" onChange={this.handleInputChange} value={this.state.report} />
              <br/>
              <label style={{fontSize : 20, textDecoration : "underline"}}> Choose therapy </label>

              {<ChooseTherapy createEPrescription={this.createEPrescription} dateEndTherapy={this.state.dateEndTherapy} dateStartTherapy={this.state.dateStartTherapy}  setStartDate={this.setStartDate} setEndDate={this.setEndDate} medications={this.state.medications} medication={this.state.medication} chooseMedication={this.chooseMedication} removeMedication={this.removeMedication}/>}

              <Button onClick={() => this.finishAppointment()}> Finish appointment </Button>
          </Container>
        );
    }

    getPatientsInfo = () => {
        return (
            <div style={{'width' : '70%'}}>
                <label style={{fontSize : 20, textDecoration : "underline"}} > Information about patient :  </label>
                <Row style={{'background':'lightGray','borderRadius' : 10}}>
                    <label style={{fontSize : 20, marginLeft : 40}} >  <b> First name: </b></label>
                    <label style={{fontSize : 20 , marginLeft: 40}} > {this.props.appointment.firstName} </label>
                </Row>
                <Row  style={{'background':'lightGray', 'borderRadius' : 10}}>
                    <label style={{fontSize : 20, marginLeft : 40}} >  <b> Last name: </b></label>
                    <label style={{fontSize : 20, marginLeft: 40}} > {this.props.appointment.lastName} </label>
                </Row>
            </div>
        )
    }


    createEPrescription = () => {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/eprescriptions/"
            : 'http://localhost:8080/api/eprescriptions/';
        axios
            .post(path, {
                'pharmacyId' : this.props.appointment.pharmacyId,
                'prescription' : {
                    'patient' : {id : this.props.appointment.patientId},
                    'medicationQuantity' : [{
                        medication: this.state.medication,
                        quantity : 1
                    }
                    ]
                },
                'examinerId' : this.state.user.id,
                'employeeType' : this.state.user.type
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert("You created ePrescription!");
                this.setState({
                    eprescription : res.data
                })

            })
            .catch(es => {
                alert("This medication is not allowed!");
                this.setState({
                    eprescription : {}
                })
            });
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
        if(this.state.eprescription.id !== undefined){
            let periodStart = this.state.dateStartTherapy;


            //let momentDate = moment(date);
            let day = periodStart.getDate();
            let month = parseInt(periodStart.getMonth())+1;
            if (month < 10)
                month = "0" + month;
            if (parseInt(day)<10)
                day = "0"+day;

            let fullYearStart = periodStart.getFullYear() + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":00";
            let periodEnd = this.state.dateEndTherapy;
             day = periodEnd.getDate();
             month = parseInt(periodEnd.getMonth())+1;
            if (month < 10)
                month = "0" + month;
            if (parseInt(day)<10)
                day = "0"+day;

            let fullYearEnd = periodEnd.getFullYear() + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":00";

            const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/finishAppointment"
                : 'http://localhost:8080/api/appointment/finishAppointment';
            axios
                .put(path, {
                    'id' : this.props.appointment.id,
                    'examinerId' : this.props.appointment.examinerId,
                    'report' : this.state.report,
                    'pharmacyId' : this.props.appointment.pharmacyId,
                    'pharmacyName' : this.props.appointment.pharmacyName,
                    'appointmentStatus' : this.props.appointment.appointmentStatus,
                    'patientId' : this.props.appointment.patientId,
                    'therapy' : {
                        'medication' : this.state.medication,
                        'period' : {
                            periodStart : fullYearStart,
                            periodEnd : fullYearEnd
                        }
                    }
                }, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => {
                    alert("Appointment finished!");
                    this.removeFromStorage();
                    this.props.renderParent(false);
                })
                .catch(res => {
                    alert("Appointment can not be finished!");
                })

        }else {
            const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/finishAppointment"
                : 'http://localhost:8080/api/appointment/finishAppointment';
            axios
                .put(path, {
                    'id' : this.props.appointment.id,
                    'examinerId' : this.props.appointment.examinerId,
                    'report' : this.state.report,
                    'pharmacyId' : this.props.appointment.pharmacyId,
                    'pharmacyName' : this.props.appointment.pharmacyName,
                    'appointmentStatus' : this.props.appointment.appointmentStatus,
                    'patientId' : this.props.appointment.patientId
                }, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => {
                    alert("Appointment finished!");
                    this.removeFromStorage();
                    this.props.renderParent(false);
                })
                .catch(res => {
                    alert("Appointment can not be finished!");
                });

        }
    }

    removeFromStorage = () => {
        if(this.state.user.type == "ROLE_dermatologist"){
            localStorage.removeItem("appointment");
            localStorage.removeItem("startedAppointment");
        }else {
            localStorage.removeItem("consultation");
            localStorage.removeItem("startedConsultation");
        }
    }
}
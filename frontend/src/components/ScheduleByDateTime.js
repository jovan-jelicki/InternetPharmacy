import React from "react";
import {Button, Col, Row} from "react-bootstrap";
import DateTimePicker from "react-datetime-picker";
import axios from "axios";

export default class ScheduleByDateTime extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            message : "",
            timeForScheduling : "",
            appointmentsFree : [],
            selected : { period : { periodStart : "Choose appointment ..."}},
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }
    componentDidMount() {
        if(this.props.appointment.type == 'ROLE_dermatologist'){
            axios
                .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/dermatologists/getFreeAppointments/', {
                    'dermatologistId' : this.state.user.id,
                    'pharmacyId' : this.props.appointment.pharmacyId
                }, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => {
                    this.setState({
                        appointmentsFree : res.data
                    })
                })
                .catch(res => alert("Dermatologist does not have free appointments initialized."));
        }
    }

    render() {

        const Appointments = this.state.appointmentsFree.map((app, index) =>
            <option value={app.period.periodStart} key={index}> {app.period.periodStart} </option>
        );
        return(
            <div>
                <Row>
                    <Col  xs={100}>
                        <p> Schedule by date and time : </p>
                        <DateTimePicker style={{margin : 10}} value={this.state.timeForScheduling} onChange={this.setTimeForNewAppointment}/>
                        <Button  style={{ height : 35, float : "right"}} variant="primary" onClick={this.schedule}>Schedule</Button>
                    </Col>

                    {this.props.appointment.type == 'ROLE_dermatologist' && <Col xs={100}>
                        <p>Schedule by initialized period : </p>
                        <select value={this.state.selected.period.periodStart}  onChange={this.chooseAppointment}>
                            <option disabled> Choose appointment ...</option>
                            {Appointments}
                        </select>
                        <Button  style={{ height : 35, float : "right"}} variant="primary" onClick={this.scheduleSelected}>Schedule</Button>

                    </Col>}
                </Row>
            </div>
        )
    }

    scheduleSelected = () => {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/scheduling/dermatologistSchedulingCreatedAppointment/', {
                'appointmentId' : this.state.selected.id,
                'patientId' : this.props.appointment.patientId
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert("You have schedule new appointment!");
                }
            )
            .catch(res => alert("Can't schedule new appointment because patient/examiner is not free at that period!"));
    }

    chooseAppointment = (event) => {
        this.setState({
            selected : this.state.appointmentsFree.filter(m => m.period.periodStart === event.target.value)[0]
        })
    }
    schedule = () => {
        let periodStart = this.state.timeForScheduling;

        //let momentDate = moment(date);
        let day = periodStart.getDate();
        let month = parseInt(periodStart.getMonth()) + 1;
        if (month < 10)
            month = "0" + month;
        if (parseInt(day) < 10)
            day = "0" + day;
        let hours = parseInt(periodStart.getHours());
        if (hours < 10)
            hours = "0" + hours;
        let minutes = parseInt(periodStart.getMinutes());
        if (minutes < 10)
            minutes = "0" + minutes;

        let fullYearStart = periodStart.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":00";

        if (this.state.user.type == "ROLE_pharmacist") {
            axios
                .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/scheduling/pharmacistScheduling/', {
                    "examinerId": this.state.user.id,
                    "patient": {"id": this.props.appointment.patientId},
                    "period": {"periodStart": fullYearStart},
                    "pharmacy": {"id": this.props.appointment.pharmacyId},
                    "type": this.state.user.type
                }, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => alert("You have schedule new appointment!"))
                .catch(res => alert("Can't schedule new appointment because patient/examiner is not free at that period!"));
        }else {
            axios
                .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/scheduling/dermatologistScheduling/', {
                    "examinerId": this.state.user.id,
                    "patient": {"id": this.props.appointment.patientId},
                    "period": {"periodStart": fullYearStart},
                    "pharmacy": {"id": this.props.appointment.pharmacyId},
                    "type": this.state.user.type
                }, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => alert("You have schedule new appointment!"))
                .catch(res => alert("Can't schedule new appointment because patient/examiner is not free at that period!"));
        }
    }
    setTimeForNewAppointment = (date) => {
        this.setState({
            timeForScheduling : new Date(date)
        })
    }
}
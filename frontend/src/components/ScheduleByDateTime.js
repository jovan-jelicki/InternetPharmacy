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
        }
    }

    render() {
        return(
            <div>
                <Row>
                    <Col xs={50}>
                        <p> Choose date: </p>
                        <DateTimePicker value={this.state.timeForScheduling} onChange={this.setTimeForNewAppointment}/>
                    </Col>
                    <br/>
                    <Col>
                        <Button  style={{ height : 40, marginTop : 10, float : "right"}} variant="primary" onClick={this.schedule}>Schedule</Button>
                    </Col>
                </Row>
            </div>
        )
    }

    schedule = () =>{
        let periodStart = this.state.timeForScheduling;

        //let momentDate = moment(date);
        let day = periodStart.getDate();
        let month = parseInt(periodStart.getMonth())+1;
        if (month < 10)
            month = "0" + month;
        if (parseInt(day)<10)
            day = "0"+day;
        let hours = parseInt(periodStart.getHours());
        if(hours < 10)
            hours = "0" + hours;
        let minutes = parseInt(periodStart.getMinutes());
        if(minutes < 10)
            minutes = "0" + minutes;

        let fullYearStart = periodStart.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":00";

        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/scheduling/pharmacistScheduling/', {
                "examinerId" : this.props.appointment.examinerId,
                "patient" : {"id" : this.props.appointment.patientId},
                "period" : {"periodStart" : fullYearStart},
                "pharmacy" : {"id" : this.props.appointment.pharmacyId},
                "type" : 1
            })
            .then(res => {
                this.setState({
                    medications : res.data
                })
            })
            .catch(res => alert("Cant get medications!"));
    }
    setTimeForNewAppointment = (date) => {
        this.setState({
            timeForScheduling : new Date(date)
        })
    }
}
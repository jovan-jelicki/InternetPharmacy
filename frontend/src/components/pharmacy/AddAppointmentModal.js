import React from "react";
import {Button, Col, Form, Modal, Table, Grid, FormControl, Row} from "react-bootstrap";
import "../../App.css";
import TimePicker from "react-time-picker";
import axios from "axios";
import DatePicker from "react-datepicker";



export default class AddAppointmentModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            dermatologist : this.props.dermatologist,
            period : {
                periodStart : "",
                periodEnd : ""
            },
            appointmentDate : ""
        }

    }


    render() {
        return (
            <div >
                <Form>
                    <label style={{marginRight : 10}}>Select date of appointment: </label>
                    <DatePicker selected={this.state.appointmentDate}  name="appointmentDate" minDate={new Date()} onChange={this.setDate} />
                    <br/>
                    <label style={{marginRight : 10}}>Select start time of appointment: </label>
                    <TimePicker  name="startShift" value={this.state.period.periodStart} onChange={this.setPeriodStart}/>
                    <br/>
                    <label style={{marginRight : 10}}>Select end time of appointment: </label>
                    <TimePicker  name="endShift" value={this.state.period.periodEnd} onChange={this.setPeriodEnd}/>

                    <hr className="mt-2 mb-3"/>

                    <div className="row">
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div >
                            <Button variant="primary"  style={({ marginRight: '1rem' })} onClick={this.submitForm}>Submit</Button>
                            <Button variant="secondary" onClick={this.props.closeModal}>Close</Button>
                        </div>
                    </div>
                </Form>

            </div>
        );
    }

    submitForm = () => {
        let date = this.state.appointmentDate;
        let b = this.state.period.periodStart;
        let c = this.state.period.periodEnd;
        //let momentDate = moment(date);
        let day = date.getDate();
        let month = parseInt(date.getMonth())+1;
        if (month < 10)
            month = "0" + month;
        if (parseInt(day)<10)
            day = "0"+day;

        let fullYear = date.getFullYear() + "-" + month + "-" + day;
        let check = fullYear + " " + this.state.period.periodStart + ":00";
        let  a = "2020-01-01 12:00:00";
        axios.post('http://localhost:8080/api/appointment', {
            examiner: {
                id : this.props.dermatologist.id
            },
            pharmacy: {
                id : 1
            },
            type : 'dermatologist',
            cost : 2000,
            period : {
                periodStart : fullYear + " " + this.state.period.periodStart + ":00",
                periodEnd : fullYear + " " + this.state.period.periodEnd + ":00",
            }

        });
    }

    setDate = (date) => {
        this.setState({
            appointmentDate : date
        })
    }

    setPeriodStart =(date) => {
        const period = this.state.period;
        period['periodStart'] = date;
        this.setState({ period });
    }

    setPeriodEnd =(date) => {
        const period = this.state.period;
        period['periodEnd'] = date;
        this.setState({ period });
    }
}
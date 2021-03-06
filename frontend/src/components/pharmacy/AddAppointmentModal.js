import React from "react";
import {Button, Col, Form, Modal, Table, Grid, FormControl, Row} from "react-bootstrap";
import "../../App.css";
import TimePicker from "react-time-picker";
import axios from "axios";
import DatePicker from "react-datepicker";
import HelperService from "../../helpers/HelperService";



export default class AddAppointmentModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            dermatologist : this.props.dermatologist,
            period : {
                periodStart : "",
                periodEnd : ""
            },
            appointmentDate : "",
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : this.props.pharmacyId
        }
    }

    componentDidMount() {
        // this.fetchPharmacyId();
    }


    render() {
        return (
            <div >
                <Form>
                    <label style={{marginRight : 10}}>Select date of appointment: </label>
                    <DatePicker selected={this.state.appointmentDate}  dateFormat="dd MMMM yyyy"  name="appointmentDate" minDate={new Date()} onChange={this.setDate} />
                    <br/>
                    <label style={{marginRight : 10}}>Select start time of appointment: </label>
                    <TimePicker  name="startShift" disableClock={true}  format="h a" value={this.state.period.periodStart} onChange={this.setPeriodStart}/>
                    <br/>
                    {/*<label style={{marginRight : 10}}>Select end time of appointment: </label>*/}
                    {/*<TimePicker  name="endShift" disableClock={true}  format="h a" value={this.state.period.periodEnd} onChange={this.setPeriodEnd}/>*/}
                    <label><span style={{color : 'red'}}>*By default appointment lasts 1 hour.</span></label>

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
        axios.post(HelperService.getPath('/api/appointment'), {
            examinerId: this.props.dermatologist.id,
            pharmacy: {
                id : this.state.pharmacyId
            },
            active : true,
            type : 'ROLE_dermatologist',
            appointmentStatus : 'available',
            period : {
                periodStart : fullYear + " " + this.state.period.periodStart + ":00",
            }
        }, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then( () =>
            {
                alert("Appointment successfully created!");
                this.props.closeModal();
            }
        ).catch(() => {
            alert("Appointment was not created!");
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

    fetchPharmacyId = () => {
        axios.get(HelperService.getPath("/api/pharmacyAdmin/getPharmacyAdminPharmacy/" + this.state.user.id),
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    pharmacyId : res.data
                })
            });
    }
}
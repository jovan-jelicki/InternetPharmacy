import React from 'react';
import {Button, Col, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import Dropdown from "react-dropdown";
import axios from "axios";
import moment from "moment";

export default class AppointmentsList extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            appointments : []
        }
    }

    componentDidMount() {
        this.fetchAppointments();
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Pregledi dermatologa</h1>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Dermatolog</th>
                        <th scope="col">Datum i vreme</th>
                        <th scope="col">Cena</th>
                        <th scope="col">Ocena dermatologa</th>
                        <th>{' '}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.appointments.map((appointment, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{appointment.dermatologistFirstName + " " + appointment.dermatologistLastName}</td>
                            <td>{moment(appointment.period.periodStart).format('DD.MM.YYYY hh:mm a') + " - " + moment(appointment.period.periodEnd).format('hh:mm a')}</td>
                            <td>{appointment.cost}</td>
                            <td>{appointment.dermatologistGrade}</td>
                            <td><Button variant={'outline-dark'} onClick={() => this.scheduleAppointment(appointment.id)}>Schedule</Button></td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    fetchAppointments = () => {
        axios.get("http://localhost:8080/api/appointment/getAllAvailableUpcomingDermatologistAppointmentsByPharmacy/1").then(res => {
            this.setState({
                appointments : res.data
            });
        })
    }

    scheduleAppointment = (id) => {
        axios
        .put('http://localhost:8080/api/appointment/update', {
            'patientId' : 0,
            'appointmentId' : id
        })
        .then(res => alert('success'))
        
    }
}
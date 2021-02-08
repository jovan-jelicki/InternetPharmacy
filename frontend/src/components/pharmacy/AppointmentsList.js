import React from 'react';
import {Button, Col, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import Dropdown from "react-dropdown";
import axios from "axios";
import moment from "moment";
import StarRatings from "react-star-ratings";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";


export default class AppointmentsList extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            appointments : [],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp
        })
        this.fetchAppointments();
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Available dermatologist appointments</h1>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Dermatologist</th>
                        <th scope="col">Date and time</th>
                        <th scope="col">Price</th>
                        <th scope="col">Dermatologist grade</th>
                        <th>{' '}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.appointments.map((appointment, index) => (
                        <tr key={index}>
                            <th scope="row">{index+1}</th>
                            <td>{appointment.dermatologistFirstName + " " + appointment.dermatologistLastName}</td>
                            <td>{moment(appointment.period.periodStart).format('DD.MM.YYYY hh:mm a') + " - " + moment(appointment.period.periodEnd).format('hh:mm a')}</td>
                            <td>{appointment.cost}</td>
                            <td>
                                <StarRatings
                                    starDimension={'25px'}
                                    rating={appointment.dermatologistGrade}
                                    starRatedColor='gold'
                                    numberOfStars={5}
                                />
                            </td>
                            <td><Button variant={'outline-dark'} onClick={() => this.scheduleAppointment(appointment.id)}>Schedule</Button></td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    fetchAppointments = () => {
        axios.get(HelperService.getPath("/api/appointment/getAllAvailableUpcomingDermatologistAppointmentsByPharmacy/" + this.state.pharmacyId),
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then(res => {
            this.setState({
                appointments : res.data
            });
        })
    }

    scheduleAppointment = (id) => {
        axios
        .put(HelperService.getPath('/api/appointment/update'), {
            'patientId' : 0,
            'appointmentId' : id
        }, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
        .then(res => alert('success'))
    }


}
import React from 'react';
import {Button, Col, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import Dropdown from "react-dropdown";
import axios from "axios";
import moment from "moment";

export default class PharmacyMedicationQueries extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medicationLackingEvents : []
        }
    }

    componentDidMount() {
        this.fetchMedicationLackingEventsListing();
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Lacking medication queries</h1>
                <br/>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Employee name</th>
                        <th scope="col">Medication name</th>
                        <th scope="col">Event date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medicationLackingEvents.map((medicationLackingEvent, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationLackingEvent.employeeFirstName + " " + medicationLackingEvent.employeeLastName}</td>
                            <td>{medicationLackingEvent.medication.name}</td>
                            <td>{moment(medicationLackingEvent.eventDate).format('DD.MM.YYYY hh:mm a')}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    fetchMedicationLackingEventsListing = () => {
        axios.get("http://localhost:8080/api/medicationLacking/getByPharmacyId/1").then(res => {
            this.setState({
                medicationLackingEvents : res.data
            });
        })
    }
}
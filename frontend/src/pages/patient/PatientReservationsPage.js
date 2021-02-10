import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import {Col, Card, Row, Table, Button} from "react-bootstrap";
import axios from 'axios'

class PatientReservationsPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            reservations : []
        }
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        axios
        .get('http://localhost:8080/api/medicationReservation/patient/' + this.aut.id, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                reservations : res.data
            })
        })
    }

    render() {
        const reservations = this.state.reservations.map((r, index) => {
            return (
                <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{r.medicationQuantity.medication.name}</td>
                    <td>{r.medicationQuantity.quantity}</td>
                    <td>{this.formatDate(r.pickUpDate)}</td>
                    <td>{r.status}</td>
                    <td>{r.status == 'requested'
                        ? <Button variant={'outline-light'} onClick={() => this.cancel(r.id)}>Cancel</Button>
                        : <div>{' '}</div>}    
                    </td>
                </tr>
            )
        })
        return (
            <PatientLayout>
                <h2 className={'mt-3 mb-3'}>Reservations</h2>
                <Table striped bordered hover variant="dark">
                    <thead>
                        <tr>
                        <th>#</th>
                        <th>Medication</th>
                        <th>Quantity</th>
                        <th>Pick-up date</th>
                        <th>status</th>
                        <th>{' '}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {reservations}
                    </tbody>
                </Table>
            </PatientLayout>
        )
    }

    formatDate = (dateTime) => {
        const parts = dateTime.substring(0, 10).split('-')
        return parts[2] + '. ' + parts[1] + '. ' + parts[0] + '.'
    }

    cancel = (id) => {
        axios
        .put('http://localhost:8080/api/medicationReservation/cancel/' + id, {}, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => alert('Success'));
    }
}

export default PatientReservationsPage
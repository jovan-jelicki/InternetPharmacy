import React from 'react'
import { Button, Table, Row } from 'react-bootstrap'

class AppointmentListing extends React.Component {
    constructor(props) {
        super(props)
        this.cancel = this.cancel.bind(this)
    }

    cancel(id) {
        this.props.cancel(id);
    }

    render() {
        const appointments = this.props.appointments.map((appointment, index) => {
            const date = appointment.period.periodStart.split(' ')[0]
            const fromTime = appointment.period.periodStart.split(' ')[1]
            const toTime = appointment.period.periodEnd.split(' ')[1]
            return (
                <tr>
                    <td>{index + 1}</td>
                    <td>{date}</td>
                    <td>{fromTime + ' to ' + toTime}</td>
                    <td>{appointment.dermatologistFirstName + ' ' + appointment.dermatologistLastName}</td>
                    <td>{appointment.pharmacyName}</td>
                    <td>{appointment.cost}</td>
                    <td>{appointment.appointmentStatus == 'cancelled' && this.props.view && <label variant={'danger'}>Cancelled</label>}
                        {!this.props.view && <Button variant={'danger'} onClick={() => this.cancel(appointment.id)}>Cancel</Button>}</td>
                </tr>
            )
        })
        return (
            <Table striped bordered hover variant="dark">
                <thead>
                    <tr>
                    <th>#</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Doctor</th>
                    <th>Pharmacy</th>
                    <th>Cost</th>
                    <th>{' '}</th>
                    </tr>
                </thead>
                <tbody>
                    {appointments}
                </tbody>
            </Table>
        )
    }
}

export default AppointmentListing
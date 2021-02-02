import React from "react";
import moment from "moment";
import {Button, Container, Modal, Table} from "react-bootstrap";
import axios from "axios";

export default class ScheduledAppointments extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal : false,
            appointmentForDelete : {}
        }
    }

    render() {
        return (
            <div>
                {this.renderAll()}
            </div>
        )
    }
    
    renderAll = () => {
        const Events = this.props.events.map((appointment, key) =>
            <tr>
                <td>{appointment.pharmacyName}</td>
                <td>{appointment.firstName}</td>
                <td>{appointment.lastName}</td>
                <td>{moment(appointment.period.periodStart).format('DD.MM.YYYY hh:mm')}</td>
                <td>{moment(appointment.period.periodEnd).format('DD.MM.YYYY hh:mm')}</td>
                <td> <Button onClick={() => this.handleClickStart(appointment)}> Start </Button> </td>
                <td> <Button onClick={() => this.handleClickCancel(appointment)} > Did not show up </Button> </td>
            </tr>)
        return (
            <Container >
                <br/>
                <Table style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} striped hover>
                    <tbody>
                    <tr>
                        <th>Pharmacy</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Start of appointment</th>
                        <th>End of appointment</th>
                        <th>Start</th>
                        <th>Cancel</th>
                    </tr>
                    {Events}
                    {this.showModal()}
                    </tbody>
                </Table>
            </Container>
        );
    }

    showModal = () => {
       return ( <Modal backdrop="static" show={this.state.modal} onHide={this.handleModal}>
            <Modal.Header>
                <Modal.Title> Alert! </Modal.Title>
            </Modal.Header>
            <Modal.Body>
             <label> Are you sure you want to cancel this appointment? </label>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={this.cancelAppointment}>
                    Yes
                </Button>
                <Button variant="secondary" onClick={this.handleModal}>
                    No
                </Button>
            </Modal.Footer>
        </Modal>
       )
    }

    cancelAppointment = () => {
        axios
            .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/patientDidNotShowUp/' + this.state.appointmentForDelete.id)
            .then(res => {
                this.props.renderParent(false);
                this.state.modal = false;
            })
            .catch(res => {alert("Can not cancel this appointment!")});
    }

    handleModal = () => {
        this.setState({
                modal: !this.state.modal
            }
        )
    }

    handleClickCancel = (appointment) => {
        this.setState({
                modal: !this.state.modal,
                appointmentForDelete : appointment
            }
        )
    }

    handleClickStart = (appointment) => {
        localStorage.setItem("appointment", JSON.stringify(appointment));
        localStorage.setItem("startedConsultation", JSON.stringify(true));
        this.props.renderParent(true);
    }
}








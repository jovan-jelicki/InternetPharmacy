import React from "react";
import moment from "moment";
import {Button, Container, Table} from "react-bootstrap";

export default class ScheduledAppointments extends React.Component {
    constructor(props) {
        super(props);
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
                    </tbody>
                </Table>
            </Container>
        );
    }

    handleClickCancel = (appointment) => {
        alert(appointment.firstName);
    }

    handleClickStart = (appointment) => {
        localStorage.setItem("appointment", JSON.stringify(appointment));
        localStorage.setItem("startedConsultation", JSON.stringify(true));
        this.props.renderParent(true);
    }
}








import React from "react";
import {Col, Container,Row, Button, Table} from "react-bootstrap";

export default class ReviewedClients extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role : props.role,
            Id : props.Id,
            clients : [],
            sortType : "desc"
        }
    }

    componentDidMount() {
        let client = {
            firstName: "Aovan",
            lastName: "Jelicki",
            dateOfAppointment: "20.01.2021."
        }
        let client2 = {
            firstName: "Pera",
            lastName: "Peric",
            dateOfAppointment: "10.01.2021."
        }
        let list = [client, client2]
        this.setState({
            clients: list
        })
    }

    render() {
        const Clients = this.state.clients.map((client, key) =>
            <tr>
                <td>{client.firstName}</td>
                <td>{client.lastName}</td>
                <td>{client.dateOfAppointment}</td>
            </tr>
        );
        return (

            <Container>
                <br/>
                <Row>
                    <Col  xs={12} md={8}>
                        <Button onClick={this.sortByFirstName} type="button" className="btn btn-secondary"> Sort by first name</Button>
                        <Button onClick={this.sortByLastName} type="button" className="btn btn-secondary"> Sort by last name</Button>
                        <Button onClick={this.sortByDateOfAppointment} type="button" className="btn btn-secondary"> Sort by date of appointment</Button>
                    </Col>
                </Row>
                <Table style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} striped hover>
                    <tbody>
                        <tr>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Date of appointment</th>
                        </tr>
                        {Clients}
                    </tbody>
                </Table>
            </Container>

        )
    }

    sortByFirstName = () => {
        if(this.state.sortType == "desc")
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.firstName > b.firstName) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.firstName > b.firstName) ? 1 : -1),
                sortType : "desc"
            })
    }
    sortByLastName = () => {
        if(this.state.sortType == "desc")
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.lastName > b.lastName) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.lastName > b.lastName) ? 1 : -1),
                sortType : "desc"
            })
    }
    sortByDateOfAppointment = () => {
        if(this.state.sortType == "desc")
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.dateOfAppointment > b.dateOfAppointment) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                clients: this.state.clients.sort((a, b) => (a.dateOfAppointment > b.dateOfAppointment) ? 1 : -1),
                sortType : "desc"
            })
    }
}
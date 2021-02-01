import React from "react";
import {Col, Container, Row, Button, Table, FormControl, FormGroup} from "react-bootstrap";
import axios from "axios";

export default class ReviewedClients extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role : props.role,
            Id : props.Id,
            clients : [],
            searchClients : [],
            query : "",
            sortType : "desc"
        }
    }

    componentDidMount() {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/getFinishedByExaminer', {
                id : 2,
                type : 1
            })
            .then(res => {
                this.setState({
                    searchClients: res.data,
                    clients : res.data
                })
            })
            .catch(res => {
                
            })
    }

    render() {
        const Clients = this.state.searchClients.map((client, key) =>
            <tr>
                <td>{client.pharmacyName}</td>
                <td>{client.patientFirstName}</td>
                <td>{client.patientLastName}</td>
                <td>{client.dateOfAppointment}</td>
            </tr>
        );
        return (

            <Container>
                <br/>
                <FormGroup as={Row} >
                    <Button onClick={this.sortByFirstName} style={{height : 40}}  type="button" className="btn btn-secondary"> Sort by first name</Button>
                    <Button onClick={this.sortByLastName} style={{height : 40}} type="button" className="btn btn-secondary"> Sort by last name</Button>
                    <Button onClick={this.sortByDateOfAppointment} style={{height : 40}} type="button" className="btn btn-secondary"> Sort by date of appointment</Button>
                    <FormControl className="mt-2 mb-2" style={{width : 200, marginLeft : 200}} value={this.state.query} placeholder={"Search clients..."} onChange={this.handleInputChange} />
                </FormGroup>
                <Table style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} striped hover>
                    <tbody>
                        <tr>
                            <th>Pharmacy</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Date of appointment</th>
                        </tr>
                        {Clients}
                    </tbody>
                </Table>
            </Container>

        );
    }

    handleInputChange = (event) => {
        const query = event.target.value;
        const clients = this.state.clients;
        if (query !== "" && query)
            this.setState({
                searchClients : clients.filter(c => (c.patientFirstName + c.patientLastName).toLowerCase().includes(query.toLowerCase().replace(/\s/g, ''))),
                query : query
            })
        else
            this.setState(({
                searchClients : this.state.clients,
                query : query
            }))
    }

    sortByFirstName = () => {
        if(this.state.sortType === "desc")
            this.setState({
                searchClients: this.state.searchClients.sort((a, b) => (a.firstName > b.firstName) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                searchClients: this.state.searchClients.sort((a, b) => (a.firstName > b.firstName) ? 1 : -1),
                sortType : "desc"
            })
    }
    sortByLastName = () => {
        if(this.state.sortType === "desc")
            this.setState({
                ssearchClients: this.state.searchClients.sort((a, b) => (a.lastName > b.lastName) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                searchClients: this.state.searchClients.sort((a, b) => (a.lastName > b.lastName) ? 1 : -1),
                sortType : "desc"
            })
    }
    sortByDateOfAppointment = () => {
        if(this.state.sortType === "desc")
            this.setState({
                searchClients: this.state.searchClients.sort((a, b) => (a.dateOfAppointment > b.dateOfAppointment) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                searchClients: this.state.searchClients.sort((a, b) => (a.dateOfAppointment > b.dateOfAppointment) ? 1 : -1),
                sortType : "desc"
            })
    }
}
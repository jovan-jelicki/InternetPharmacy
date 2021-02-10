import React from "react";
import {Col, Container, Row, Button, Table, FormControl, FormGroup, Modal} from "react-bootstrap";
import axios from "axios";

export default class ReviewedClients extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role : props.role,
            Id : props.Id,
            clients : [],
            client : "",
            searchClients : [],
            appointments : [],
            searchApp : [],
            query : "",
            sortType : "desc",
            showModal : false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }

    componentDidMount() {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/getFinishedByExaminer"
            : 'http://localhost:8080/api/appointment/getFinishedByExaminer';
        axios
            .post(path, {
                id : this.state.user.id,
                type : this.state.user.type
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
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
                <td><Button onClick={() => this.showAppointments(client)}>See previous appointments info</Button></td>
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
                <hr className="mt-2 mb-3"/>

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
                {this.showModal()}
            </Container>


        );
    }
    showAppointments = (client) => {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/getAllFinishedByPatientAndExaminer"
            : 'http://localhost:8080/api/appointment/getAllFinishedByPatientAndExaminer';
        axios
            .post(path, {
                patientId : client.patientId,
                type : this.state.user.type
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    searchApp: res.data,
                    appointments : res.data
                });
                this.handleModal();
            })
            .catch(res => {

            })
    }

    showModal = () => {
        const Appointments = this.state.appointments.map((app, key) =>
            <div>
                <Row>
                    <b> Pharmacy : </b> <p> {app.pharmacyName} </p>
                </Row>
                <Row>
                    <b> Date : </b> <p> {app.period.periodStart}</p> <br/>
                </Row>
                <Row>
                    <b> Report : </b> <p> {app.report}</p> <br/>
                </Row>
                <Row>
                    <b> Medication therapy : </b>  {(app.therapy !== null) &&<p> {app.therapy.medication.name} </p>}
                </Row>
                <hr className="mt-2 mb-3"/>
            <br/>
            </div>
        );
        return (
            <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                <Modal.Header>
                    <Row>
                        <Modal.Title style={{marginLeft : 10}}> Finished </Modal.Title>
                    </Row>
                    <br/>
                    <Row>
                        <FormControl className="mt-2 mb-2" style={{width : 200, marginLeft : 50}} value={this.state.query} placeholder={"Search clients..."} onChange={this.handleInputChange} />
                    </Row>
                </Modal.Header>
                <Modal.Body>
                    <div>
                    {Appointments}
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        )
    }

    handleModal = () => {
        this.setState({
                showModal: !this.state.showModal
            }
        )
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
import React from 'react';
import {Button, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import axios from "axios";
import moment from "moment";


export default class PharmacyVacationsRequests extends React.Component{
    constructor() {
        super();
        this.state = {
            vacationRequests : [],
            userType : 'pharmacyAdmin',
            showModal : false,
            modalVacationRequest : {
                rejectionNote:""
            }
        }
    }

    componentDidMount() {
        let vacationRequests = [
            {
                periodStart : '21.2.2021.',
                periodEnd : '03.03.2021.',
                vacationNote : 'Bahami',
                employeeFirstName : 'Marko',
                employeeLastName : 'Jugovic',
                vacationRequestStatus : 'requested',
                employeeType : 'pharmacist',
                rejectionNote : ''
            },
            {
                periodStart : '21.5.2021.',
                periodEnd : '03.06.2021.',
                vacationNote : 'Covid-19',
                employeeFirstName : 'Dejan',
                employeeLastName : 'Petrovic',
                vacationRequestStatus : 'approved',
                employeeType : 'dermatologist',
                rejectionNote : ''
            },
            {
                periodStart : '21.2.2021.',
                periodEnd : '03.10.2021.',
                vacationNote : 'Beba',
                employeeFirstName : 'Dijana',
                employeeLastName : 'Jankovic',
                vacationRequestStatus : 'rejected',
                employeeType : 'pharmacist',
                rejectionNote : ''
            }
        ];

        axios
            .get('http://localhost:8080/api/vacationRequest/findByPharmacyAndEmployeeType/1/pharmacist') //todo change pharmacyId
            .then(res => {
                this.setState({
                    vacationRequests : res.data
                })
            });

        // this.setState({
        //     vacationRequests : vacationRequests
        // })
    }

    render() {
        return (
            <div className="container-fluid">
                <div>
                    <br/><br/>
                    <h1>Trazena & odobrena odsustva</h1>

                    <br/>

                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Ime</th>
                            <th scope="col">Prezime</th>
                            <th scope="col">Tip radnika</th>
                            <th scope="col">Razlog</th>
                            <th scope="col">Pocetak odsustva</th>
                            <th scope="col">Kraj odsustva</th>
                            <th scope="col">Status</th>

                        </tr>
                        </thead>
                        <tbody>
                        {this.state.vacationRequests.map((vacationRequest, index) => (
                            <tr>
                                <th scope="row">{index+1}</th>
                                <td>{vacationRequest.employeeFirstName}</td>
                                <td>{vacationRequest.employeeLastName}</td>
                                <td>{vacationRequest.employeeType}</td>
                                <td>{vacationRequest.vacationNote}</td>
                                <td>{moment(vacationRequest.period.periodStart).format('DD.MM.YYYY') }</td>
                                <td>{moment(vacationRequest.period.periodEnd).format('DD.MM.YYYY') }</td>
                                <td>{vacationRequest.vacationRequestStatus}</td>

                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-success" onClick={() => this.acceptVacationRequest(vacationRequest)}>
                                        Accept
                                    </Button>
                                </td >
                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-danger" onClick={() => this.handleModal(vacationRequest)}>
                                        Reject
                                    </Button>
                                </td >
                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'rejected' ? {display : 'none'} : {}}>
                                </td >
                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'rejected' ? {display : 'none'} : {}}>
                                </td >
                            </tr>
                        ))}
                        </tbody>
                    </table>



                    <Modal show={this.state.showModal} onHide={this.handleModal}>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal heading</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form.Group controlId="exampleForm.ControlTextarea1">
                                <Form.Label>Please enter a reason of declining the vacation request</Form.Label>
                                <Form.Control type='text' value={this.state.modalVacationRequest.rejectionNote !== undefined ? this.state.modalVacationRequest.rejectionNote : ""} onChange={(event) => this.handleRejectionNoteChange(event)} />
                            </Form.Group>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleModal}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={this.rejectRequest}>
                                Confirm
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>
        );
    }

    handleRejectionNoteChange = (event) => {
        const { name, value } = event.target;
        this.setState({
            modalVacationRequest : {
                ...this.state.modalVacationRequest,
                rejectionNote : value
            }
        })
    }

    rejectRequest = () => {
        axios.put("http://localhost:8080/api/vacationRequest/rejectVacationRequest/", this.state.modalVacationRequest).then(() => {
            this.fetchVacationRequests();
            this.setState({
                showModal : !this.state.showModal
            });
        })
    }

    handleModal = (vacationRequest) => {
        this.setState({
            showModal : !this.state.showModal,
            modalVacationRequest : vacationRequest
        });
    }

    acceptVacationRequest = (vacationRequest) => {
        let answer = window.confirm('Are you sure you want to accept the vacation request from ' + vacationRequest.employeeFirstName + '?');
        if (answer) {
            let path = "http://localhost:8080/api/vacationRequest/confirmVacationRequest/";
            axios.put(path, vacationRequest).then(() => this.fetchVacationRequests())
                .catch(() => {
                    alert("Request cannot be accepted because pharmacist has scheduled appointments for that period.");
                });
        }
    }

    fetchVacationRequests = () => {
        axios
            .get('http://localhost:8080/api/vacationRequest/findByPharmacyAndEmployeeType/1/pharmacist') //todo change pharmacyId
            .then(res => {
                this.setState({
                    vacationRequests : res.data,
                    modalVacationRequest : {
                        rejectionNote:""
                    }
                })
            });

    }
}
import React from 'react';
import {Button, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import axios from "axios";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";


export default class PharmacyVacationsRequests extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            vacationRequests : [],
            showModal : false,
            modalVacationRequest : {
                rejectionNote:""
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : this.props.pharmacy.id
        }
    }

    componentDidMount() {
        this.fetchVacationRequests();
    }

    render() {
        return (
            <div className="container-fluid">
                <div>
                    <br/><br/>
                    <h1>Requested & accepted pharmacists vacation requests</h1>

                    <br/>

                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                            {/*<th scope="col">Employee type</th>*/}
                            <th scope="col">Reason</th>
                            <th scope="col">Absence period</th>
                            <th scope="col">Status</th>

                        </tr>
                        </thead>
                        <tbody>
                        {this.state.vacationRequests.map((vacationRequest, index) => (
                            <tr key={index}>
                                <th scope="row">{index+1}</th>
                                <td>{vacationRequest.employeeFirstName}</td>
                                <td>{vacationRequest.employeeLastName}</td>
                                {/*<td>{vacationRequest.employeeType}</td>*/}
                                <td>{vacationRequest.vacationNote}</td>
                                <td>{moment(vacationRequest.period.periodStart).format('DD.MM.YYYY') + " - " +
                                    moment(vacationRequest.period.periodEnd).format('DD.MM.YYYY')}</td>
                                <td>{vacationRequest.vacationRequestStatus}</td>

                                <td style={vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-success" onClick={() => this.acceptVacationRequest(vacationRequest)}>
                                        Accept
                                    </Button>
                                </td >
                                <td style={vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-danger" onClick={() => this.handleModal(vacationRequest)}>
                                        Reject
                                    </Button>
                                </td >
                                <td style={vacationRequest.vacationRequestStatus === 'rejected' ? {display : 'none'} : {}}>
                                </td >
                                <td style={vacationRequest.vacationRequestStatus === 'rejected' ? {display : 'none'} : {}}>
                                </td >
                            </tr>
                        ))}
                        </tbody>
                    </table>



                    <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                        <Modal.Header closeButton>
                            <Modal.Title>Reject vacation request</Modal.Title>
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
        console.info(this.state.modalVacationRequest);
        if (this.state.modalVacationRequest.rejectionNote === "" || this.state.modalVacationRequest.rejectionNote === null || this.state.modalVacationRequest.rejectionNote === undefined) {
            alert("Rejection note cannot be empty!");
            return;
        }
        axios.put(HelperService.getPath("/api/vacationRequest/rejectVacationRequest/"), this.state.modalVacationRequest,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then(() => {
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
            axios.put(HelperService.getPath("/api/vacationRequest/confirmVacationRequest/"), vacationRequest,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                }).then(() => this.fetchVacationRequests())
                .catch(() => {
                    alert("Request cannot be accepted because pharmacist has scheduled appointments for that period.");
                });
        }
    }

    fetchVacationRequests = () => {
        axios
            .get(HelperService.getPath('/api/vacationRequest/findByPharmacyAndEmployeeType/' + this.state.pharmacyId + '/ROLE_pharmacist'),
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
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
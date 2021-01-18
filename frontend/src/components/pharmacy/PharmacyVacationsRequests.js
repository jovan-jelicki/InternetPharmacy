import React from 'react';
import {Button, Form, FormControl, Modal, Navbar} from "react-bootstrap";

export default class PharmacyVacationsRequests extends React.Component{
    constructor() {
        super();
        this.state = {
            vacationRequests : [],
            userType : 'pharmacyAdmin',
            showModal : false
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

        this.setState({
            vacationRequests : vacationRequests
        })
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
                                <td>{vacationRequest.periodStart}</td>
                                <td>{vacationRequest.periodEnd}</td>
                                <td>{vacationRequest.vacationRequestStatus}</td>

                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-success" onClick={() => this.acceptVacationRequest(vacationRequest)}>
                                        Accept
                                    </Button>
                                </td >
                                <td style={this.state.userType === 'pharmacyAdmin' && vacationRequest.vacationRequestStatus === 'requested' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-danger" onClick={this.handleModal}>
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
                                <Form.Control as="textarea" rows={3} />
                            </Form.Group>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleModal}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={this.handleModal}>
                                Confirm
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>
        );
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

    acceptVacationRequest = (vacationRequest) => {
        window.confirm('Are you sure you want to accept the vacation request from ' + vacationRequest.employeeFirstName + '?');
    }
}
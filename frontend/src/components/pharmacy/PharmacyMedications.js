import React from 'react';
import {Button, Form, FormControl, Modal, Navbar, Col} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';

const options = [
    'one', 'two', 'three'
];
const defaultOption = options[0];

export default class PharmacyMedications extends React.Component{
    constructor() {
        super();
        this.state = {
            medications : [],
            userType : "",
            showModal : false
        };
    }

    componentDidMount() {
        let medications = [
            {
                name: "Xanax",
                type: "antihistamine",
                dose: 2,
                loyaltyPoints: 3,
                medicationShape: "pill",
                manufacturer: "ABC",
                medicationIssue: "withPrescription",
                note: "take when hungry",
                quantity : 10,
                price : 400.00,
                grade : 4,
                ingredient: [
                    {
                        name: "brufen"
                    },
                    {
                        name: "linex"
                    }

                ],
                sideEffect: [
                    {
                        name: "nausea"
                    },
                    {
                        name: "blindness"
                    }
                ],
                alternatives: [
                    {
                        name: "brufen"
                    },
                    {
                        name: "linex"
                    }
                ]
            },
            {
                name: "Linex",
                type: "antihistamine",
                dose: 2,
                grade : 4,
                loyaltyPoints: 3,
                medicationShape: "pill",
                manufacturer: "ABC",
                quantity : 10,
                price : 1300,
                medicationIssue: "withPrescription",
                note: "take when hungry",
                ingredient: [
                    {
                        name: "brufen"
                    },
                    {
                        name: "linex"
                    }

                ],
                sideEffect: [
                    {
                        name: "nausea"
                    },
                    {
                        name: "blindness"
                    }
                ],
                alternatives: [
                    {
                        name: "brufen"
                    },
                    {
                        name: "linex"
                    }
                ]
            }
        ];
        this.setState({
            medications : medications,
            userType : "pharmacyAdmin"
        })
    }

    render() {
        return (
            <div>
                <br/><br/>
                <h1>Lekovi</h1>

                <br/><br/>
                <Button variant="success" onClick={this.handleModal}>Dodaj lek</Button>
                <Button variant="primary">Proveri dostupnost preko eRecepta</Button>
                <br/><br/>

                <Navbar bg="light" expand="lg">
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Form inline>
                            <FormControl type="text" placeholder="Search by name" className="mr-sm-2" />
                            <Button variant="outline-success">Search</Button>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Naziv</th>
                        <th scope="col">Tip</th>
                        <th scope="col">Ocena</th>
                        <th scope="col">Kolicina</th>
                        <th scope="col">Cena</th>
                        <th scope="col">Sastojci</th>
                        <th scope="col">Alternative</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medications.map((medication, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medication.name}</td>
                            <td>{medication.type}</td>
                            <td>{medication.grade}</td>
                            <td>{medication.quantity}</td>
                            <td>{medication.price}</td>
                            <td>
                                <Dropdown options={options}  value={defaultOption} />
                            </td>
                            <td>
                                <Dropdown options={options}  value={defaultOption} />
                            </td>
                            <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="primary" onClick={this.handleModal}>
                                    Rezervisi
                                </Button>
                            </td >

                            <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="danger" onClick={this.handleModal}>
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>



                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Dodavanje leka</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Row>
                                <Col>
                                    <Form.Control placeholder="Medication" as={"select"} >
                                        <option disabled={true} selected="selected">Choose...</option>
                                        <option >...</option>
                                    </Form.Control>
                                </Col>
                                <Col>
                                    <Form.Control placeholder="Quantity"  />
                                </Col>
                            </Form.Row>
                        </Form>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.handleModal}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
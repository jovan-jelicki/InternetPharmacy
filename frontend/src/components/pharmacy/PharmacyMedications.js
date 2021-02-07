import React from 'react';
import {Button, Form, FormControl, Modal, Navbar, Col} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DatePicker from "react-datepicker";
import axios from "axios";
import StarRatings from "react-star-ratings";


const options = [
    'one', 'two', 'three'
];
const defaultOption = options[0];

export default class PharmacyMedications extends React.Component{
    constructor() {
        super();
        this.state = {
            userType: "",
            showModal: false,
            showEditMedicationQuantityModal : false,
            addMedication: {
                priceDateStart: new Date(),
                priceDateEnd: "",
                cost: "",
                quantity: "",
                medicationId: "",
                pharmacyId: 1
            },
            medicationForEditing : {
                medicationId : 0,
                medicationQuantityId : 0,
                pharmacyId : 0,
                name : "",
                quantity : ""
            },
            searchMedicationName : "",
            notContainedMedications: [],
            pharmacyMedicationListingDTOs: [],
            backupMedications : []
        }
    }

    componentDidMount() {
        this.fetchPharmacyMedicationListingDTOs();
        //this.fetchNotContainedMedicationsInPharmacy();
        this.setState({
            userType : "pharmacyAdmin"
        })
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Lekovi</h1>

                <Button variant="success" onClick={this.openAddMedicationModal} >Add medication</Button>
                <Button variant="primary" style={({ marginLeft: '1rem' })}>Check availability via ePrescription</Button>
                <br/><br/>

                <Navbar bg="light" expand="lg">
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Form inline>
                            <FormControl type="text" placeholder="Search by name" className="mr-sm-2" value={this.state.searchMedicationName} onChange={this.changeSearchMedicationName}/>
                            <Button variant="outline-success" onClick={this.SearchMedication} style={{marginLeft : '1rem'}}>Search</Button>
                            <Button variant="outline-info" onClick={this.resetSearchMedication} style={{marginLeft : '1rem'}}>Reset</Button>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                        <th scope="col">Grade</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Ingredients</th>
                        <th scope="col">Alternatives</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.pharmacyMedicationListingDTOs.map((medicationListing, index) => (
                        <tr key={index}>
                            <th scope="row">{index+1}</th>
                            <td>{medicationListing.name}</td>
                            <td>{medicationListing.type}</td>
                            <td>
                                <StarRatings
                                    starDimension={'25px'}
                                    rating={medicationListing.grade}
                                    starRatedColor='gold'
                                    numberOfStars={5}
                                />
                            </td>
                            <td>{medicationListing.quantity}</td>
                            <td>{medicationListing.price}</td>
                            <td>
                                <Dropdown options={medicationListing.ingredients.map((ingredient, index) => ingredient.name)}  value={medicationListing.ingredients[0].name} />
                            </td>
                            <td>
                                <Dropdown options={medicationListing.alternatives.map((alternative, index) => alternative.name)}   />
                            </td>
                            <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="primary" onClick={this.handleModal}>
                                    Reserve
                                </Button>
                            </td >

                            <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="info" onClick={() => this.editMedication(medicationListing)}>
                                    Edit
                                </Button>
                            </td>

                            <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="danger" onClick={() => this.deleteMedication(medicationListing)}>
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                <Modal show={this.state.showEditMedicationQuantityModal} onHide={this.handleEditModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Edit Medication</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form style={{marginLeft : 2}}>
                            <Form.Row>
                                <Col>
                                    <label>{this.state.medicationForEditing.name}</label>
                                </Col>
                                <Col>

                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Quantity</label>
                                </Col>
                                <Col>
                                    <FormControl type="text" value={this.state.medicationForEditing.quantity} onChange={this.changeMedicationForEditingQuantity}/>
                                </Col>
                            </Form.Row>
                            <br/>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleEditModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.submitEditMedication}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Adding medication</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form style={{marginLeft : 2}}>
                            <Form.Row>
                                <Col>
                                    <Form.Control placeholder="Medication" as={"select"} value={this.state.notContainedMedications.id} onChange={this.handleSelectMedication}>
                                        <option disabled={true} selected="selected">Choose</option>
                                        {this.state.notContainedMedications.map(medication =>
                                            <option key={medication.id} value={medication.id}>{medication.name}</option>
                                        )}
                                    </Form.Control>
                                </Col>
                                <Col>
                                    <Form.Control placeholder="quantity" value={this.state.addMedication.quantity} onChange={this.changeQuantity}/>
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Price</label>
                                </Col>
                                <Col>
                                    <FormControl type="text" value={this.state.addMedication.cost} onChange={this.changeCost}/>
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Price period beginning is today.</label>
                                </Col>
                                {/*<Col>*/}
                                {/*    <DatePicker selected={this.state.addMedication.priceDateStart} dateFormat="dd MMMM yyyy"  name="priceDateStart" minDate={new Date()} onChange={this.setPriceDateStart} />*/}
                                {/*</Col>*/}
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Price period end</label>
                                </Col>
                                <Col>
                                    <DatePicker selected={this.state.addMedication.priceDateEnd} dateFormat="dd MMMM yyyy"  name="priceDateEnd" minDate={new Date()} onChange={this.setPriceDateEnd} />
                                </Col>
                            </Form.Row>
                        </Form>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.submitAddMedication}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }

    submitAddMedication = () => {
        console.log(this.state.addMedication);
        axios.put("http://localhost:8080/api/pharmacy/addNewMedication", this.state.addMedication)
            .then(res => {
                alert("Medication added successfully!");
                this.setState({
                    addMedication: {
                        priceDateStart: new Date(),
                        priceDateEnd: "",
                        cost: "",
                        quantity: "",
                        medicationId: "",
                        pharmacyId: 1
                    }
                })
                this.fetchPharmacyMedicationListingDTOs();
                // this.fetchNotContainedMedicationsInPharmacy();
            })
            .catch(() => {
                alert("Medication was not added successfully!")
            })
        this.handleModal();
    }

    submitEditMedication= () => {
        console.log(this.state.medicationForEditing);
        if (parseInt(this.state.medicationForEditing.quantity) < 0) {
            alert("Medication quantity cannot be negative.");
            return;
        }
        axios.put("http://localhost:8080/api/pharmacy/editMedicationQuantity", this.state.medicationForEditing)
            .then(res => {
                alert("Medication edited successfully!");
                this.setState({
                    medicationForEditing: {
                        quantity: "",
                        name : ""
                    }
                })
                this.fetchPharmacyMedicationListingDTOs();
            })
            .catch(() => {
                alert("Medication was not edited successfully!")
            })
        this.handleEditModal();
    }

    openAddMedicationModal = async () => {
        await this.fetchNotContainedMedicationsInPharmacy();
        if (this.state.notContainedMedications.length === 0) {
            alert ("No medications to add!");
            return;
        }
        this.handleModal();
    }
    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

    handleEditModal= () => {
        this.setState({
            showEditMedicationQuantityModal : !this.state.showEditMedicationQuantityModal,
        });
    }

    deleteMedication = (medication) => {
        let isBoss = window.confirm('Are you sure you want to delete ' + medication.name + ' from your medications list?');
        if (isBoss) {
            console.log(medication);
            axios.put("http://localhost:8080/api/pharmacy/deleteMedicationFromPharmacy", medication)
                .then((res) => {
                    alert("Medication deleted successfully from pharmacay!");

                    this.fetchPharmacyMedicationListingDTOs();
                })
                .catch(() => {
                    alert("Medication was not deleted due to requested medication reservations or it is a part of current promotions.");
                })
        }
    }

    editMedication = (medication) => {
        this.setState({
            medicationForEditing : {
                name : medication.name,
                quantity : medication.quantity,
                medicationId : medication.medicationId,
                medicationQuantityId : medication.medicationQuantityId,
                pharmacyId : medication.pharmacyId,
            },
        });
        this.handleEditModal();
    }

    setPriceDateStart = (date) => {
        this.setState({
            addMedication : {
                ...this.state.addMedication,
                priceDateStart : date
            }
        })
    }

    setPriceDateEnd = (date) => {
        this.setState({
            addMedication : {
                ...this.state.addMedication,
                priceDateEnd : date
            }
        })
    }

    changeQuantity = (event) => {
        this.setState({
            addMedication : {
                ...this.state.addMedication,
                quantity : event.target.value
            }
        })
    }

    changeMedicationForEditingQuantity= (event) => {
        this.setState({
            medicationForEditing : {
                ...this.state.medicationForEditing,
                quantity : event.target.value
            }
        })
    }

    changeCost = (event) => {
        this.setState({
            addMedication : {
                ...this.state.addMedication,
                cost : event.target.value
            }
        })
    }

    fetchNotContainedMedicationsInPharmacy = async () => {
        await axios.get("http://localhost:8080/api/medications/getMedicationsNotContainedInPharmacy/1").then(res => {
            this.setState({
                notContainedMedications : res.data
            });
        })
    }

    handleSelectMedication = async (event) => {
        const target = event.target;
        let value = event.target.value;

        this.setState({
            addMedication : {
                ...this.state.addMedication,
                medicationId : value
            }
        })
    }

    fetchPharmacyMedicationListingDTOs = () => {
        axios.get("http://localhost:8080/api/pharmacy/getPharmacyMedicationListing/1").then(res => { //todo change pharmacyid
            this.setState({
                pharmacyMedicationListingDTOs : res.data,
                backupMedications : res.data
            })
        });
    }
    changeSearchMedicationName = (event) => {
        this.setState({
            searchMedicationName : event.target.value
        })
    }

    SearchMedication = () => {
        let filtered = this.state.pharmacyMedicationListingDTOs.filter((medication) => medication.name.toLowerCase().includes(this.state.searchMedicationName.toLowerCase()));
        this.setState({
            pharmacyMedicationListingDTOs : filtered
        })
    }

    resetSearchMedication = () => {
        this.setState({
            searchMedicationName : "",
            pharmacyMedicationListingDTOs : this.state.backupMedications
        })
    }
}
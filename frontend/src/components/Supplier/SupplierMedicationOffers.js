import React from "react";
import {Button, Form, Modal, Row, Table} from "react-bootstrap";
import Dropdown from "react-dropdown";
import axios from "axios";
import MedicationSpecification from "../MedicationSpecification";
import EditOffer from "./EditOffer";


export default class SupplierMedicationOffers extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            medicationOffers : [],
            selectedOption:"",
            medicationOffersPom:[],
        }
    }


    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/suppliers/getAllBySupplier/'+1)
            .then((res) => {
                this.setState({
                    medicationOffers : res.data
                })
                console.log("USEO")
                console.log(this.state.medicationOffers);
            })
        this.offersBackup = [...this.state.medicationOffers]
    }

    cancel() {
        console.log("BACKUP")
        console.log(this.offersBackup)
        this.setState({
            medicationOffers : this.offersBackup
        })
    }

    onTypeChange=(event) => {
        var option = event.target.id

        this.state.selectedOption=option;

        if(this.state.selectedOption=="all"){
            this.cancel()
        }else {
            this.state.medicationOffers=this.offersBackup;
            let filteredData = this.state.medicationOffers.filter(column => {
                return column.orderStatus.toLowerCase().indexOf(this.state.selectedOption.toLowerCase()) !== -1;
            });
            this.setState({
                medicationOffers: filteredData
            });
        }
    }

    render() {
        return (
            <div>
                <h2 style={({marginTop: '1rem', textAlignVertical: "center", textAlign: "center"})}  >Offers</h2>
                <fieldset>
                    <Form>
                        <Form.Group as={Row}>
                            <label style={{'marginLeft':'2rem'}}> Order status:</label>
                            <Row sm={10} style={{'marginLeft':'1rem'}}>
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="all" name="formHorizontalRadios"id="all" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="pending" name="formHorizontalRadios"id="pending" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="approved" name="formHorizontalRadios" id="approved" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="rejected" name="formHorizontalRadios" id="rejected" onChange={this.onTypeChange} />
                            </Row>
                        </Form.Group>
                    </Form>
                </fieldset>
        <div style={{marginRight:'5rem', marginLeft:'5rem'}}>
                <Table striped bordered hover variant="dark" >
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Created by</th>
                        <th scope="col">Dead line</th>
                        <th scope="col">Medication name</th>
                        <th scope="col">Medication quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Shipping date</th>
                        <th scope="col">Offer status</th>
                        <th scope="col">Order status</th>
                        <th scope="col">Edit offer</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.medicationOffers.map((medicationOffer, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOffer.pharmacyAdminId}</td>
                            <td>{medicationOffer.deadline.split("T")[0]}</td>
                            <td>
                                {medicationOffer.medicationQuantity.map((e, key) => {
                                    return <option key={key} value={e.medication}>{e.medication.name} </option>

                                })
                                }
                            </td>
                            <td>
                                {medicationOffer.medicationQuantity.map((e, key) => {
                                    return <option key={key} value={e.medication}> {e.quantity}</option>

                                })
                                }
                            </td>
                            <td>{medicationOffer.cost}</td>
                            <td>{medicationOffer.shippingDate.split("T")[0]}</td>
                            <td>{medicationOffer.offerStatus}</td>
                            <td>{medicationOffer.orderStatus}</td>
                            <td> <Button variant="primary" onClick={() => this.handleModal(medicationOffer)}>
                                Edit offer {medicationOffer.cost}
                            </Button>
                            </td>
                        </tr>

                    ))}

                    </tbody>
                </Table>
            </div>

                <Modal show={this.state.showModal} onHide={this.closeModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <EditOffer modalOffer={this.state.modalOffer}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.closeModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.handleModal}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

    handleModal = (medicationOffer) => {
        console.log(medicationOffer)
        this.setState({
            modalOffer: medicationOffer,

        });
        console.log("AJAJJAJA")
        console.log(this.state.modalOffer)
        this.setState({
            showModal : !this.state.showModal

        });
    }
    closeModal=()=>{
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
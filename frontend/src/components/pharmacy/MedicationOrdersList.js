import React from "react";
import {Button, Modal} from "react-bootstrap";
import {ButtonGroup, Input} from "rsuite";
import Dropdown from "react-dropdown";
import axios from "axios";
import moment from "moment";

export default class MedicationOrdersList extends React.Component {
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medicationOrders : [],
            showModal : false,
            showContent : 'listOrders',
            backupMedicationOrders : [],
        }
    }

    componentDidMount() {
        this.fetchMedicationOrders();
    }

    render() {

        return (
            <div className="container-fluid">

                <h1>Narudzbenice</h1>

                <br/>
                <Button variant="success" onClick={this.createOrder}>Create order</Button>
                <br/><br/>

                <b>Filter by :</b>
                <ButtonGroup>
                    <Button style={{marginRight : '1rem'}}>All
                        <Input ref="input1" type="radio" name="radioButtonSet" value='filterAll' onChange={this.filterButton} standalone defaultChecked/>
                    </Button>
                    <Button style={{marginRight : '1rem'}}>Pending
                        <Input ref="input2" type="radio" name="radioButtonSet" value='pending' standalone onChange={this.filterButton} />
                    </Button>
                    <Button>Processed
                        <Input ref="input2" type="radio" name="radioButtonSet" value='processed' standalone onChange={this.filterButton} />
                    </Button>
                </ButtonGroup>
                <br/>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Kreirao</th>
                        <th scope="col">Rok</th>
                        <th scope="col">Lista lekova</th>
                        <th scope="col">Status</th>
                        <th scope="col">Ponude</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medicationOrders.map((medicationOrder, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOrder.pharmacyAdminFirstName + ' ' + medicationOrder.pharmacyAdminLastName}</td>
                            <td>{moment(medicationOrder.deadline).format('DD.MM.YYYY')}</td>
                            <td>
                                <Dropdown options={medicationOrder.medicationQuantity.map((medicationQuantityItem, index) =>
                                    medicationQuantityItem.medication.name + "  |  " + medicationQuantityItem.quantity)}  value={medicationOrder.medicationQuantity[0].medication.name + "  |  " + medicationOrder.medicationQuantity[0].quantity} />
                            </td>
                            <td>{medicationOrder.status}</td>
                            <td>
                                <Button variant="primary" onClick={() => this.showOffersButtonClick(medicationOrder)}>
                                    Pregledaj ponude
                                </Button>
                            </td>
                            <td>
                                <Button variant="info" onClick={() => this.editOrder(medicationOrder)}>
                                    Izmeni
                                </Button>
                            </td>
                            <td>
                                <Button variant="danger" onClick={() => this.deleteOrder(medicationOrder)}>
                                    Obrisi
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
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

    filterButton = (event) => {
        //filtriraj po buttonima
        console.log(event);
        if (event === 'filterAll') {
            this.setState({
                medicationOrders: this.state.backupMedicationOrders
            });
            return;
        }
        this.setState({
            medicationOrders: this.state.backupMedicationOrders.filter(medicationOrder => medicationOrder.status === event)
        });

    }

    fetchMedicationOrders = () => {
        axios.get("http://localhost:8080/api/medicationOrder/getAllMedicationOrdersByPharmacy/1")
            .then((res) => {
                this.setState({
                    medicationOrders : res.data,
                    backupMedicationOrders : res.data
                })
            })
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

    showOffersButtonClick = (medicationOrder) => {
        this.props.updateClickedMedicationOrder(medicationOrder);
        this.props.showOffers('showOffers');
    }

    editOrder = (medicationOrder) => {
        this.props.updateClickedMedicationOrder(medicationOrder);
        this.props.showOffers('editOrder');
    }

    createOrder = () => {
        this.props.showOffers('showCreateOrder');
    }

    deleteOrder = (order) => {
        if (window.confirm('Are you sure you want to delete the order from your order list?')) {
            const path = "http://localhost:8080/api/medicationOrder/deleteMedicationOrder/" + order.id;
            axios.delete(path)
                .then((res) => {
                    alert("Order deleted successfully!");
                    this.fetchMedicationOrders();
                })
                .catch(() => alert("Cannot delete this order because it already has offers!"));
        }
    }
}
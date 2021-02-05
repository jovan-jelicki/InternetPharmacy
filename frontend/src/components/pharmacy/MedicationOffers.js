import React from "react";
import {Button, Modal} from "react-bootstrap";
import axios from "axios";
import moment from "moment";


export default class MedicationOffers extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medicationOrder : props.order,
            medicationOffers : []
        }
    }

    componentDidMount() {
        console.log(this.state.medicationOrder);
        this.fetchMedicationOffers();
    }

    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <Button style={({ marginLeft: '1rem', marginBottom: '1rem' })} variant={"outline-info"} onClick={() => this.props.showListOrders("listOrders")}>{"←"}</Button>
                        <br/>
                        <div className="col-md-12">
                            <div className="card">
                                <h5 className="card-header">
                                    Narudzbenica
                                </h5>

                                <div className="card-body">
                                    <p className="card-text">
                                        Kreirao : {this.state.medicationOrder.pharmacyAdminFirstName + " " + this.state.medicationOrder.pharmacyAdminLastName}
                                        <br/>
                                        Status : {this.state.medicationOrder.status}
                                        <br/>
                                        Rok isporuke : {moment(this.state.medicationOrder.deadLine).format("DD.MM.YYYY")}
                                        <br/>
                                        <br/>
                                        Lekovi
                                        <ul>
                                            {this.state.medicationOrder.medicationQuantity.map((medicationQuantity,index) =>
                                                <li key={index}>{medicationQuantity.medication.name + " : " + medicationQuantity.quantity}</li>
                                            )}
                                        </ul>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <br/>
                <h2>Ponude</h2>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Supplier</th>
                        <th scope="col">Price</th>
                        <th scope="col">Shipping date</th>
                        <th scope="col">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medicationOffers.map((medicationOffer, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOffer.supplierFirstName + " " + medicationOffer.supplierLastName}</td>
                            <td>{medicationOffer.cost}</td>
                            <td>{moment(medicationOffer.shippingDate).format("DD.MM.YYYY")}</td>
                            <td>{medicationOffer.status}</td>
                            <td style={medicationOffer.status === 'pending' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="outline-success" onClick={() => this.acceptOffer(medicationOffer)}>
                                    Accept
                                </Button>
                            </td >
                        </tr>
                    ))}
                    </tbody>
                </table>

                {/*<Modal show={this.state.showModal} onHide={this.handleModal}>*/}
                {/*    <Modal.Header closeButton>*/}
                {/*        <Modal.Title>Modal heading</Modal.Title>*/}
                {/*    </Modal.Header>*/}
                {/*    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>*/}
                {/*    <Modal.Footer>*/}
                {/*        <Button variant="secondary" onClick={this.handleModal}>*/}
                {/*            Close*/}
                {/*        </Button>*/}
                {/*        <Button variant="primary" onClick={this.handleModal}>*/}
                {/*            Save Changes*/}
                {/*        </Button>*/}
                {/*    </Modal.Footer>*/}
                {/*</Modal>*/}
            </div>
        )
    }

    acceptOffer = (medicationOffer) => {
        axios.put("http://localhost:8080/api/medicationOffer/acceptOffer/1", medicationOffer)
            .then((res) => {
                alert("Medication offer accepted successfully!");
                this.fetchMedicationOffers();
                //this.handleModal();
            })
            .catch(() => {
                alert("Medication offer was not accepted successfully!");
            })
    }

    fetchMedicationOffers = () => {
        const path = "http://localhost:8080/api/medicationOffer/getOffersByOrderId/" + this.state.medicationOrder.id;
        axios.get(path)
            .then((res) => {
                this.setState({
                    medicationOffers : res.data
                })
            })
    }

    // handleModal = () => {
    //     this.setState({
    //         showModal : !this.state.showModal
    //     });
    // }
}
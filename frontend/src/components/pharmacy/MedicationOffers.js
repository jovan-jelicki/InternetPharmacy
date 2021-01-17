import React from "react";
import {Button, Modal} from "react-bootstrap";

export default class MedicationOffers extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            order : this.props.order,
            medicationOffers : []
        }
    }

    componentDidMount() {
        let medicationOffers = [
            {
                supplier : "Bayern",
                price : 12443,
                shippingDate : '20.10.2021.',
                status : 'pending'
            },
            {
                supplier : "Hemofarm",
                price : 23533,
                shippingDate : '30.10.2021.',
                status : 'pending'
            },
            {
                supplier : "ABC",
                price : 12400,
                shippingDate : '10.10.2021.',
                status : 'pending'
            }
        ]

        this.setState({
            medicationOffers : medicationOffers
        })
    }

    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-12">
                            <div className="card">
                                <h5 className="card-header">
                                    Narudzbenica
                                </h5>
                                <div className="card-body">
                                    <p className="card-text">
                                        Kreirao : {this.state.order.pharmacyAdmin.firstName + " " + this.state.order.pharmacyAdmin.lastName}
                                        <br/>
                                        Status : {this.state.order.status}
                                        <br/>
                                        Rok isporuke : {this.state.order.deadLine}
                                        <br/>
                                        <br/>
                                        Lekovi
                                        <ul>
                                            <li>Bromazepan : 4</li>
                                            <li>Brufen : 1000</li>
                                            <li>Gaze : 2</li>
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
                            <td>{medicationOffer.supplier}</td>
                            <td>{medicationOffer.price}</td>
                            <td>{medicationOffer.shippingDate}</td>
                            <td>{medicationOffer.status}</td>
                            <td style={medicationOffer.status === 'pending' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="outline-success" onClick={this.handleModal}>
                                    Accept
                                </Button>
                            </td >
                            <td style={medicationOffer.status === 'pending' ? {display : 'inline-block'} : {display : 'none'}}>
                                <Button variant="outline-danger" onClick={this.handleModal}>
                                    Reject
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
        )
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
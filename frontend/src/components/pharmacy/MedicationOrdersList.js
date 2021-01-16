import React from "react";
import {Button, Modal} from "react-bootstrap";

export default class MedicationOrdersList extends React.Component {
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medicationOrders : [],
            showModal : false,
            showContent : 'listOrders'
        }
    }

    componentDidMount() {
        let medicationOrders = [
            {
                pharmacyAdmin : {
                    firstName : 'Mirko',
                    lastName : 'Mirkovic'
                },
                deadLine : '21.3.2021.',
                medicationQuantity: {

                },
                status : 'pending',
                medicationOffers : []
            },
            {
                pharmacyAdmin : {
                    firstName : 'Jelena',
                    lastName : 'Rozga'
                },
                deadLine : '13.5.2021.',
                medicationQuantity: {

                },
                status : 'processed',
                medicationOffers : []
            }
        ];

        this.setState({
            medicationOrders : medicationOrders
        })
    }

    render() {
        return (
            <div className="container-fluid">

                <h1>Narudzbenice</h1>

                <br/>
                <Button variant="success">Create order</Button>
                <br/><br/>


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
                            <td>{medicationOrder.pharmacyAdmin.firstName + ' ' + medicationOrder.pharmacyAdmin.lastName}</td>
                            <td>{medicationOrder.deadLine}</td>
                            <td>
                                <Button variant="primary" onClick={this.handleModal}>
                                    Lista lekova
                                </Button>
                            </td>
                            <td>{medicationOrder.status}</td>
                            <td>
                                <Button variant="primary" onClick={this.showOffersButtonClick}>
                                    Pregledaj ponude
                                </Button>
                            </td>
                            <td>
                                <Button variant="info" >
                                    Izmeni
                                </Button>
                            </td>
                            <td>
                                <Button variant="danger" >
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

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

    showOffersButtonClick = () => {
        this.props.showOffers('showOffers');
    }
}
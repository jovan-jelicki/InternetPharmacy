import React from "react";
import {Button, Modal} from "react-bootstrap";
import {ButtonGroup, Input} from "rsuite";
import Dropdown from "react-dropdown";
import axios from "axios";
import CreateNewOffer from "./CreateNewOffer";


const options = [
    'Xanax | 3', 'Brufen | 4', 'Linex | 100'
];
const defaultOption = options[0];

export default class MedicationOrdersForSupplier extends React.Component {
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medicationOrders : [],
            showModal : false,
            showContent : 'listOrders',
            radioAll : '1',
            radioPending : '2',
            radioProcessed : '3',
            order:[]

        }
    }
    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/medicationOrder/getAll')
            .then((res) => {
                this.setState({
                    medicationOrders : res.data
                })
                console.log(this.state.medicationOrders);
            })
    }



    render() {
        const orders= this.state.medicationOrders.map((medicationOrder, index) => (
                <tr>
                    <th scope="row">{index+1}</th>
                    <td>{medicationOrder.pharmacyAdmin.firstName + ' ' + medicationOrder.pharmacyAdmin.lastName}</td>
                    <td>{medicationOrder.deadline.split("T")[0]}</td>
                    <td>
                        {medicationOrder.medicationQuantity.map((e, key) => {
                            return <option key={key} value={e.medication}>{e.medication.name} | {e.quantity}</option>

                        })
                        }
                    </td>
                    <td>
                        <Button variant="primary" onClick={() => this.handleModal(medicationOrder)}>
                            Create offer
                        </Button>
                    </td>
                </tr>
            ))

        return (
            <div className="container-fluid">

                <h1>Medication orders</h1>
                <br/>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Created by</th>
                        <th scope="col">Dead line</th>
                        <th scope="col">Medications</th>
                        <th scope="col">Offer</th>

                    </tr>
                    </thead>
                    <tbody>
                    {orders}
                    </tbody>
                </table>

                <Modal show={this.state.showModal} onHide={this.closeModal}  style={{'height':850}} >
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Create new offer</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        <CreateNewOffer order={this.state.order} />
                    </Modal.Body>

                </Modal>

            </div>
        );
    }

    handleModal = (medicationOrder) => {

        this.setState({
            showModal : !this.state.showModal,
            order: medicationOrder
        });
    }

    closeModal=()=>{

        this.setState({
            showModal : !this.state.showModal
        });
    }


}
import React from "react";
import {Button, Modal, Table} from "react-bootstrap";
import {ButtonGroup, Input} from "rsuite";
import Dropdown from "react-dropdown";
import axios from "axios";
import CreateNewOffer from "./CreateNewOffer";

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
            order:[],
            medicationList:[],
            boolMedications:true,

        }
    }
    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/medicationOrder/getAll')
            .then((res) => {
                this.setState({
                    medicationOrders : res.data
                })
                console.log("pokupi")
                console.log(this.state.medicationOrders);
            })
        this.state.boolMedications=false;

        await axios
            .get('http://localhost:8080/api/suppliers/getSuppliersMedicationList/'+1)
            .then((res) => {
                this.setState({
                    medicationList : res.data
                })
                console.log("LEkovi koje imam")
                console.log(this.state.medicationList);
               // this.state.boolMedications=true;

            })

    }

    checkMedication(){
        for (var j = 0, l = this.state.order.medicationQuantity.length; j < l; j++) {
            if(!this.state.medicationList.some(item => item.medicationName === this.state.order.medicationQuantity[j].medication.name)){
                    this.state.boolMedications = false;

            }else {

                this.state.boolMedications = true;
            }
        }
        console.log(this.state.boolMedications)
    }



    render() {
        const orders= this.state.medicationOrders.map((medicationOrder, index) => (
                <tr>
                    <th scope="row">{index+1}</th>
                    <td>{medicationOrder.pharmacyAdmin.pharmacy.name}</td>
                    <td>{medicationOrder.deadline.split("T")[0]}</td>
                    <td>
                        {medicationOrder.medicationQuantity.map((e, key) => {
                            return <option key={key} value={e.medication}>{e.medication.name} </option>
                        })
                        }
                    </td>
                    <td>
                        {medicationOrder.medicationQuantity.map((e, key) => {
                            return <option key={key} value={e.medication}> {e.quantity}</option>
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

                <h3>Medication orders</h3>
                <br/>
                <div style={{marginRight:'5rem', marginLeft:'5rem'}}>
                    <Table striped bordered hover variant="dark">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Created by</th>
                            <th scope="col">Dead line</th>
                            <th scope="col">Medication name</th>
                            <th scope="col">Medication quantity</th>
                            <th scope="col">Offer</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orders}
                        </tbody>
                    </Table>
                </div>

                <Modal show={this.state.showModal} onHide={this.closeModal}  style={{'height':850}} >
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Create new offer</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        { this.state.boolMedications ?
                        <CreateNewOffer order={this.state.order}/>
                        :
                            <div> You dont have enought medications.</div>
                        }
                    </Modal.Body>
                </Modal>

            </div>
        );
    }

    handleModal = (medicationOrder) => {
        this.setState({
            order: medicationOrder,
        });
        this.state.order=medicationOrder;
        this.checkMedication();

        this.setState({
            showModal: !this.state.showModal,
        });
    }

    closeModal=()=>{
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
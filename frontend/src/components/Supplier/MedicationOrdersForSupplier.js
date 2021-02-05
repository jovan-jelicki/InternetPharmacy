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

        await axios
            .get('http://localhost:8080/api/suppliers/getSuppliersMedicationList/'+1)
            .then((res) => {
                this.setState({
                    medicationList : res.data
                })
                console.log("LEkovi koje imam")
                console.log(this.state.medicationList);

            })

    }

    checkMedication(){
        let myMedications=this.state.medicationList;
        let orderMedications=this.state.order.medicationQuantity;
        console.log("UHUH")
        console.log(myMedications)
        console.log("UHUH")
        console.log(orderMedications)

            for (var j = 0, l = orderMedications.length; j < l; j++) {
                let myMedications = this.state.medicationList;
                let orderMedications = this.state.order.medicationQuantity;
                console.log(orderMedications.length)
                console.log("AJ")
                console.log(j)
                console.log(orderMedications[j])
                if(j<orderMedications.length) {
                    if (!myMedications.some(item => item.medicationName === orderMedications[j].medication.name)) {
                        this.state.boolMedications = false;
                        break;
                    } else {
                        this.state.boolMedications = true;
                        for (var i = 0, l = this.state.medicationList.length; i < l; i++) {
                            if ((myMedications[i].medicationName) === (orderMedications[j].medication.name)) {
                                if (myMedications[i].medicationQuantity < orderMedications[j].quantity) {
                                    this.state.boolMedications = false;
                                    break;
                                } else {
                                    this.state.boolMedications = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

       // console.log(this.state.boolMedications)
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

                <h2 style={({marginTop: '1rem', textAlignVertical: "center", textAlign: "center"})} >Medication orders</h2>
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
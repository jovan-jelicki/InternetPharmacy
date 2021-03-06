import React from "react";
import {Button, Modal, Table} from "react-bootstrap";
import {ButtonGroup, Input} from "rsuite";
import Dropdown from "react-dropdown";
import axios from "axios";
import CreateNewOffer from "./CreateNewOffer";
import HelperService from "../../helpers/HelperService";

export default class MedicationOrdersForSupplier extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role : props.role,
            Id : props.Id,
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
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }
    async componentDidMount() {
        await this.fetchOrders();
        await this.fetchSuppliersMedicationList();
    }
    fetchOrders(){
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/medicationOrder/getAllActive"
         //   : 'http://localhost:8080/api/medicationOrder/getAllActive';
         axios
            .get(HelperService.getPath('/api/medicationOrder/getAllActive'),
                {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }})
            .then((res) => {
                this.setState({
                    medicationOrders : res.data
                })
                console.log("pokupi")
                console.log(this.state.medicationOrders);
            }).catch(
                console.log("greska")
            )
    }

    fetchSuppliersMedicationList(){
         axios.get(HelperService.getPath("/api/suppliers/getSuppliersMedicationList/" + this.state.user.id),
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then((res) => {
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

            for (var j = 0, l = orderMedications.length; j < l; j++) {
                let myMedications = this.state.medicationList;
                let orderMedications = this.state.order.medicationQuantity;
                console.log(orderMedications.length)
                console.log("AJ")
                console.log(j)
                console.log(orderMedications[j])
                if(j<orderMedications.length) {
                    if (!myMedications.some(item => item.medication.name === orderMedications[j].medication.name)) {
                        this.state.boolMedications = false;
                        break;
                    } else {
                        this.state.boolMedications = true;
                        for (var i = 0, l = this.state.medicationList.length; i < l; i++) {
                            if ((myMedications[i].medication.name) === (orderMedications[j].medication.name)) {
                                if (myMedications[i].quantity < orderMedications[j].quantity) {
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
        console.log(this.state.medicationOrders)
        const orders= this.state.medicationOrders.map((medicationOrder, index) => (
                <tr>
                    <th scope="row">{index+1}</th>
                    <td>{medicationOrder.pharmacyName}</td>
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

                <Modal show={this.state.showModal} onHide={this.closeModal}  style={{'height':650}} >
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
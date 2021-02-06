import React from 'react';
import {Button, Col, Form, FormControl, Modal, Table} from "react-bootstrap";
import DatePicker from "react-datepicker";
import axios from "axios";


export default class SupplierMedicationListing extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            supplierId:1,
            medicationId:'',
            quantity:'',
            notContainedMedications:[],
            containedMedications:[],

            changedQuantity:'',
            quantityId:''

        }

    }
    async componentDidMount() {
        await axios.get("http://localhost:8080/api/suppliers/getNonMedicationsBySupplier/1").then(res => {
            this.setState({
                notContainedMedications : res.data
            });
        })
        console.log("nema")
        console.log(this.state.notContainedMedications)
        this.fetchSuppliersMedicationListing()
    }

    fetchSuppliersMedicationListing=()=>{
         axios.get("http://localhost:8080/api/suppliers/getSuppliersMedicationList/1").then(res => {
            this.setState({
                containedMedications : res.data
            });
        })
        console.log("ima")
        console.log(this.state.containedMedications)
    }


    handleSelectMedication = async (event) => {
        const target = event.target;
        let value = event.target.value;
        //console.log(value)
        this.setState({
                medicationId : value
        })
    }
    handleEditMedication=async(event)=>{
        const target = event.target;
        let value = event.target.value;
        //console.log(value)
        this.setState({
            quantityId : value
        })
    }

    handleQuantity = (event) => {
        this.setState({
                quantity : event.target.value
        })
    }

    changeQuantity = (event) => {
        this.setState({
            changedQuantity : event.target.value
        })
    }

    submitAddMedication = () => {
        axios.put("http://localhost:8080/api/suppliers/addNewMedication", {
            supplierId:1,
            medicationId:this.state.medicationId,
            quantity:this.state.quantity,
        })
            .then(res => {
                //alert("Medication added successfully!");
                this.fetchSuppliersMedicationListing();
            })
            .catch(() => {
                alert("Medication was not added successfully!")
            })
    }

    submitEditMedication=()=>{
        axios.post("http://localhost:8080/api/suppliers/edit", {
            supplierId:1,
            quantity:this.state.changedQuantity,
            medicationQuantityId:this.state.quantityId
        })
            .then(res => {
               // alert("Medication edited successfully!");
                this.fetchSuppliersMedicationListing();
            })
            .catch(() => {
                alert("Medication was not added successfully!")
            })
    }

    deleteMedicationQuantity = (medicationQuantity) => {
        let isBoss = window.confirm('Are you sure you want to delete ' + medicationQuantity.medication.name + ' from your medications list?');
        if (isBoss) {
            console.log(medicationQuantity);

            axios.put("http://localhost:8080/api/suppliers/deleteMedicationQuantity", {
                "medicationQuantityId":medicationQuantity.id,
                "supplierId":1
            })
                .then((res) => {
                    alert("Medication deleted successfully from pharmacay!");

                    this.fetchSuppliersMedicationListing();
                })
        }
    }



    render() {
        return (
            <div className="App">
                <h3 style={{marginTop:'5rem'}}>Medication listing</h3>
                <div style={{marginTop:'2rem',marginRight:'20rem', marginLeft:'20rem'}}>
                <Table striped bordered hover variant="dark"  >
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Medication</th>
                        <th scope="col">Quantity</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.containedMedications.map((medicationQuantity, key) =>
                        <tr>
                            <td>{key+1}</td>
                        <td>{medicationQuantity.medication.name}</td>
                        <td>{medicationQuantity.quantity}</td>
                        <td>   <Button variant="danger" onClick={() => this.deleteMedicationQuantity(medicationQuantity)}>
                            Delete
                        </Button></td>
                        </tr>
                        )}

                    </tbody>
                </Table>
                </div>
                <Form style={{marginLeft : 2,marginTop:25, marginBottom:55}}>

                    <Form.Row style={{marginLeft : 380,marginTop:55, marginRight:380}}>
                        <Col>
                            <h5> Add new medication</h5>
                        </Col>
                        <Col>
                            <Form.Control placeholder="Medication" as={"select"} value={this.state.notContainedMedications.id} onChange={this.handleSelectMedication}>
                                <option disabled={true} selected="selected">Choose</option>
                                {this.state.notContainedMedications.map(medication =>
                                    <option key={medication.id} value={medication.id}>{medication.name}</option>
                                )}
                            </Form.Control>
                        </Col>
                        <Col>
                            <Form.Control type="number" placeholder="quantity" value={this.state.quantity} onChange={this.handleQuantity}/>
                        </Col>
                        <Button variant="primary" onClick={this.submitAddMedication}>
                            Add
                        </Button>
                    </Form.Row>

                </Form>

                <Form.Row style={{marginLeft : 380,marginTop:25, marginRight:380}}>
                    <Col>
                        <h5> Change medication quantity</h5>
                    </Col>
                    <Col>
                        <Form.Control placeholder="Medication" as={"select"} value={this.state.containedMedications.id} onChange={this.handleEditMedication}>
                            <option disabled={true} selected="selected">Choose</option>
                            {this.state.containedMedications.map(medicationQuantity =>
                                <option key={medicationQuantity.medication.id} value={medicationQuantity.id}>{medicationQuantity.medication.name}</option>
                            )}
                        </Form.Control>
                    </Col>
                    <Col>
                        <Form.Control type="number" placeholder="quantity" value={this.state.changedQuantity} onChange={this.changeQuantity}/>
                    </Col>
                    <Button variant="primary" onClick={this.submitEditMedication}>
                        Save Changes
                    </Button>
                </Form.Row>



            </div>
        );
    }
}
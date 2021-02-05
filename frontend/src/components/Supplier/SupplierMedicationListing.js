import React from 'react';
import {Button, Col, Form, FormControl, Modal} from "react-bootstrap";
import DatePicker from "react-datepicker";
import axios from "axios";


export default class SupplierMedicationListing extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            notContainedMedications:[],
                    supplierId:1,
                    medicationId:'',
                    quantity:'',

        }

    }
    async componentDidMount() {
        await axios.get("http://localhost:8080/api/suppliers/getNonMedicationsBySupplier/1").then(res => {
            this.setState({
                notContainedMedications : res.data
            });
        })
        console.log(this.state.notContainedMedications)
    }

    handleSelectMedication = async (event) => {
        const target = event.target;
        let value = event.target.value;

        this.setState({
                medicationId : value
        })
    }

    changeQuantity = (event) => {
        this.setState({
                quantity : event.target.value
        })
    }

    submitAddMedication = () => {
        console.log(this.state.medicationId)
        console.log(this.state.quantity)
        axios.put("http://localhost:8080/api/suppliers/addNewMedication", {
            supplierId:1,
            medicationId:this.state.medicationId,
            quantity:this.state.quantity,
        })
            .then(res => {
                alert("Medication added successfully!");
              //  this.fetchPharmacyMedicationListingDTOs();
                // this.fetchNotContainedMedicationsInPharmacy();
            })
            .catch(() => {
                alert("Medication was not added successfully!")
            })
    }

    render() {
        return (
            <div className="App">
                <Form style={{marginLeft : 2,marginTop:25}}>
                    <h3> Add new medications</h3>
                    <Form.Row style={{marginLeft : 380,marginTop:25, marginRight:380}}>
                        <Col>
                            <Form.Control placeholder="Medication" as={"select"} value={this.state.notContainedMedications.id} onChange={this.handleSelectMedication}>
                                <option disabled={true} selected="selected">Choose</option>
                                {this.state.notContainedMedications.map(medication =>
                                    <option key={medication.id} value={medication.id}>{medication.name}</option>
                                )}
                            </Form.Control>
                        </Col>
                        <Col>
                            <Form.Control type="number" placeholder="quantity" value={this.state.quantity} onChange={this.changeQuantity}/>
                        </Col>
                        <Button variant="primary" onClick={this.submitAddMedication}>
                            Save Changes
                        </Button>
                    </Form.Row>

                </Form>
            </div>
        );
    }
}
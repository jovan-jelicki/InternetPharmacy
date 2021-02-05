import React from 'react';
import {Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import AddMedicationQuantity from "./AddMedicationQuantity";
import AllergyPatientListing from "../AllergyPatientListing";
import OrderQuantityListing from "./OrderQuantityListing";
import axios from "axios";

export default class CreateOrder extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medications : [],
            medicationOrder : {
                deadline : "",
                pharmacyAdminId : 1,
                status : "pending",
                medicationQuantity : []
            },
            quantities : []
        }
    }

    componentDidMount() {
        this.fetchMedication();
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    <div>
                        <h1>
                            Create order
                        </h1>
                        <Button variant={"outline-info"} onClick={() => this.props.showListOrders("listOrders")}>{"←"}</Button>
                        <br/><br/>
                        Choose deadline for medication offers: <i/><i/><i/>
                        <DatePicker  selected={this.state.medicationOrder.deadline}  minDate={new Date()} onChange={this.changeDatePicker} />
                        <br/><br/>
                        <AddMedicationQuantity addQuantity={this.addQuantity} medications = {this.state.medications} />

                        <OrderQuantityListing quantities={this.state.quantities} removeQuantity ={this.removeQuantity}/>


                        <Button variant={"dark"} onClick={this.handleSubmit}>Submit</Button>

                    </div>
                </div>
            </div>
        );
    }

    fetchMedication = () => {
        axios.get("http://localhost:8080/api/medications")
            .then((res) => {
                this.setState({
                    medications : res.data
                })
            })
    }

    addQuantity = async (quantity) => {
        if(!this.state.quantities.map(a => a.medication.name).includes(quantity.medication.name))
            await this.setState({
                quantities : this.state.quantities.concat(quantity)
            })
        console.log(this.state.quantities);
    }

    removeQuantity = (quantity) => { //in this context quantity is medication name!
        this.setState({
            quantities : this.state.quantities.filter(a => a.medication !== quantity)
        })
    }


    changeDatePicker = (date) => {
        this.setState({
            medicationOrder : {
                ...this.state.medicationOrder,
                deadline : date
            }
        });
    }

    handleSubmit = async () => {
        await this.setState({
                medicationOrder : {
                    ...this.state.medicationOrder,
                    medicationQuantity : this.state.quantities
                }
            }
        );
        console.log(this.state.medicationOrder);
        if (this.state.medicationOrder.medicationQuantity.length === 0) {
            alert("Cannot submit medication order without any medications!");
            return;
        }

        axios.post("http://localhost:8080/api/medicationOrder/newMedicationOrder", this.state.medicationOrder)
            .then((res) => {
                alert("Medication order created successfully!");
                this.props.showListOrders("listOrders")
            })
    }
}
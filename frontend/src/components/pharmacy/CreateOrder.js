import React from 'react';
import {Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import AddMedicationQuantity from "./AddMedicationQuantity";
import AllergyPatientListing from "../AllergyPatientListing";
import OrderQuantityListing from "./OrderQuantityListing";

export default class CreateOrder extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            medications : [
                {
                    id : 1,
                    name : "Xantil"
                },
                {
                    id: 2,
                    name : "Brufen"
                },
                {
                    id: 3,
                    name : "Linex"
                },
                {
                    id: 4,
                    name : "Vitamin C"
                }
            ],
            medicationOrder : {
                deadLine : "",
                medicationQuantity : []
            },
            quantities : []
        }
    }

    componentDidMount() {
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    <div>
                        <h1>
                            Create order
                        </h1>
                        <br/>
                        Choose deadline for medication offers: <i/><i/><i/>
                        <DatePicker  selected={this.state.medicationOrder.deadLine} minDate={new Date()} onChange={date => this.changeDatePicker(date)} />

                        <AddMedicationQuantity addQuantity={this.addQuantity} medications = {this.state.medications} />

                        <OrderQuantityListing quantities={this.state.quantities} removeQuantity ={this.removeQuantity}/>


                        <Button variant={"dark"} onClick={this.handleSubmit}>Submit</Button>

                    </div>
                </div>
            </div>
        );
    }

    addQuantity = async (quantity) => {
        if(!this.state.quantities.map(a => a.medication).includes(quantity.medication))
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
                deadLine : date
            }
            }
        );
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
    }
}
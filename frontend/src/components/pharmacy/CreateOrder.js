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
            inputOrder : {
                medicationId : 0,
                quantity : 0
            },
            numberOfRows : 1,
            medicationOrder : {
                deadLine : "",
                medicationQuantity : []
            },
            date : "",
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
        let a = this.state.quantities.filter(a => a.medication !== quantity);
        this.setState({
            quantities : a
        })
    }


    renderInputRow = (numberOfRows) => {
        return Array.from({length: numberOfRows}, (v, i) => i).map((item, key) => {
            let medicationId;
            return (
                <tr key={key}>
                    <td>
                        <select  name="medicationId" onChange={this.handleInputChange}>
                            <option disabled={true} selected="selected">select</option>
                            {this.state.medications.map((medication) => <option key={medication.name} value={medication.name}>{medication.name}</option>)}
                        </select>
                    </td>
                    <td onChange={this.handleInputChange}>
                        <input type="text" className="in-text medium"  name="quantity" />
                    </td>
                    <td>
                        <Button variant={"outline-primary"} onClick={this.incrementNumberOfRows}>+</Button>
                    </td>
                </tr>
            );
        });
    }

    incrementNumberOfRows = async () => {

        if (this.state.inputOrder.medicationId === 0) {
            alert('Please select medication!')
            return;
        }

        console.log(this.state.inputOrder);
        await this.setState({
            numberOfRows : this.state.numberOfRows+1
        })

        await this.setState({
            medicationOrder : {
                ...this.state.medicationOrder,
                medicationQuantity : this.state.medicationOrder.medicationQuantity.concat(this.state.inputOrder)
            }
        })

        await this.setState({
            inputOrder : {
                medicationId : 0,
                quantity : 0
            }
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

    handleInputChange = (event) => {
        const target = event.target;
        let name = target.name;
        let value = event.target.value;

        this.setState({
            inputOrder : {
                ...this.state.inputOrder,
                [name] : value
            }
        })
    }

    handleSubmit = () => {
        console.log(this.state.medicationOrder);
    }
}
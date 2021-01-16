import React from 'react';
import {Button} from "react-bootstrap";
import DatePicker from "react-datepicker";

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
                    id:2,
                    name : "Brufen"
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
            date : ""
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
                        <table className="table table-hover" >
                            <thead>
                            <tr>
                                <th scope="col">Medication</th>
                                <th scope="col">Quantity</th>
                            </tr>
                            </thead>
                            <tbody>
                                {this.renderInputRow(this.state.numberOfRows)}
                                <Button variant={"dark"} onClick={this.handleSubmit}>Submit</Button>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }


    renderInputRow = (numberOfRows) => {
        return Array.from({length: numberOfRows}, (v, i) => i).map((item, key) => {
            let medicationId;
            return (
                <tr key={key}>
                    <td>
                        <select  name="medicationId" onChange={this.handleInputChange} value={medicationId}>
                            {this.state.medications.map((medication) => <option key={medication.id} value={medication.id} >{medication.name}</option>)}
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

    incrementNumberOfRows = () => {
        // console.log(this.state.inputOrder);
        this.setState({
            numberOfRows : this.state.numberOfRows+1
        })

        this.setState({
            medicationOrder : {
                // deadLine : this.state.date,
                ...this.state.medicationOrder,
                medicationQuantity : this.state.medicationOrder.medicationQuantity.concat(this.state.inputOrder)
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
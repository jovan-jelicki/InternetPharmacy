import React from 'react';
import {Button} from "react-bootstrap";

export default class AddMedicationQuantity extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userType : 'pharmacyAdmin',
            inputOrder : {
                medication : {
                    name : 'select medication'
                },
                quantity : 0
            },
        }
    }

    componentDidMount() {
    }

    render() {
        return (
            <table className="table table-hover" >
                <thead>
                <tr>
                    <th scope="col">Medication</th>
                    <th scope="col">Quantity</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <select  name="medication" onChange={this.handleSelectInputChange} value={this.state.inputOrder.medication.name}>
                                <option disabled>select medication</option>
                                {this.props.medications.map((medication) => <option key={medication.id} value={medication.name}>{medication.name}</option>)}
                            </select>
                        </td>
                        <td onChange={this.handleInputChange}>
                            <input type="text" className="in-text medium"  name="quantity" value={this.state.inputOrder.quantity}/>
                        </td>
                        <td>
                            <Button variant={"outline-primary"} onClick={this.addQuantity}>+</Button>
                        </td>
                    </tr>
                </tbody>
            </table>
        );
    }

    handleSelectInputChange = (event) => {
        const target = event.target;
        let name = target.name;
        let value = event.target.value;

        this.setState({
            inputOrder : {
                ...this.state.inputOrder,
                medication : {
                    name : value
                }
            }
        })
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

    addQuantity = async () => {
        console.log(this.state.inputOrder);

        if (this.state.inputOrder.medication === 'select medication' || isNaN(this.state.inputOrder.quantity) || parseInt(this.state.inputOrder.quantity) <= 0)
            return;
        await this.props.addQuantity(this.state.inputOrder);
        this.setState({
            inputOrder : {
                medication : 'select medication',
                quantity : 0
            }
        })
    }
}
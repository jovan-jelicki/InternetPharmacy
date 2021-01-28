import React from "react";
import {Button, Modal} from "react-bootstrap";
import {ButtonGroup, Input} from "rsuite";
import Dropdown from "react-dropdown";


const options = [
    'Xanax | 3', 'Brufen | 4', 'Linex | 100'
];
const defaultOption = options[0];

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
        }
    }

    componentDidMount() {

        let medicationOrders = [
            {
                pharmacyAdmin : {
                    firstName : 'Mirko',
                    lastName : 'Mirkovic'
                },
                deadLine : '21.3.2021.',
                medicationQuantity: {

                },
                status : 'pending',
                medicationOffers : []
            },
            {
                pharmacyAdmin : {
                    firstName : 'Jelena',
                    lastName : 'Rozga'
                },
                deadLine : '13.5.2021.',
                medicationQuantity: {

                },
                status : 'processed',
                medicationOffers : []
            }
        ];

        this.setState({
            medicationOrders : medicationOrders
        })
    }

    render() {

        return (
            <div className="container-fluid">

                <h1>Medication orders</h1>


                <br/>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Created by</th>
                        <th scope="col">Dead line</th>
                        <th scope="col">Medications</th>
                        <th scope="col">Offer</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medicationOrders.map((medicationOrder, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOrder.pharmacyAdmin.firstName + ' ' + medicationOrder.pharmacyAdmin.lastName}</td>
                            <td>{medicationOrder.deadLine}</td>
                            <td>
                                <Dropdown options={options}  value={defaultOption} />
                            </td>
                            <td>
                                <Button variant="primary" onClick={this.showOffersButtonClick}>
                                    Create offer
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

            </div>
        );
    }
    showOffersButtonClick = () => {

    }



}
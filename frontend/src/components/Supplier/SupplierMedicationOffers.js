import React from "react";
import UnregisteredLayout from "../../layout/UnregisteredLayout";
import PharmacyListing from "../pharmacy/PharmacyListing";
import MedicationListing from "../MedicationListing";
import {Button, Modal} from "react-bootstrap";
import Dropdown from "react-dropdown";
import {ButtonGroup, Input} from "rsuite";
const options = [
    'Xanax | 3', 'Brufen | 4', 'Linex | 100'
];
const defaultOption = options[0];

export default class SupplierMedicationOffers extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            medicationOffers : [],
            selectedOption:"",
            medicationOffersPom:[]
        }
    }

    componentDidMount() {

        let medicationOffers = [
            {
                price : 12443,
                shippingDate : '20.2.2021.',
                status : 'pending',
                medicationOrder:{
                    pharmacyAdmin : {
                        firstName : 'Mirko',
                        lastName : 'Mirkovic'
                    },
                    deadLine : '21.3.2021.',
                    medicationQuantity: {

                    },
                    status : 'pending',
                }
            },
            {
                price : 23533,
                shippingDate : '30.3.2021.',
                status : 'approved',
                medicationOrder:  {
                    pharmacyAdmin : {
                        firstName : 'Jelena',
                            lastName : 'Rozga'
                    },
                    deadLine : '13.5.2021.',
                        medicationQuantity: {

                    },
                    status : 'processed',
                }
            },
            {
                price : 12400,
                shippingDate : '10.2.2021.',
                status : 'rejected',
                medicationOrder:
                    {
                        pharmacyAdmin : {
                            firstName : 'Jovana',
                                lastName : 'Tipsin'
                        },
                        deadLine : '23.04.2021.',
                            medicationQuantity: {

                    },
                status : 'processed',
            }
            }
        ]

        this.setState({
            medicationOffers : medicationOffers,
            medicationOffersPom:medicationOffers
        })
    }

    onValueChange=(event) =>{
        var option=event.target.value
        console.log(option)
        this.setState({
            selectedOption : option,
        })
        console.log(option)
        if(option=="All"){
            this.setState({
                medicationOffers: this.state.medicationOffersPom
            });

            console.log("dosao");
            return this.state.medicationOffers;

        }else {
            let filteredData = this.state.medicationOffers.filter(column => {

                return column.status.toLowerCase().indexOf(option.toLowerCase()) !== -1;
            });
            this.setState({
                medicationOffers: filteredData
            });
        }

    }

    render() {
        return (
            <div>
                <h2 style={{marginLeft:'2rem'}} >Offers</h2>
                <div className="row" style={{marginTop: '1rem', marginLeft:'2rem'}} >

                    <div className="form-check">
                        <b >Filter by  :</b>
                        <label>
                            <input type="radio" value="All" checked={this.state.selectedOption === "all"}onChange={this.onValueChange} />
                            All
                        </label>
                    </div>
                    <div className="radio">
                        <label>
                            <input type="radio" value="Pending" checked={this.state.selectedOption === "pending"}onChange={this.onValueChange} />
                            Pending
                        </label>
                    </div>
                    <div className="radio">
                        <label>
                            <input  type="radio"  value="Approved" checked={this.state.selectedOption === "approved"} onChange={this.onValueChange}/>
                            Approved
                        </label>
                    </div>
                    <div className="radio">
                        <label>
                            <input type="radio" value="Rejected" checked={this.state.selectedOption === "rejected"} onChange={this.onValueChange}/>
                            Rejected
                        </label>
                    </div>
                </div>

                <table className="table table-hover" >
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Created by</th>
                        <th scope="col">Dead line</th>
                        <th scope="col">Medications</th>
                        <th scope="col">Price</th>
                        <th scope="col">Shipping date</th>
                        <th scope="col">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.medicationOffers.map((medicationOffer, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOffer.medicationOrder.pharmacyAdmin.firstName + ' ' + medicationOffer.medicationOrder.pharmacyAdmin.lastName}</td>
                            <td>{medicationOffer.medicationOrder.deadLine}</td>
                            <td>
                                <Dropdown options={options}  value={defaultOption} />
                            </td>
                            <td>{medicationOffer.price}</td>
                            <td>{medicationOffer.shippingDate}</td>
                            <td>{medicationOffer.status}</td>
                        </tr>

                    ))}
                    </tbody>
                </table>

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.handleModal}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
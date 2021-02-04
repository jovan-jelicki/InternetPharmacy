import React from "react";
import {Button, Modal} from "react-bootstrap";
import Dropdown from "react-dropdown";
import axios from "axios";

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

  /*  componentDidMount() {

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
    }*/

    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/suppliers/getAllBySupplier/'+1)
            .then((res) => {
                this.setState({
                    medicationOffers : res.data
                })
                console.log("USEO")
                console.log(this.state.medicationOffers);
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
                        <th scope="col">Offer status</th>
                        <th scope="col">Offer status</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.medicationOffers.map((medicationOffer, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{medicationOffer.pharmacyAdminId}</td>
                            <td>{medicationOffer.deadline.split("T")[0]}</td>
                            <td>
                                {medicationOffer.medicationQuantity.map((e, key) => {
                                    return <option key={key} value={e.medication}>{e.medication.name} | {e.quantity}</option>

                                })
                                }
                            </td>
                            <td>{medicationOffer.cost}</td>
                            <td>{medicationOffer.shippingDate.split("T")[0]}</td>
                            <td>{medicationOffer.offerStatus}</td>
                            <td>{medicationOffer.orderStatus}</td>
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
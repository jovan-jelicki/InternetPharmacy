import React from "react";
import {Button} from "react-bootstrap";
import axios from "axios";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";


export default class MedicationOffers extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medicationOrder : props.order,
            medicationOffers : [],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : this.props.pharmacy.id
        }
    }

    componentDidMount() {
        console.log(this.state.medicationOrder)
        // let temp = await PharmacyAdminService.fetchPharmacyId();
        // this.setState({
        //     pharmacyId : temp
        // })
        this.fetchMedicationOffers();
    }

    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <Button style={({ marginLeft: '1rem', marginBottom: '1rem' })} variant={"outline-info"} onClick={() => this.props.showListOrders("listOrders")}>{"←"}</Button>
                        <br/>
                        <div className="col-md-12">
                            <div className="card">
                                <h5 className="card-header">
                                    Medication order
                                </h5>

                                <div className="card-body">
                                    <p className="card-text">
                                        Created by : {this.state.medicationOrder.pharmacyAdminFirstName + " " + this.state.medicationOrder.pharmacyAdminLastName}
                                        <br/>
                                        Status : {this.state.medicationOrder.status}
                                        <br/>
                                        Shipping deadline : {moment(this.state.medicationOrder.deadline).format("DD.MM.YYYY")}
                                        <br/>
                                        <br/>
                                        Medications
                                        <ul>
                                            {this.state.medicationOrder.medicationQuantity.map((medicationQuantity,index) =>
                                                <li key={index}>{medicationQuantity.medication.name + " : " + medicationQuantity.quantity}</li>
                                            )}
                                        </ul>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <br/>
                <div style={{marginLeft : '1rem'}}>
                    <h2>Offers</h2>
                    <table className="table table-hover" >
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Supplier</th>
                            <th scope="col">Price</th>
                            <th scope="col">Shipping date</th>
                            <th scope="col">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.medicationOffers.map((medicationOffer, index) => (
                            <tr key={index}>
                                <th scope="row">{index+1}</th>
                                <td>{medicationOffer.supplierFirstName + " " + medicationOffer.supplierLastName}</td>
                                <td>{medicationOffer.cost}</td>
                                <td>{moment(medicationOffer.shippingDate).format("DD.MM.YYYY")}</td>
                                <td>{medicationOffer.status}</td>
                                <td style={medicationOffer.status === 'pending' ? {display : 'inline-block'} : {display : 'none'}}>
                                    <Button variant="outline-success" onClick={() => this.acceptOffer(medicationOffer)}>
                                        Accept
                                    </Button>
                                </td >
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }

    acceptOffer = (medicationOffer) => {
        axios.put(HelperService.getPath("/api/medicationOffer/acceptOffer/" + this.state.user.id), medicationOffer,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                alert("Medication offer accepted successfully!");
                this.fetchMedicationOffers();
                //this.handleModal();
            })
            .catch(() => {
                alert("Medication offer was not accepted successfully!");
            })
    }

    fetchMedicationOffers = () => {
        axios.get(HelperService.getPath("/api/medicationOffer/getOffersByOrderId/" + this.state.medicationOrder.id), {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                this.setState({
                    medicationOffers : res.data
                })
            })
    }
}
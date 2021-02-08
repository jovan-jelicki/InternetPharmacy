import React from 'react';
import {Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import AddMedicationQuantity from "./AddMedicationQuantity";
import OrderQuantityListing from "./OrderQuantityListing";
import axios from "axios";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";

export default class EditOrder extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userType : 'pharmacyAdmin',
            medications : [],
            medicationOrder : {
                id : this.props.order.id,
                deadline : "",
                pharmacyAdminId : 1,
                status : "pending",
                medicationQuantity : []
            },
            quantities : this.props.order.medicationQuantity,
            deadline : moment(this.props.order.deadline).toDate(),
            isEditable : false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp
        })
        this.checkIfOrderIsEditable();
        console.log(this.props.order);
        this.fetchMedication();
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    {
                        this.state.isEditable &&
                        <div>
                            <h1>
                                Edit order
                            </h1>
                            <Button variant={"outline-info"} onClick={() => this.props.showListOrders("listOrders")}>{"←"}</Button>
                            <br/><br/>
                            Choose deadline for medication offers: <i/><i/><i/>
                            <DatePicker  selected={this.state.deadline}  minDate={new Date()} onChange={this.changeDatePicker} />
                            <br/><br/>
                            <AddMedicationQuantity addQuantity={this.addQuantity} medications = {this.state.medications} />

                            <OrderQuantityListing quantities={this.state.quantities} removeQuantity ={this.removeQuantity}/>


                            <Button variant={"dark"} onClick={this.handleSubmit}>Submit</Button>

                        </div>
                    }
                    {
                        !this.state.isEditable &&
                            <div>
                                <h1>
                                    Cannot edit this order because suppliers have already made offers for it.
                                </h1>
                                <Button variant={"outline-info"} onClick={() => this.props.showListOrders("listOrders")}>{"←"}</Button>
                            </div>
                    }

                </div>
            </div>
        );
    }

    checkIfOrderIsEditable = () => {
        const path = "http://localhost:8080/api/medicationOrder/checkIfOrderIsEditable/" + this.props.order.id;
        axios.get(path, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                this.setState({
                    isEditable : res.data
                })
            })
    }

    fetchMedication = () => {
        axios.get("http://localhost:8080/api/medications", {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
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
            deadline : date
        });
        console.log(date);
    }

    convertDates = (date) => {
        return moment(date).format('YYYY-MM-DD');
    }

    handleSubmit = async () => {
        await this.setState({
                medicationOrder : {
                    ...this.state.medicationOrder,
                    medicationQuantity : this.state.quantities,
                    deadline : this.state.deadline
                }
            }
        );
        console.log(this.state.medicationOrder);
        if (this.state.medicationOrder.medicationQuantity.length === 0) {
            alert("Cannot submit medication order without any medications!");
            return;
        }

        axios.post("http://localhost:8080/api/medicationOrder/editMedicationOrder", this.state.medicationOrder, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                alert("Medication order edited successfully!");
                this.props.showListOrders("listOrders")
            })
            .catch(() => alert("Medication order was not edited successfully! Please try again later."))
    }
}
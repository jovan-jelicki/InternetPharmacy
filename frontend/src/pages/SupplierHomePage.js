import React from "react";
import PharmacyRegistration from "../components/SystemAdmin/PharmacyRegistration";
import DermatologistRegistration from "../components/SystemAdmin/DermatologistRegistration";
import SupplierRegistration from "../components/SystemAdmin/SupplierRegistration";
import AddNewMedication from "../components/SystemAdmin/AddNewMedication";
import Complaints from "../components/SystemAdmin/Complaints";
import LoyaltyProgram from "../components/SystemAdmin/LoyaltyProgram";
import PharmacyAdminRegistration from "../components/SystemAdmin/PharmacyAdminRegistration";
import MedicationOrdersForSupplier from "../components/Supplier/MedicationOrdersForSupplier";
import MedicationOrdersList from "../components/pharmacy/MedicationOrdersList";
import MedicationOffers from "../components/pharmacy/MedicationOffers";
import CreateOrder from "../components/pharmacy/CreateOrder";
import CreateNewOffer from "../components/Supplier/CreateNewOffer";
import SupplierMedicationOffers from "../components/Supplier/SupplierMedicationOffers";
import SupplierProfile from "../components/Supplier/SupplierProfile";
import SupplierMedicationListing from "../components/Supplier/SupplierMedicationListing";
import {Button, Modal} from "react-bootstrap";
import axios from "axios";

export default class SupplierHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state= {
            supplier:{
                firstName:'',
                lastName:'',
            },
            navbar : "",
            showContent:"",
            showModal : false,
            oldPw : "",
            newPw : "",
            repeatPw : "",
            repErr : "",
            wrongPw : ""
        }
    }
    componentDidMount() {
        console.log("LOKACIJA")
        console.log(this.props.location)
        let  supplier= {
            firstName: 'Manja',
            lastName: 'Babic',
        }
        this.setState({
            supplier : supplier
        })

        axios
            .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/suppliers/isAccountApproved/' + 1)
            .then(res => {
                if(!res.data){
                    this.setState({
                        showModal : true
                    })
                }
            })
            .catch(res => alert("Greska!"));

    }

    render() {
        return (
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.state.supplier.firstName +" " +this.state.supplier.lastName}</h1>
                </div>
                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="order">Medication orders</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="offer" onClick={this.handleChange}>My medication offers</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medications" onClick={this.handleChange}>Medication Listing</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="profile" onClick={this.handleChange}>Profile</a>
                    </li>
                </ul>
                {this.renderNavbar()}
                {this.showModalDialog()}
            </div>
        );
    }

    showModalDialog = () => {
        return (
            <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                <Modal.Header>
                    <Modal.Title>Verify account!</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p> You have to change password when you log in for first time.</p> <br/>
                    <p> First password : </p> <input name="oldPw" onChange={this.handleInputChange} value={this.state.oldPw} type={"password"}/>
                    <p> New password : </p> <input name="newPw" onChange={this.handleInputChange} value={this.state.newPw} type={"password"}/>
                    <p> Repeat new password : </p> <input name="repeatPw" onChange={this.handleInputChange} value={this.state.repeatPw} type={"password"}/>
                    <p style={{"color" : "red"}}>{this.state.repErr} </p>

                </Modal.Body>
                <Modal.Footer>
                    <p style={{"color" : "red"}}>{this.state.wrongPw}</p>
                    <Button variant="secondary" onClick={this.sendData}>
                        Send
                    </Button>
                </Modal.Footer>
            </Modal>
        )
    }


    sendData = () => {
        if(this.state.repeatPw !== this.state.newPw)
            return;
        axios
            .put(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/suppliers/pass', {
                'userId' : 1,
                'oldPassword' : this.state.oldPw,
                'newPassword' : this.state.newPw,
                'repeatedPassword' : this.state.repeatPw
            })
            .then(res => {
                if(!res.data){
                    this.setState({
                        showModal : false
                    })
                }
            })
            .catch(res => this.setState({wrongPw : "First password is not correct!"}));
    }


    handleInputChange = (event) => {
        const target = event.target;
        this.setState({
            [target.name]: target.value
        })
        this.validatePassword(event);
    }

    validatePassword(event) {
        let repErr = ''
        let val = event.target.value;
        let newPass = this.state.newPw;

        if (event.target.name === 'repeatPw')
            if(val !== newPass.substr(0, Math.min(val.length, newPass.length)) ||
                (val.trim() === '' && newPass.trim() !== '')) {
                repErr = 'This password must match the previous';
            }

        this.setState({
            'repErr': repErr
        })
    }


    handleChange = (event) => {
        const target = event.target;
        const name = target.name;

        this.setState({
            navbar : name
        });
    }

    renderNavbar = () => {
        if (this.state.navbar === "order")
            return (
                <MedicationOrdersForSupplier/>
            );
        else if (this.state.navbar === "offer")
            return (
                <SupplierMedicationOffers/>
            );
        else if (this.state.navbar === "profile")
            return (
                <SupplierProfile/>
            );
        else if (this.state.navbar === "medications")
            return (
                <SupplierMedicationListing/>
            );

    }



}
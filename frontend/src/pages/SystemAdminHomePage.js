import React from "react";
import PharmacyRegistration from "../components/SystemAdmin/PharmacyRegistration";
import DermatologistRegistration from "../components/SystemAdmin/DermatologistRegistration";
import SupplierRegistration from "../components/SystemAdmin/SupplierRegistration";
import AddNewMedication from "../components/SystemAdmin/AddNewMedication";
import Complaints from "../components/SystemAdmin/Complaints";
import LoyaltyProgram from "../components/SystemAdmin/LoyaltyProgram";
import PharmacyAdminRegistration from "../components/SystemAdmin/PharmacyAdminRegistration";
import SystemAdminVacationRequestListing from "../components/SystemAdmin/SystemAdminVacationRequestListing";
import SystemAdminRegistration from "../components/SystemAdmin/SystemAdminRegistration";
import {Button, Modal} from "react-bootstrap";
import axios from "axios";

export class SystemAdminHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state= {
            pharmacyAdmin:{
                firstName:'',
                lastName:'',
            },
            navbar : "",
            showModal : false,
            oldPw : "",
            newPw : "",
            repeatPw : "",
            repErr : "",
            wrongPw : "",
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

        }
    }
    componentDidMount() {
        if (this.state.user.type == undefined || this.state.user.type != "ROLE_systemAdmin")
            this.props.history.push({
                pathname: "/unauthorized"
            });

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/systemAdmin/isAccountApproved/"
            : 'http://localhost:8080/api/systemAdmin/isAccountApproved/';
        axios
            .get(path + this.state.user.id,
                {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
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
                    <h1 className="display-4">{this.state.pharmacyAdmin.firstName +" " +this.state.pharmacyAdmin.lastName }</h1>
                </div>

                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <a className="nav-link active" href='#' onClick={this.handleChange} name="pharmacy">Pharmacy registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="pharmacyAdmin" onClick={this.handleChange}>Pharmacy admin registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="medication" onClick={this.handleChange}>Add medication</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="dermatologist" onClick={this.handleChange}>Dermatologist registration</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="supplier" onClick={this.handleChange}>Supplier registration</a>
                    </li>

                    <li className="nav-item">
                        <a className="nav-link" href="#" name="complaint" onClick={this.handleChange}>Complaint</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="loyalty" onClick={this.handleChange}>Loyalty program</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="dermatologistVacationRequests" onClick={this.handleChange}>Dermatologist vacation requests</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href="#" name="systemAdmin" onClick={this.handleChange}>System admin registration</a>
                    </li>
                    <button type="button" className="btn btn-dark"  onClick={this.logOut}>Log out</button>

                </ul>
                {this.renderNavbar()}
                {this.showModalDialog()}
            </div>
        );
    }
    logOut = () => {
        localStorage.removeItem("user");
        this.props.history.push({
            pathname: "/"
        });
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

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/systemAdmin/pass"
            : 'http://localhost:8080/api/systemAdmin/pass';

        axios
            .put(path, {
                'userId' : 1,
                'oldPassword' : this.state.oldPw,
                'newPassword' : this.state.newPw,
                'repeatedPassword' : this.state.repeatPw
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
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
        if (this.state.navbar === "pharmacy")
            return (
                <PharmacyRegistration/>
            );
        else if (this.state.navbar === "dermatologist")
            return (
                <DermatologistRegistration/>
            );
        else if (this.state.navbar === "supplier")
            return (
                <SupplierRegistration/>
            );
        else if (this.state.navbar === "medication")
            return (
                <AddNewMedication/>
            );
        else if (this.state.navbar === "complaint")
            return (
                <Complaints/>
            );
        else if (this.state.navbar === "loyalty")
            return (
                <LoyaltyProgram/>
            );
        else if (this.state.navbar === "pharmacyAdmin")
            return (
                <PharmacyAdminRegistration/>
            );
        else if (this.state.navbar === "dermatologistVacationRequests")
            return (
                <SystemAdminVacationRequestListing/>
            );
        else if (this.state.navbar === "systemAdmin")
            return (
                <SystemAdminRegistration/>
            );
    }
}
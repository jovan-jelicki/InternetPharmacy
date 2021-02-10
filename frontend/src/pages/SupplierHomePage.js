import React from "react";
import MedicationOrdersForSupplier from "../components/Supplier/MedicationOrdersForSupplier";
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
            role: this.props.role,
            Id: this.props.Id,
            navbar : "",
            showContent:"",
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
        if (this.state.user.type == undefined || this.state.user.type != "ROLE_supplier")
            this.props.history.push({
                pathname: "/unauthorized"
            });

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/suppliers/isAccountApproved/"
            : 'http://localhost:8080/api/suppliers/isAccountApproved/';
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
                    <Button onClick={this.logOut}>Log out</Button>
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
        console.log(this.state.oldPw)
        if(this.state.repeatPw !== this.state.newPw)
            return;

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/suppliers/pass"
            : 'http://localhost:8080/api/suppliers/pass';

        axios
            .put(path, {
                'userId' : this.state.user.id,
                'oldPassword' : this.state.oldPw,
                'newPassword' : this.state.newPw,
                'repeatedPassword' : this.state.repeatPw
            }, {
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
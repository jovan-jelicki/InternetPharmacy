import React from "react";
import {Container, Button, Modal} from "react-bootstrap";
import ReviewedClients from "../ReviewedClients";
import VacationRequest from "../VacationRequest";
import DermatologistsProfilePage from "./DermatologistsProfilePage";
import DermatologistWorkingHours from "./DermatologistWorkingHours";
import DermatologistAppointmentStart from "./DermatologistAppointmentStart";
import axios from "axios";

export default class DermatologistHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            navbar : "reviewedClients",
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
        axios
            .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/dermatologists/isAccountApproved/' + this.state.user.id,
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
            <div>
                <Container fluid style={{'background-color' : '#AEB6BF'}}>
                    <br/>
                <ul className="nav justify-content-center">
                    <h3> Welcome dermatologist! </h3>
                    <li className="nav-item">
                        <a className="nav-link active" style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' onClick={this.handleChange} name="reviewedClients">Reviewed clients</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="startAppointment" onClick={this.handleChange}>Start appointment</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="workHours" onClick={this.handleChange}>Work hours</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="vacationRequest" onClick={this.handleChange}>Vacation request</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="profile" onClick={this.handleChange}>Account</a>
                    </li>
                </ul>
                </Container>
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
            .put(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/dermatologists/pass', {
                'userId' : this.state.user.id,
                'oldPassword' : this.state.oldPw,
                'newPassword' : this.state.newPw,
                'repeatedPassword' : this.state.repeatPw
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
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
        if (this.state.navbar === "reviewedClients")
            return (
                <ReviewedClients />
            );
        else if (this.state.navbar === "vacationRequest")
            return (
                <VacationRequest  />
            );
        else if (this.state.navbar === "profile")
            return (
                <DermatologistsProfilePage  />
            );
        else if (this.state.navbar === "workHours")
            return (
                <DermatologistWorkingHours />
            );
        else if (this.state.navbar === "startAppointment")
            return (
                <DermatologistAppointmentStart />
            );
        else
            return (
              <div><p>Proba</p></div>
            );
    }
}
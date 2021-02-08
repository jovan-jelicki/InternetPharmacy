import React from "react";
import {Col, Container, Nav, Row, Button, Toast, Navbar, NavDropdown, Modal} from "react-bootstrap";
import UserInfo from "../components/UserInfo";
import ChangePassword from "../components/ChangePassword";
import axios from "axios"
import PatientLayout from "../layout/PatientLayout";
import PharmacyAdminLayout from "../layout/PharmacyAdminLayout";
import AuthentificationService from "../helpers/AuthentificationService";
import {NavLink} from "react-router-dom";
import HelperService from "../helpers/HelperService";

export default class PharmacyAdminProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            'firstName' : '',
            'lastName' : '',
            'email' : '',
            'address' : '',
            'town' : '',
            'country' : '',
            'phoneNumber' : '',
            'oldPass' : '',
            'newPass' : '',
            'repPass' : '',
            'editMode' : false,
            'saveDisabled' : false,
            'pharmacyName' : '',
            'pharmacyId' : -1,
            showModal : false,
            oldPw : "",
            newPw : "",
            repeatPw : "",
            repErr : "",
            wrongPw : ""
        }
    }

    async componentDidMount() {
        this.validateUser();
        console.log(this.state.user);
        await axios
            .get('http://localhost:8080/api/pharmacyAdmin/' + this.state.user.id, {
              headers: {
                'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                let pharmacyAdmin = res.data;
                console.log(pharmacyAdmin)
                this.setState({
                    'id' : pharmacyAdmin.id,
                    'firstName' : pharmacyAdmin.firstName,
                    'lastName' : pharmacyAdmin.lastName,
                    'email' : pharmacyAdmin.email,
                    'userType' : pharmacyAdmin.userType,
                    'editMode' : false,
                    'changePasswordMode' : false,
                    'address' : pharmacyAdmin.contact.address.street,
                    'town' : pharmacyAdmin.contact.address.town,
                    'country' : pharmacyAdmin.contact.address.country,
                    'phoneNumber' : pharmacyAdmin.contact.phoneNumber,
                    'pharmacyName' : pharmacyAdmin.pharmacyName,
                    'pharmacyId' : pharmacyAdmin.pharmacyId
                })
            });

        this.createBackupUser();
        this.checkFirstLoginAccount();
    }


    render() {

        const {oldPass, newPass, repPass} = this.state;
        const passwords = [oldPass, newPass, repPass]

        return (
            <Container fluid style={{'background-color' : '#AEB6BF'}}>
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Navbar.Brand as={NavLink} to="/">WebPharm</Navbar.Brand>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                            <NavDropdown title={"My pharmacy"} id="collasible-nav-dropdow">
                                <NavDropdown.Item onClick={this.redirectToPharmacy}>{this.state.pharmacyName}</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <NavDropdown title="Account" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="/pharmacy-admin-profile">My Account</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Row className="pt-5">
                    <Col xs={2}>
                        <Nav defaultActiveKey="/home" className="flex-column">
                            {!this.state.editMode
                                ? <Button variant="dark" onClick={this.activateUpdateMode}>Edit</Button>
                                : <Button variant="outline-secondary" onClick={this.activateUpdateMode}>Cancel</Button>
                            }
                            {this.state.editMode && <Button variant="primary mt-2"
                                                            onClick={this.activateChangePasswordMode}>
                                Change Password</Button>}
                            {this.state.editMode && <Button variant="success mt-2"
                                                            disabled={this.state.saveDisabled} onClick={this.save}>Save</Button>}
                        </Nav>
                    </Col>
                    <Col>
                        <UserInfo user={this.state} edit={this.state.editMode} onChange={this.handleInputChange}/>

                        {this.state.changePasswordMode && <ChangePassword pass={passwords} onChange={this.handleInputChange} disable={this.disableSave}/>}
                    </Col>
                </Row>

                {this.showModalDialog()}
            </Container>
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
            .put(HelperService.getPath('/api/pharmacyAdmin/pass'), {
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

    createBackupUser = () => {
        this.user = {
            'firstName' : this.state.firstName,
            'lastName' : this.state.lastName,
            'email' : this.state.email,
            'address' : this.state.address,
            'town' : this.state.town,
            'country' : this.state.country,
            'phoneNumber' : this.state.phoneNumber,
        }

        this.setState({
            'editMode' : false,
            'changePasswordMode' : false
        })
    }

    activateUpdateMode = () => {
        this.resetData();
        this.setState({
            editMode : !this.state.editMode,
            changePasswordMode : false
        })
    }

    activateChangePasswordMode = () => {
        this.setState({
            'saveDisabled' : !this.state.changePasswordMode,
            'changePasswordMode' : !this.state.changePasswordMode,
            'oldPass' : '',
            'newPass' : '',
            'repPass' : ''
        })
    }

    changePass = () => {
        axios
            .put('http://localhost:8080/api/pharmacyAdmin/pass', {
                    'userId' : this.state.id,
                    'oldPassword' : this.state.oldPass,
                    'newPassword' : this.state.newPass,
                    'repeatedPassword' : this.state.repPass
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
            .then(res => {
                this.setState({
                    'password' : this.state.newPass
                })
            })
            .catch(e => alert('Nisam pijana i nece moci!!!'));
    }

    handleInputChange = (event) => {
        const target = event.target;
        this.setState({
            [target.name] : target.value,
        })
    }

    resetData = () => {
        this.setState({
            'firstName' : this.user.firstName,
            'lastName' : this.user.lastName,
            'address' : this.user.address,
            'town' : this.user.town,
            'country' : this.user.country,
            'phoneNumber' : this.user.phoneNumber,
        })
    }

    disableSave = (disable) => {
        this.setState({
            'saveDisabled' : disable
        })
    }



    save = () => {
        axios
            .put('http://localhost:8080/api/pharmacyAdmin', {
                'id' : this.state.id,
                'firstName' : this.state.firstName,
                'lastName' : this.state.lastName,
                'userType' : this.state.userType,
                'credentials' : {
                    'email' : this.state.email,
                    'password' : this.state.password
                },
                'contact' : {
                    'phoneNumber' : this.state.phoneNumber,
                    'address' : {
                        'street' : this.state.address,
                        'town' : this.state.town,
                        'country' : this.state.country
                    }
                }
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                if(this.state.changePasswordMode)
                    this.changePass();
                this.createBackupUser();
            });

    }


    validateUser = () => {
        if (!AuthentificationService.isLoggedIn() || this.state.user.type !== 'ROLE_pharmacyAdmin')
            this.props.history.push({
                pathname: "/unauthorized"
            });
    }

    redirectToPharmacy = () => {
        sessionStorage.setItem("pharmacyId", this.state.pharmacyId);
        this.props.history.push({
            pathname: "/pharmacy",
            state : {
                pharmacyId : this.state.pharmacyId
            }
        });
    }

    checkFirstLoginAccount = () => {
        axios
            .get(HelperService.getPath("/api/pharmacyAdmin/isAccountApproved/" + this.state.user.id),
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
}
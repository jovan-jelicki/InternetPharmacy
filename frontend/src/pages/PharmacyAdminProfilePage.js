import React from "react";
import {Col, Container, Nav, Row, Button, Toast} from "react-bootstrap";
import UserInfo from "../components/UserInfo";
import ChangePassword from "../components/ChangePassword";
import axios from "axios"
import PatientLayout from "../layout/PatientLayout";
import PharmacyAdminLayout from "../layout/PharmacyAdminLayout";

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
            'saveDisabled' : false
        }
    }

    async componentDidMount() {
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
                    'phoneNumber' : pharmacyAdmin.contact.phoneNumber
                })
            });

        this.createBackupUser();
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

    render() {

        const {oldPass, newPass, repPass} = this.state;
        const passwords = [oldPass, newPass, repPass]

        return (
            <PharmacyAdminLayout>
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
            </PharmacyAdminLayout>
        );
    }
}
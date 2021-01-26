import React from "react";
import {Container, Row, Col, Nav, Button} from "react-bootstrap";
import UserInfo from "../components/UserInfo";
import ChangePassword from "../components/ChangePassword";
import axios from "axios";

//TODO Za sada namerno postoje dve iste stranice za profil dermatologa i farmaceuta, u toku rada uvideti da li je to zaista potrebno
export default class PharmacistProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'firstName' : '',
            'lastName' : '',
            'email' : '',
            'address' : '',
            'town' : '',
            'country' : '',
            'phoneNumber' : '',
            'longitude' : '',
            'latitude' : '',
            'oldPass' : '',
            'newPass' : '',
            'repPass' : '',
            'editMode' : false,
            'saveDisabled' : false
        }
    }

    async componentDidMount() {

        await axios
            .get('http://localhost:8080/api/pharmacist/1')
            .then(res => {
                let patient = res.data;
                console.log(patient)
                this.setState({
                    'id' : patient.id,
                    'firstName' : patient.firstName,
                    'lastName' : patient.lastName,
                    'email' : patient.credentials.email,
                    'password' : patient.credentials.password,
                    'userType' : patient.userType,
                    'editMode' : false,
                    'changePasswordMode' : false,
                    'address' : patient.contact.address.street,
                    'longitude' : patient.contact.address.longitude,
                    'latitude' : patient.contact.address.latitude,
                    'town' : patient.contact.address.town,
                    'country' : patient.contact.address.country,
                    'phoneNumber' : patient.contact.phoneNumber
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
            'phoneNumber' : this.state.phoneNumber
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
            .put('http://localhost:8080/api/pharmacist/pass', {
                'userId' : this.state.id,
                'oldPassword' : this.state.oldPass,
                'newPassword' : this.state.newPass,
                'repeatedPassword' : this.state.repPass
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
            'phoneNumber' : this.user.phoneNumber
        })
    }

    disableSave = (disable) => {
        this.setState({
            'saveDisabled' : disable
        })
    }


    save = () => {
        axios
            .put('http://localhost:8080/api/pharmacist', {
                'id' : this.state.id,
                'firstName' : this.state.firstName,
                'lastName' : this.state.lastName,
                'userType' : this.state.userType,
                'credentials' : {
                    'email' : this.state.email,
                    'password' : this.state.password
                },
                'penaltyCount' : this.state.penaltyCount,
                'contact' : {
                    'phoneNumber' : this.state.phoneNumber,
                    'address' : {
                        'street' : this.state.address,
                        'town' : this.state.town,
                        'country' : this.state.country,
                        'latitude' : this.state.latitude,
                        'longitude' : this.state.longitude
                    }
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
                    {this.state.changePasswordMode &&
                    <ChangePassword pass={passwords} onChange={this.handleInputChange} disable={this.disableSave}/>}
                </Col>
            </Row>
        );
    }
}
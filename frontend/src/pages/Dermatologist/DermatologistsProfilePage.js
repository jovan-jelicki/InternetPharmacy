import React from "react";
import {Row, Col, Nav, Button} from "react-bootstrap";
import UserInfo from "../../components/UserInfo";
import ChangePassword from "../../components/ChangePassword";
import axios from "axios";

export default class DermatologistsProfilePage extends React.Component {
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
            .get('http://localhost:8080/api/dermatologists/'  + 3)
            .then(res => {
                let dermatologist = res.data;
                console.log(dermatologist)
                this.setState({
                    'id' : dermatologist.id,
                    'firstName' : dermatologist.firstName,
                    'lastName' : dermatologist.lastName,
                    'email' : dermatologist.email,
                    'userType' : dermatologist.userType,
                    'editMode' : false,
                    'changePasswordMode' : false,
                    'address' : dermatologist.contact.address.street,
                    'longitude' : dermatologist.contact.address.longitude,
                    'latitude' : dermatologist.contact.address.latitude,
                    'town' : dermatologist.contact.address.town,
                    'country' : dermatologist.contact.address.country,
                    'phoneNumber' : dermatologist.contact.phoneNumber
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
            .put('http://localhost:8080/api/dermatologists/pass', {
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
            .catch(e => alert('Some password is not correct!'));
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
            .put('http://localhost:8080/api/dermatologists', {
                'id' : this.state.id,
                'firstName' : this.state.firstName,
                'lastName' : this.state.lastName,
                'userType' : this.state.userType,
                'credentials' : {
                    'email': this.state.email,
                },
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
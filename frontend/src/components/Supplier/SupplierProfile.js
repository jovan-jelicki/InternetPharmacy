import React from "react";
import axios from "axios";
import {Button, Col, Nav, Row} from "react-bootstrap";
import UserInfo from "../UserInfo";
import ChangePassword from "../ChangePassword";
import HelperService from "../../helpers/HelperService";


export default class SupplierProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state={
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
            'saveDisabled' : false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

        }

    }

    async componentDidMount() {
        await axios
            .get(HelperService.getPath('/api/suppliers/'  + this.state.user.id),
                {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
            .then(res => {
                let supplier = res.data;
                console.log(supplier)
                this.setState({
                    'id' : supplier.id,
                    'firstName' : supplier.firstName,
                    'lastName' : supplier.lastName,
                    'email' : supplier.email,
                    'userType' : supplier.userType,
                    'editMode' : false,
                    'changePasswordMode' : false,
                    'address' : supplier.contact.address.street,
                    'longitude' : supplier.contact.address.longitude,
                    'latitude' : supplier.contact.address.latitude,
                    'town' : supplier.contact.address.town,
                    'country' : supplier.contact.address.country,
                    'phoneNumber' : supplier.contact.phoneNumber
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
     //   const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/suppliers/pass"
     //       : 'http://localhost:8080/api/suppliers/pass';
        axios
            .put(HelperService.getPath('/api/suppliers/pass'), {
                'userId' : this.state.id,
                'oldPassword' : this.state.oldPass,
                'newPassword' : this.state.newPass,
                'repeatedPassword' : this.state.repPass
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
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
       // const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/suppliers"
        //    : 'http://localhost:8080/api/suppliers';
        axios
            .put(HelperService.getPath('/api/suppliers'), {
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
            }, {  headers: {
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
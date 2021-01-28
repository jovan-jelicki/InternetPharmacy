import React from "react";
import {Col, Container, Nav, Row, Button, Toast} from "react-bootstrap";
import UserInfo from "../../components/UserInfo";
import AllergyPatientListing from "../../components/AllergyPatientListing";
import AddAllergy from "../../components/AddAllergy";
import ChangePassword from "../../components/ChangePassword";
import axios from "axios"
import PatientLayout from "../../layout/PatientLayout";

export default class PatientProfilePage extends React.Component {
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
            'oldPass' : '',
            'newPass' : '',
            'repPass' : '',
            'allergies' : [],
            'editMode' : false,
            'saveDisabled' : false
        }
    }

    async componentDidMount() {

        await axios
            .get('http://localhost:8080/api/patients/1')
            .then(res => {
                let patient = res.data;
                console.log(patient)
                this.setState({
                    'id' : patient.id,
                    'firstName' : patient.firstName,
                    'lastName' : patient.lastName,
                    'email' : patient.credentials.email,
                    'password' : patient.credentials.password,
                    'penaltyCount' : patient.penaltyCount,
                    'userType' : patient.userType,
                    'editMode' : false,
                    'changePasswordMode' : false,
                    'address' : patient.contact.address.street,
                    'town' : patient.contact.address.town,
                    'country' : patient.contact.address.country,
                    'phoneNumber' : patient.contact.phoneNumber
                })
            });

        await axios
            .get('http://localhost:8080/api/patients/allergies/1')
            .then(res => {
                this.setState({
                    'allergies' : res.data
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
            'allergies' : [...this.state.allergies]
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
        .put('http://localhost:8080/api/patients/pass', {
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
            'phoneNumber' : this.user.phoneNumber,
            'allergies' : this.user.allergies
        })
    }

    disableSave = (disable) => {
        this.setState({
            'saveDisabled' : disable
        })
    }

    addAllergy = (allergy) => {
        if(!this.state.allergies.map(a => a.id).includes(allergy.id))
            this.setState({
                'allergies' : [...this.state.allergies, allergy]
            })
    }

    removeAllergy = (allergy) => {
        this.setState({
            'allergies' : [...this.state.allergies.filter(a => a.id != allergy)]
        })
    }

    save = () => {
        axios
        .put('http://localhost:8080/api/patients', {
            'id' : this.state.id,
            'firstName' : this.state.firstName,
            'lastName' : this.state.lastName,
            //'userType' : this.state.userType,
            'allergies' : this.state.allergies,
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
                    'country' : this.state.country
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
            <PatientLayout>
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
                        <h2 className="pt-4 pb-3">Allergies</h2>
                        {this.state.editMode && <AddAllergy addAllergy={this.addAllergy}/>}
                        <AllergyPatientListing edit={this.state.editMode}
                            allergies={this.state.allergies} removeAllergy={this.removeAllergy}/>
                        {this.state.changePasswordMode &&
                        <ChangePassword pass={passwords} onChange={this.handleInputChange} disable={this.disableSave}/>}
                    </Col>
                </Row>
            </PatientLayout>
        );
    }
}
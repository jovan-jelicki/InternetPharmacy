import React from 'react';
import {Button, Container, Form, Row} from "react-bootstrap";
import UnregisteredLayout from "../layout/UnregisteredLayout"
import PharmacyListing from "../components/pharmacy/PharmacyListing"
import MedicationListing from "../components/MedicationListing"
import DateTime from "./DateTime";
import ScheduleCounsel from "./ScheduleCounsel";
import PatientLayout from "../layout/PatientLayout";
import axios from "axios";

export default class CreateCoplaint extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            dermatologists:[],
            boolDermatologist:false,
            boolPharmacist:false,
            boolPharmacy:false,
            content:'',
            patient:{
                id: 0,
            },
            employeeId: null,
            errors: {
                    users: 'Choose complaint type',
                    content: 'Enter content',
            },
        }
    }

    async componentDidMount() {
        await axios.post("http://localhost:8080/api/appointment/getFinishedForComplaint",{
            id: this.state.patient.id,
            type:'dermatologist' //dermatolog/farmaceut
        }).then(res => {
            this.setState({
                dermatologists : res.data
            });
            console.log("Probaj")
            console.log(this.state.dermatologists)
        })


    }

    onTypeChange=(event) => {
        var option = event.target.id
        console.log(option)
        if(option==="dermatologist"){
            this.setState({boolDermatologist:true})
            this.setState({boolPharmacist:false})
            this.setState({boolPharmacy:false})

        }
        if(option==="pharmacist"){
            this.setState({boolDermatologist:false})
            this.setState({boolPharmacist:true})
            this.setState({boolPharmacy:false})
        }
        if(option==="pharmacy"){
            this.setState({boolDermatologist:false})
            this.setState({boolPharmacist:false})
            this.setState({boolPharmacy:true})
        }
    }

    handleInputChange = (event) => {
        console.log(event.target.value)
        const { name, value } = event.target;

        this.setState({ content:value });
        console.log(this.state.content)
        this.validationErrorMessage(event);
    }

    handleDermatologistSelected=(event)=>{
        console.log("DASAO")
        const target = event.target;
        let value = event.target.value;

        this.setState({
            employeeId:value
        })
        this.state.employeeId=value;
        console.log(this.state.employeeId)
        console.log(event);

        //this.validationErrorMessage(event)
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }

    validationErrorMessage = (event) => {
        console.log(event)
        const {name, value} = event.target;
        let errors = this.state.errors;
        console.log("NAME")
        console.log(name)
        switch (name) {
            case 'users':
                errors.users = value.length < 1 ? 'Choose complaint type' : '';
                break;
            case 'content':
                errors.content = value.length < 1 ? 'Enter content' : '';
                break;

            default:
                break;
        }

        this.setState({errors});
    }


    submitForm = async (event) => {
        this.setState({submitted: true});
        event.preventDefault();
        if(this.state.employeeId!=null){
            let errors = this.state.errors;
            errors.users='';
            this.setState({errors});
        }
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
            console.log(this.state.employeeId)
            this.sendData();
        } else {
            console.log(this.state.employeeId)
            console.log('Invalid Form')
        }
    }

    async sendData() {
        console.log( this.state.patient.id)
        console.log( this.state.content)
        console.log( this.state.employeeId)
        axios
            .post('http://localhost:8080/api/complaints/save', {
                'id':'',
                'patient' : this.state.patient,
                'content' : this.state.content,
                'type' : 0,
                'complaineeId' : this.state.employeeId,
            })
            .then(res => {

            });

    }

    render() {
        const dermatologist={

        }
        return (
            <PatientLayout>
                <Container fluid>
                    <h3 style={{marginLeft:'1rem', marginTop:'2rem'}}>Create complaint</h3>
                    <Form>
                        <Form.Group as={Row}>
                            <label style={{'marginLeft':'1.8rem', marginTop:'0.5rem'}}> Choose complaint type:</label>
                            <Row sm={10} style={{marginLeft:'1rem', marginTop:'0.6rem'}}>
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="dermatologist" name="userType"id="dermatologist" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="pharmacist" name="userType"id="pharmacist" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="pharmacy" name="userType" id="pharmacy" onChange={this.onTypeChange} />
                            </Row>
                        </Form.Group>
                    </Form>
                    <Form  style={{'marginLeft':'10rem', 'marginRight':'60rem'}}>
                        {
                        this.state.boolDermatologist && !this.state.boolPharmacist && !this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose dermatologist" as={"select"} value={this.state.dermatologists.employeeId}
                                      onChange={(e)=>{this.handleDermatologistSelected(e)}}>
                            <option disabled={true} selected="selected">Choose dermatologist</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.employeeId}
                                        value={dermatologist.employeeId}>{dermatologist.employeeFirstName} {dermatologist.employeeLastName}</option>
                            )}
                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && this.state.boolPharmacist && !this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose pharamacist" as={"select"} value={this.state.dermatologists.id}
                                      onChange={(e)=>{this.handlePharmacistSelected(e)}}>
                            <option disabled={true} selected="selected">Choose pharamacist</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.id}
                                        value={dermatologist.id}>{dermatologist.firstName} {dermatologist.lastName}</option>
                            )}

                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && !this.state.boolPharmacist && this.state.boolPharmacy &&
                        <Form.Control name="users" placeholder="Choose pharmacy" as={"select"} value={this.state.dermatologists.id}
                                      onChange={(e)=>{this.handlePharmacySelected(e)}}>
                            <option name="users" disabled={true} selected="selected">Choose pharmacy</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option name="users" key={dermatologist.id}
                                        value={dermatologist.id}>{dermatologist.firstName} {dermatologist.lastName}</option>
                            )}
                        </Form.Control>
                    }
                        {this.state.submitted && this.state.errors.users.length > 0 &&  <span className="text-danger">{this.state.errors.users}</span>}
                    </Form>
                    <label style={{'marginLeft': '1rem', marginTop: '0.5rem'}}> Pease write your complaint:</label>
                        <div className="row" style={{marginTop: '1rem', marginLeft: '2rem'}}>
                                <div className="col-sm-6 mb-2">
                                    <Form.Control as="textarea" name="content" rows={6} onChange={(e) => {this.handleInputChange(e)}} />
                                    {this.state.submitted && this.state.errors.content.length > 0 &&  <span className="text-danger">{this.state.errors.content}</span>}

                                </div>
                                <div className="col-sm-4">
                                </div>
                            </div>
                    <div className="row"style={{marginTop: '1rem'}}>
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div className="col-sm-4">
                            <Button variant="primary" onClick={this.submitForm} >Submit</Button>
                        </div>
                    </div>

                </Container>
            </PatientLayout>
        );
    }
}
import React from 'react';
import {Button, Container, Form, Modal, Row} from "react-bootstrap";
import PatientLayout from "../layout/PatientLayout";
import axios from "axios";

export default class CreateCoplaint extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            dermatologists:[],
            pharmacists:[],
            pharmacy:[],
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
            showModal:false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

        }
    }

    async componentDidMount() {
        await this.fetchDermatologist();
        await this.fetchPharmacist();
        await this.fetchPharmacy();
    }

    fetchDermatologist(){
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "api/appointment/getFinishedForComplaint"
            : 'http://localhost:8080/api/appointment/getFinishedForComplaint';
        axios.post(path,{
            id: this.state.patient.id,
            type: 0 //dermatolog/farmaceut
        },{
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + this.state.user.jwtToken
            }
        }).then(res => {
            this.setState({
                dermatologists : res.data
            });
            console.log("Probaj")
            console.log(this.state.dermatologists)
        })
    }

    fetchPharmacist(){
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacist/getPharmacistsByPatient/"
            : 'http://localhost:8080/api/pharmacist/getPharmacistsByPatient/';
        axios.get(path+this.state.patient.id,{
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + this.state.user.jwtToken
            }
        }) .then(res => {
                this.setState({
                    pharmacists : res.data
                });
                console.log("Probaj")
                console.log(this.state.pharmacists)
            })
    }

    fetchPharmacy(){
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/getAppointmentsPharmacyForComplaint/"
            : 'http://localhost:8080/api/appointment/getAppointmentsPharmacyForComplaint/';
        axios.get(path+this.state.patient.id,{
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then(res => {
                this.setState({
                    pharmacy : res.data
                });
                console.log("apoteke")
                console.log(this.state.pharmacy)
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

    handleOptionSelected=(event)=>{
        console.log("DASAO")
        const target = event.target;
        let value = event.target.value;

        this.setState({
            employeeId:value
        })
        this.state.employeeId=value;
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

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

    async sendData() {
        let type;
        if(this.state.boolPharmacist) type="pharmacist"
        else if(this.state.boolDermatologist) type="dermatologist"
        else type="pharmacy"
        axios
            .post('http://localhost:8080/api/complaints/save', {
                'id':'',
                'patient' : this.state.patient,
                'content' : this.state.content,
                'type' : type,
                'complaineeId' : this.state.employeeId,
                'active':true
            })
            .then(res => {
                this.handleModal();
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
                                      onChange={(e)=>{this.handleOptionSelected(e)}}>
                            <option disabled={true} selected="selected">Choose dermatologist</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.employeeId}
                                        value={dermatologist.employeeId}>{dermatologist.employeeFirstName} {dermatologist.employeeLastName}</option>
                            )}
                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && this.state.boolPharmacist && !this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose pharamacist" as={"select"} value={this.state.pharmacists.id}
                                      onChange={(e)=>{this.handleOptionSelected(e)}}>
                            <option disabled={true} selected="selected">Choose pharamacist</option>
                            {this.state.pharmacists.map(pharmacist =>
                                <option key={pharmacist.id}
                                        value={pharmacist.id}>{pharmacist.firstName} {pharmacist.lastName}</option>
                            )}

                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && !this.state.boolPharmacist && this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose pharmacy" as={"select"} value={this.state.pharmacy.id}
                                      onChange={(e)=>{this.handleOptionSelected(e)}}>
                            <option disabled={true} selected="selected">Choose pharmacy</option>
                            {this.state.pharmacy.map(p =>
                                <option name="users" key={p.id}
                                        value={p.id}>{p.name}</option>
                            )}
                        </Form.Control>
                    }
                        {this.state.submitted && this.state.errors.users.length > 0 &&  <span className="text-danger">{this.state.errors.users}</span>}
                    </Form>
                    <label style={{'marginLeft': '1rem', marginTop: '0.5rem'}}> Please write your complaint:</label>
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

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Complaint sent successfully!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        <div className="row" >
                            <div className="col-md-12">
                                <div className="card">
                                    <div className="card-body" style={{padding : '1rem'}}>
                                        <p className="card-text">
                                            Thank you for contacting us! We will respond as soon as possible
                                        </p>
                                    </div>
                                </div>
                                <br/><br/>
                            </div>
                        </div>
                    </Modal.Body>
                </Modal>

            </PatientLayout>
        );
    }
}
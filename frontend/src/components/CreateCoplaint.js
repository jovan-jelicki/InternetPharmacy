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
            boolPharmacy:false
        }
    }

    async componentDidMount() {
        await axios.post("http://localhost:8080/api/appointment/getAllFinishedByPatientAndExaminer",{
            patientId: 0,
            type:'dermatologist' //dermatolog
        }).then(res => {
            this.setState({
                dermatologists : res.data
            });
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

    render() {
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
                        <Form.Control placeholder="Choose dermatologist" as={"select"} value={this.state.dermatologists.id}
                                      onChange={this.handleAdminSelected}>
                            <option disabled={true} selected="selected">Choose dermatologist</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.id}
                                        value={dermatologist.id}>{dermatologist.firstName} {dermatologist.lastName}</option>
                            )}
                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && this.state.boolPharmacist && !this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose pharamacist" as={"select"} value={this.state.dermatologists.id}
                                      onChange={this.handleAdminSelected}>
                            <option disabled={true} selected="selected">Choose pharamacist</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.id}
                                        value={dermatologist.id}>{dermatologist.firstName} {dermatologist.lastName}</option>
                            )}
                        </Form.Control>
                    }

                    {
                        !this.state.boolDermatologist && !this.state.boolPharmacist && this.state.boolPharmacy &&
                        <Form.Control placeholder="Choose pharmacy" as={"select"} value={this.state.dermatologists.id}
                                      onChange={this.handleAdminSelected}>
                            <option disabled={true} selected="selected">Choose pharmacy</option>
                            {this.state.dermatologists.map(dermatologist =>
                                <option key={dermatologist.id}
                                        value={dermatologist.id}>{dermatologist.firstName} {dermatologist.lastName}</option>
                            )}
                        </Form.Control>
                    }
                    </Form>
                    <label style={{'marginLeft': '1rem', marginTop: '0.5rem'}}> Pease write your complaint:</label>
                        <div className="row" style={{marginTop: '1rem', marginLeft: '2rem'}}>
                                <div className="col-sm-6 mb-2">
                                    <Form.Control as="textarea" rows={6}/>
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
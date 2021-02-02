import React from 'react'
import {Button, Card, Col, Form, Modal, Row} from "react-bootstrap";
import axios from 'axios';
import MedicationSearch from "./MedicationSearch";
import MedicationSpecification from "./MedicationSpecification";
import MedicationPharmacy from "./MedicationPharmacy";

export default class MedicationListing extends React.Component {
    constructor() {
        super();
        this.state = {
            medications : []
        }
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
    }

    async componentDidMount() {
        await axios
        .get('http://localhost:8080/api/medications')
        .then((res) => {
            this.setState({
                medications : res.data
            })
        })
        this.medicationsBackup = [...this.state.medications]
    }

    cancel() {
        console.log(this.medicationsBackup)
        this.setState({
            medications : this.medicationsBackup
        })
    }

    search({name}) {
        axios
            .post('http://localhost:8080/api/medications/search', {
                'name' : name,

            })
            .then((res) => {
                this.setState({
                    medications : res.data
                })
            })

    }

    render() {
        const medications = this.state.medications.map((medication, index) => {
            return (
                <Col xs={4} key={index}>
                <Card bg={'dark'} key={index} text={'white'} style={{ width: '30rem', height: '30rem' }}  className="mb-2">
                    <Card.Body>
                    <Card.Title>{medication.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{medication.type}</Card.Subtitle>
                        <Card.Text>
                            <Button variant="link"  onClick={this.handleModal} >Check medication specification</Button>
                            <hr style={{'background-color' : 'gray'}}/>
                            <MedicationPharmacy medication={medication}></MedicationPharmacy>

                        </Card.Text>
                    </Card.Body>

                    <Modal show={this.state.showModal} onHide={this.handleModal}>
                        <Modal.Header closeButton>
                            <Modal.Title>Medication specification</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <MedicationSpecification medication={medication}/>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="primary" onClick={this.handleModal}>
                                Close
                            </Button>
                        </Modal.Footer>
                    </Modal>


                </Card>
                </Col> )
        })

        return (
            <div>
                <Row>
                <h2 className={'mt-5 ml-3'} id="medications">Medications</h2>
                </Row>
                <MedicationSearch search={this.search} cancel={this.cancel}/>
                <Row className={'mt-4'}>
                    {medications}
                </Row>
            </div>
            
        )
    }
    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal,
        });
    }
}

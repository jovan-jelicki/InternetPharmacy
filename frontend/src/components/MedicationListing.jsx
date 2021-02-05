import React from 'react'
import {Alert, Button, Card, Col, Form, Modal, Row} from "react-bootstrap";
import axios from 'axios';
import MedicationSearch from "./MedicationSearch";
import MedicationSpecification from "./MedicationSpecification";
import MedicationPharmacy from "./MedicationPharmacy";
import MedicationFilter from "./MedicationFilter";

export default class MedicationListing extends React.Component {
    constructor() {
        super();
        this.state = {
            medications : [],
            selectedOption:'',
            modalMedicaiton:[]
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
        //console.log(this.medicationsBackup)
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
    onTypeChange=({selectedOption}) => {
        if(selectedOption=="all"){
            this.cancel()
        }else {

            this.state.medications=this.medicationsBackup;
            let filteredData = this.state.medications.filter(column => {

                return column.type.toLowerCase().indexOf(selectedOption.toLowerCase()) !== -1;
            });

            this.setState({
                medications: filteredData
            });
        }
    }


    render() {
        console.log(this.state.medications)

        const medications = this.state.medications.map((medication, index) => {
            return (
                <Col xs={6} key={index}>
                <Card bg={'dark'} key={index} text={'white'} style={{ width: '35rem', height: '30rem' }}  className="mb-2">
                    <Card.Body>
                    <Card.Title>{medication.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{medication.type}</Card.Subtitle>
                        <Card.Text>
                            <Button variant="link" onClick={() => this.handleModal(medication)} >Check medication specification</Button>
                            <hr style={{'background-color' : 'gray'}}/>
                            <MedicationPharmacy medication={medication}></MedicationPharmacy>
                        </Card.Text>
                    </Card.Body>
                </Card>
                </Col> )
        })

        return (
            <div>
                <Row>
                <h2 className={'mt-5 ml-3'} id="medications">Medications</h2>
                </Row>
                <MedicationSearch search={this.search} cancel={this.cancel}/>
                <MedicationFilter onTypeChange={this.onTypeChange}/>
                {this.state.medications.length != 0 ?
                    <Row className={'mt-4'}>
                            {medications}
                    </Row>
                    :
                    <Alert variant='dark'  show={true}  style={({textAlignVertical: "center", textAlign: "center", marginLeft:'5rem',marginRight:'5rem', backgroundColor:'darkgray'})}>
                    No records found. Try again.
                    </Alert>
                }

                <Modal show={this.state.showModal} onHide={this.closeModal}  >
                    <Modal.Header closeButton style={{'background':'gray'}}>
                        <Modal.Title>Medication specification</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'gray'}}>
                        <MedicationSpecification medication={this.state.modalMedication}/>
                    </Modal.Body>
                    <Modal.Footer style={{'background':'gray'}}>
                        <Button variant="primary" onClick={this.closeModal}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

    handleModal = (medication) => {
        this.setState({
            showModal : !this.state.showModal,
            modalMedication: medication
        });
        console.log(this.state.modalMedication)
    }

    closeModal=()=>{
        this.setState({
            showModal : !this.state.showModal
        });
    }
}

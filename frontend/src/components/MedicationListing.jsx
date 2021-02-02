import React from 'react'
import {Card, Col, Row} from "react-bootstrap";
import axios from 'axios';
import MedicationSearch from "./MedicationSearch";

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
        console.log(name)
        axios
            .post('http://localhost:8080/api/medications/search', {
                'name' : name,

            })
            .then((res) => {
                console.log("NASAO JE")
                this.setState({
                    medications : res.data
                })
                console.log("Printaj")
                console.log(this.state.medications)
            })
            .catch((res) => {
                    console.log("PUKOOOO");
                }
            )
    }

    render() {
        const medications = this.state.medications.map((medication, index) => {
            let ingredients = ''
            medication.ingredient.forEach(i => ingredients += ' ' + i.name)
            return (
                <Col xs={4} key={index}>
                <Card bg={'dark'} key={index} text={'white'} style={{ width: '25rem', height: '20rem' }}  className="mb-2">
                    <Card.Body>
                    <Card.Title>{medication.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{medication.manufacturer}</Card.Subtitle>
                        <Card.Text>
                            {medication.note}
                            <hr style={{'background-color' : 'gray'}}/>
                                {ingredients}
                        </Card.Text>
                    </Card.Body>
                </Card>
                </Col>
            )
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
}
import React from 'react'
import {Card, Col, Row} from "react-bootstrap";

export default class MedicationListing extends React.Component {
    constructor() {
        super();
        this.state = {
            medications : []
        }
    }

    componentDidMount() {
        this.setState({
            medications : [
                {
                    name: "Xanax",
                    type: "antihistamine",
                    dose: 2,
                    loyaltyPoints: 3,
                    medicationShape: "pill",
                    manufacturer: "ABC",
                    medicationIssue: "withPrescription",
                    note: "take when hungry",
                    quantity : 10,
                    price : 400.00,
                    grade : 4,
                    ingredient: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
    
                    ],
                    sideEffect: [
                        {
                            name: "nausea"
                        },
                        {
                            name: "blindness"
                        }
                    ],
                    alternatives: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
                    ]
                },
                {
                    name: "Linex",
                    type: "antihistamine",
                    dose: 2,
                    grade : 4,
                    loyaltyPoints: 3,
                    medicationShape: "pill",
                    manufacturer: "ABC",
                    quantity : 10,
                    price : 1300,
                    medicationIssue: "withPrescription",
                    note: "take when hungry",
                    ingredient: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
    
                    ],
                    sideEffect: [
                        {
                            name: "nausea"
                        },
                        {
                            name: "blindness"
                        }
                    ],
                    alternatives: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
                    ]
                }
            ]
        })
    }

    render() {
        const medications = this.state.medications.map((medication, index) => {
            let ingredients = ''
            medication.ingredient.forEach(i => ingredients += i.name)
            return (
                <Col xs={3} key={index}>
                <Card 
                    bg={'dark'}
                    key={index}
                    text={'white'}
                    style={{ width: '18rem', height: '18rem' }}
                    className="mb-2"
                >
                    <Card.Body>
                    <Card.Title>{medication.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{medication.manufacturer}</Card.Subtitle>
                        <Card.Text>
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
                <Row className={'mt-4'}>
                    {medications}
                </Row>
            </div>
            
        )
    }
}
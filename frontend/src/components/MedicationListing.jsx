import React from 'react'
import {Card, Col, Row} from "react-bootstrap";

export default class MedicationListing extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        const medications = this.props.medications.map((medication, index) => {
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
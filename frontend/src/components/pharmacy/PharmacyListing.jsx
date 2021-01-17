import React from 'react';
import {Card, Col, Row} from "react-bootstrap";

export default class PharmacyListing extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const pharmacies = this.props.pharmacies.map((pharmacy, index) => {
            return (
                <Col xs={3} >
                <Card 
                    bg={'dark'}
                    key={index}
                    text={'white'}
                    style={{ width: '18rem', height: '18rem' }}
                    className="mb-2"
                >
                    <Card.Body>
                    <Card.Title>{pharmacy.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{pharmacy.address}</Card.Subtitle>
                        <Card.Text>
                        {pharmacy.description}
                        </Card.Text>
                    </Card.Body>
                </Card>
                </Col>
            )
        })
        return (
            <div>
                <Row>
                <h2 className={'mt-5 ml-3'} id="pharmacies">Pharmacies</h2>
                </Row>
                <Row className={'mt-4'}>
                    {pharmacies}
                </Row>
            </div>
            
        )
    }

}
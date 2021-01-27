import React from 'react'
import {Accordion, Card, Col, ListGroup, Row} from "react-bootstrap";

export default class ScheduleCounsel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const pharmacies = this.props.data.map((pharmacy, index) => {
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country
            const pharmacists = this.props.pharmacists.filter(p => p.workingHours.pharmacy.id === pharmacy.id)
                .map((pharmacist, index) => {
                return(
                    <ListGroup.Item key={index}>{pharmacist.firstName + ' ' + pharmacist.lastName}</ListGroup.Item>
                )
            })
            return (
                <Col>
                <Accordion defaultActiveKey="1" className={'mt-3 mb-3'}>
                    <Card bg={'dark'} text={'white'}>
                        <Accordion.Toggle as={Card.Header} eventKey="0">
                            <Row className={'pl-3'}><b>{pharmacy.name}</b></Row>
                            <Row className={'pl-3'}> {address} </Row>
                        </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                            <Card text={'dark'}>
                            <Card.Body>
                                <ListGroup>
                                    {pharmacists}
                                </ListGroup>
                            </Card.Body>
                            </Card>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
                </Col>
            )
        })
        return (
            <Row className={'mt-4'}>
                {pharmacies}
            </Row>
        )
    }
}

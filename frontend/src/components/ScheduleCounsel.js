import React from 'react'
import {Accordion, Card, Col, ListGroup, Row} from "react-bootstrap";

export default class ScheduleCounsel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const pharmacies = this.props.data.map((pharmacy, index) => {
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country
            return (
                <Col>
                <Accordion defaultActiveKey="1" className={'mt-3 mb-3'}>
                    <Card bg={'dark'} text={'white'}>
                        <Accordion.Toggle as={Card.Header} eventKey="0">
                            <Row><b>{pharmacy.name}</b></Row>
                            <Row> {address} </Row>
                        </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                            <Card text={'dark'}>
                            <Card.Body>
                                <ListGroup>
                                    <ListGroup.Item>Cras justo odio</ListGroup.Item>
                                    <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                                    <ListGroup.Item>Morbi leo risus</ListGroup.Item>
                                    <ListGroup.Item>Porta ac consectetur ac</ListGroup.Item>
                                    <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
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

import React from 'react'
import {Accordion, Card, Col, ListGroup, Row, Button} from "react-bootstrap";

export default class ScheduleCounsel extends React.Component {
    constructor(props) {
        super(props);
        this.schedule = this.schedule.bind(this)
    }

    schedule(pharmacyId, pharmacistId) {
        this.props.schedule(pharmacyId, pharmacistId);
    }


    render() {
        const pharmacies = this.props.data.map((pharmacy, index) => {

            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country
            const pharmacists = this.props.pharmacists.filter(p => p.pharmacyDTO.id === pharmacy.id)
                .map((pharmacist, index) => {
                let pharmacistPlain = pharmacist.pharmacistPlainDTO
                return(
                    <ListGroup.Item key={index}>
                        <Row>
                            <Col xs={4}>{pharmacistPlain.firstName + ' ' + pharmacistPlain.lastName}</Col>
                            <Col xs={{span : 2, offset : 5}}>
                                <Button variant={'success'} oClick={(e) => this.schedule(pharmacy.id, pharmacistPlain.id)}>Schedule</Button>
                            </Col>
                        </Row>
                    </ListGroup.Item>
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

import React from "react";
import {Button, Card, Col, Row} from "react-bootstrap";

class AllergyPatientListing extends React.Component {
    constructor(props) {
        super(props);
        this.removeAllergy = this.removeAllergy.bind(this);
    }

    removeAllergy(allergy) {
        this.props.removeAllergy(allergy)
    }

    render() {
        const allergies = this.props.allergies.map((allergy, index) => {
            return (
                <Col xs={3} >
                    <Card bg='primary' key={index} text='white' className="m-2" style={{ height: '5rem' }}>
                        <Card.Body>
                            <Card.Text>
                                <Button variant="primary" className="mr-3"
                                        onClick={this.removeAllergy.bind(this, allergy)}>X</Button>
                                {allergy}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            )
        })

        return (
            <Row>
                {allergies}
            </Row>
        )
    }
}

export default AllergyPatientListing;
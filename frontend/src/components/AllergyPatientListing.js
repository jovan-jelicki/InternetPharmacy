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
                    <div className="m-2 bg-primary p-2" style={{ height: '3rem' }}>
                        {this.props.edit && <Button variant="primary" className="mr-3 p-0" style={{width: '1rem'}}
                                 onClick={this.removeAllergy.bind(this, allergy)}>X</Button>}
                        {allergy}
                    </div>
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
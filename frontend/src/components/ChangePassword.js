import React from "react";
import {Col, Container, FormControl, Row} from "react-bootstrap";

class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Container>
                <h2 className="pt-4 pb-3">Password</h2>
                <Row className="m-2">
                    <FormControl type="password" placeholder="Password" />
                </Row>
                <Row className="m-2">
                    <FormControl type="password" placeholder="New Password" />
                </Row>
                <Row className="m-2">
                    <FormControl type="password" placeholder="Repeat new Password" />
                </Row>
            </Container>
        )
    }
}

export default ChangePassword;
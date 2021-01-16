import React from "react";
import {Container, Row, Col, Nav, Button} from "react-bootstrap";
import UserInfo from "../components/UserInfo";
import ChangePassword from "../components/ChangePassword";

//TODO Za sada namerno postoje dve iste stranice za profil dermatologa i farmaceuta, u toku rada uvideti da li je to zaista potrebno
export default class PharmacistProfilePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'firstName': '',
            'lastName': '',
            'email': '',
            'address': '',
            'town': '',
            'country': '',
            'phoneNumber': '',
            'allergies': [],
            'editMode': false
        }
    }

    componentDidMount() {
        this.setState({
            'firstName': 'ILIJA',
            'lastName': 'Brdar',
            'email': 'ilija_brdar@yahoo.com',
            'address': 'Vuka Karadzica 18',
            'town': 'Sirig',
            'country': 'Serbia',
            'phoneNumber': '+381604648117',
            'allergies': ['a1', 'a2', 'a3', 'a4', 'a5', 'a6'],
            'editMode': false
        })
        this.user = {
            'firstName': 'ILIJA',
            'lastName': 'Brdar',
            'email': 'ilija_brdar@yahoo.com',
            'address': 'Vuka Karadzica 18',
            'town': 'Sirig',
            'country': 'Serbia',
            'phoneNumber': '+381604648117',
        }
    }

    render() {
        return (
            <Container fluid>
                <Row className="pt-5">
                    <Col xs={2}>
                        <Nav defaultActiveKey="/home" className="flex-column">
                            {!this.state.editMode
                                ? <Button variant="primary" onClick={this.activateUpdateMode}>Edit</Button>
                                : <Button variant="outline-secondary" onClick={this.activateUpdateMode}>Cancel</Button>
                            }
                            {this.state.editMode && <Button variant="success mt-2">Save</Button>}
                        </Nav>
                    </Col>
                    <Col>
                        <UserInfo user={this.state} edit={this.state.editMode} onChange={this.handleInputChange}/>
                        {this.state.editMode && <ChangePassword/>}
                    </Col>
                </Row>

            </Container>
        );
    }
    activateUpdateMode = () => {
        this.resetData();
        this.setState({
            editMode : !this.state.editMode
        })
    }

    handleInputChange = (event) => {
        const target = event.target;
        this.setState({
            [target.name] : target.value,
        })
    }
    resetData = () => {
        this.setState({
            'firstName' : this.user.firstName,
            'lastName' : this.user.lastName,
            'address' : this.user.address,
            'town' : this.user.town,
            'country' : this.user.country,
            'phoneNumber' : this.user.phoneNumber
        })
    }
}
import React from 'react'
import { Container, Navbar, NavDropdown, Nav } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import { withRouter } from "react-router-dom";
import helper from './../helpers/AuthentificationService'

class PatientLayout extends React.Component {
    constructor(props) {
        super(props)
    }

    logout = () => {
        helper.logout()
        this.props.history.push({
            pathname: "/"
        });
    }

    render() {
        return (
            <Container fluid style={{'background-color' : '#AEB6BF'}}>
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Navbar.Brand as={NavLink} to="/patient-home">WebPharm</Navbar.Brand>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                        <NavDropdown title="Pharmacies" id="collasible-nav-dropdow">
                                <NavDropdown.Item as={NavLink} to="/patient-home">All Pharmacies</NavDropdown.Item>
                                <NavDropdown.Item as={NavLink} to="/patient-home-promo">Promotions</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Schedule" id="collasible-nav-dropdow">
                                <NavDropdown.Item as={NavLink} to='/patient-counsel-schedule'>Pharmacist Counseling</NavDropdown.Item>
                                <NavDropdown.Item as={NavLink} to='/scheduled-appointments'>Upcoming Appointments</NavDropdown.Item>
                                <NavDropdown.Item as={NavLink} to='/scheduled-appointments-history'>Appointment History</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Reservations" id="collasible-nav-dropdow">
                                <NavDropdown.Item as={NavLink} to='/patient-medications'>Medications</NavDropdown.Item>
                                <NavDropdown.Item as={NavLink} to='/patient-reservations'>All Reservations</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link as={NavLink} to='/patient-eprescription'>ePrescriptions</Nav.Link>
                            <Nav.Link as={NavLink} to='/qrCode'>Upload QR code</Nav.Link>
                        </Nav>
                        <Nav>
                            <NavDropdown title="Account" id="collasible-nav-dropdow">
                                <NavDropdown.Item as={NavLink} to='/patient-profile'>My Account</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Penalties</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link as={NavLink} to='/grading'>Grade</Nav.Link>
                            <Nav.Link as={NavLink} to='/complaint'>File a Complaint</Nav.Link>
                            <Nav.Link onClick={this.logout}>Log out</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                {this.props.children}
            </Container>
        )
    }
}

export default withRouter(PatientLayout)
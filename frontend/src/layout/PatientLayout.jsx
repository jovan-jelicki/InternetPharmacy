import React from 'react'
import { Container, Navbar, NavDropdown, Nav } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

class PatientLayout extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <Container fluid style={{'background-color' : '#AEB6BF'}}>
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Navbar.Brand as={NavLink} to="/">WebPharm</Navbar.Brand>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                        <NavDropdown title="Pharmacies" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="javascript:;">All Pharmacies</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Promotions</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Schedule" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="javascript:;">Dermatologist Examination</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Pharmacist Counceling</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">All Schedules</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Medications" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="javascript:;">Reservations</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Pick Up</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="ePrescription" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="javascript:;">Prescriptions History</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Medication History</NavDropdown.Item>
                            </NavDropdown>    
                        </Nav>
                        <Nav>
                            <NavDropdown title="Account" id="collasible-nav-dropdow">
                                <NavDropdown.Item as={NavLink} to='/patient-profile'>My Account</NavDropdown.Item>
                                <NavDropdown.Item href="javascript:;">Penalties</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link as={NavLink} to='javascript:;'>File a Complaint</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                {this.props.children}
            </Container>
        )
    }
}

export default PatientLayout
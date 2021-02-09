import React from 'react'
import { Container, Navbar, NavDropdown, Nav } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

class PharmacyAdminLayout extends React.Component {
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
                            <NavDropdown title="Pharmacy" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="/pharmacy">My pharmacy</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <NavDropdown title="Account" id="collasible-nav-dropdow">
                                <NavDropdown.Item href="/pharmacy-admin-profile">My Account</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                {this.props.children}
            </Container>
        )
    }
}

export default PharmacyAdminLayout
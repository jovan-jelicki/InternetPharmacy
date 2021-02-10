import React from 'react'
import { Container, Navbar, NavDropdown, Nav } from 'react-bootstrap';
import Login from "../components/Login";
import Registration from "../pages/Registration";

export default class UnregisteredLayout extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Container fluid style={{'background-color' : '#AEB6BF'}}>
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                <Navbar.Brand href="/">WebPharm</Navbar.Brand>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="#pharmacies">Pharmacies</Nav.Link>
                        <Nav.Link href="#medications">Medications</Nav.Link>
                        <Nav.Link href="#registration">Registration</Nav.Link>
                    </Nav>
                    <Nav>
                    <Login/>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
            {this.props.children}
        </Container>
        )
    }
}
import React from 'react';
import {Button} from "react-bootstrap";
import {Modal} from "react-bootstrap";
import {Navbar} from "react-bootstrap";
import {Form} from "react-bootstrap";
import {FormControl} from "react-bootstrap";




export default class PharmacyEmployees extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            dermatologists: [],
            pharmacists: [],
            showModal : false,
            userType : "pharmacyAdmin"
        }
    }
// definise slobodne termine,pretražuje, kreira i uklanja farmaceute/dermatologe
    componentDidMount() {
        let dermatologists = [
            {
                firstName : "Mirko",
                lastName : "Jugovic",
                grade : 5,
            },
            {
                firstName : "Maja",
                lastName : "Jugovic",
                grade : 3,
            }
        ];
        let pharmacists = [
            {
                firstName : "Jelena",
                lastName : "Matic",
                grade : 3.25,
            },
            {
                firstName : "Maja",
                lastName : "Berovic",
                grade : 4.1,
            }
        ];

        this.setState({
            dermatologists : dermatologists,
            pharmacists : pharmacists
        });
    }

    render() {
        return (
           <div>
               <br/><br/>
               <h1>Dermatolozi</h1>
               
               <Button variant="success">Dodaj dermatologa</Button>

               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" />
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" />
                           <Button variant="outline-success">Search</Button>
                       </Form>
                   </Navbar.Collapse>
               </Navbar>

               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">Ime</th>
                       <th scope="col">Prezime</th>
                       <th scope="col">Ocena</th>
                   </tr>
                   </thead>
                   <tbody>
               {this.state.dermatologists.map((dermatologist, index) => (
                   <tr>
                       <th scope="row">{index+1}</th>
                       <td>{dermatologist.firstName}</td>
                       <td>{dermatologist.lastName}</td>
                       <td>{dermatologist.grade}</td>
                       <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="primary" onClick={this.handleModal}>
                                Zakazi pregled
                           </Button>
                       </td >
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="warning" onClick={this.handleModal}>
                               Definisi slobodne termine
                           </Button>
                       </td>
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="danger" onClick={this.handleModal}>
                               Izbrisi dermatologa
                           </Button>
                       </td>
                   </tr>
               ))}
                   </tbody>
               </table>


               <br/><br/>
               <h1>Farmaceuti</h1>
               <Button variant="success">Kreiraj farmaceuta</Button>
               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" />
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" />
                           <Button variant="outline-success">Search</Button>
                       </Form>
                   </Navbar.Collapse>
               </Navbar>
               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">Ime</th>
                       <th scope="col">Prezime</th>
                       <th scope="col">Ocena</th>
                   </tr>
                   </thead>
                   <tbody>
                   {this.state.pharmacists.map((dermatologist, index) => (
                       <tr>
                           <th scope="row">{index+1}</th>
                           <td>{dermatologist.firstName}</td>
                           <td>{dermatologist.lastName}</td>
                           <td>{dermatologist.grade}</td>
                           <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="primary" onClick={this.handleModal}>
                                   Zakazi savetovanje
                               </Button>
                           </td >
                           <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="warning" onClick={this.handleModal}>
                                   Definisi slobodne termine
                               </Button>
                           </td>
                           <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="danger" onClick={this.handleModal}>
                                   Izbrisi farmaceuta
                               </Button>
                           </td>
                       </tr>
                   ))}
                   </tbody>
               </table>


               <Modal show={this.state.showModal} onHide={this.handleModal}>
                   <Modal.Header closeButton>
                       <Modal.Title>Modal heading</Modal.Title>
                   </Modal.Header>
                   <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
                   <Modal.Footer>
                       <Button variant="secondary" onClick={this.handleModal}>
                           Close
                       </Button>
                       <Button variant="primary" onClick={this.handleModal}>
                           Save Changes
                       </Button>
                   </Modal.Footer>
               </Modal>
           </div>
        );
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }
}
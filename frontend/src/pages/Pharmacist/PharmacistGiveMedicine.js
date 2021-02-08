import React from "react";
import {Button, Col, FormControl, FormGroup, Row, Table} from "react-bootstrap";
import axios from "axios";

export default class PharmacistGiveMedicine extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medicineCode : "",
            reservation : "samo cisto da probam",
            reservationFound : false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }

    render() {
        return (
            <div>
                <br/>
                <Row>
                    <label style={{fontSize : 20, marginLeft : 55, marginTop : 10}}> Insert medicine code: </label>
                    <FormControl className="mt-2 mb-2" style={{width : 200, marginLeft : 20, marginBottom : 50}} value={this.state.medicineCode} placeholder={"Insert here..."} onChange={this.handleInputChange} />
                    <Button onClick={this.getReservation} style={{height : 40, margin : 8}}  type="button" className="btn btn-primary"> Search </Button>
                </Row>
                <hr className="mt-2 mb-3"/>
                {this.state.reservationFound && this.showReservation()}
            </div>
        )
    }

    showReservation = () => {
        return (
            <div>
                <Table style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} striped hover>
                    <tbody>
                    <tr>
                        <th>Patient full name</th>
                        <th>Medication</th>
                        <th>Quantity</th>
                    </tr>
                    <tr>
                        <td>{this.state.reservation.firstName}</td>
                        <td>{this.state.reservation.medicationQuantity.medication.name}</td>
                        <td>{this.state.reservation.medicationQuantity.quantity}</td>
                        <td> <Button onClick={this.giveMedicine}> Give medication </Button> </td>
                    </tr>
                    </tbody>
                </Table>
            </div>
        )
    }

    giveMedicine = () => {
        axios
            .put( process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/medicationReservation/giveMedicine/' + this.state.reservation.id , {},
                {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
            .then(res => {
                    this.setState({
                        reservationFound : false,
                        reservation : ""
                    });
                    alert("Successful medication approval!")
                }
            )
            .catch(e => {
                    alert("Reservation is not valid!")
                }
            );
    }


    handleInputChange = (event) => {
        this.setState({
            medicineCode : event.target.value
        })
    }

    getReservation = () => {
        axios
            .post( process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/medicationReservation/getMedicationReservation', {
                pharmacistId : this.state.user.id,
                medicationId : this.state.medicineCode
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
               let reservation = res.data;
               if(res.data.id !== undefined) {
                   this.setState({
                       reservation: reservation,
                       reservationFound: true
                   })
               }
            })
            .catch(e => {
                    this.setState({
                        reservationFound: false
                    });
                    alert("Reservation is not valid!")
                }
            );
    }
}
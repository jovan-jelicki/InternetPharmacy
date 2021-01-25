import React from "react";
import {Button, Col, FormControl, FormGroup, Row} from "react-bootstrap";

export default class PharmacistGiveMedicine extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medicineCode : "",
            reservation : "samo cisto da probam",
            reservationFound : true
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

            </div>
        )
    }

    handleInputChange = (event) => {
        this.setState({
            medicineCode : event.target.value
        })
    }

    getReservation = () => {

    }
}
import React from "react";
import {Col, Container,  Row, Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";


export default class VacationRequest extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate : "",
            endDate : "",
            pharmacy : "",
            vacationNote : ""
        }
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }
    render() {
        let pharmacies = [{name : "hemofarm"}, {name : "jankovic"} ];
        const  PharmaciesTag = pharmacies.map((pharmacy, key) =>
            <option value={pharmacy.name}> {pharmacy.name} </option>
        );
        return (
            <Container>
                <br/>
                <h2> Create request for vacation or absence</h2>
                <br/>
                <Row>
                    <Col xs={2}> <p> Choose period: </p> </Col>
                    <Col xs={-4}> <DatePicker  selected={this.state.startDate} minDate={new Date()} onChange={date => this.setState({startDate : date})} /> </Col>
                    <Col xs={-4}> <DatePicker  selected={this.state.endDate} minDate={new Date()} onChange={date => this.setState({endDate : date})} /> </Col>
                </Row>
                <Row>
                    <Col xs={2}> <p> Choose pharmacy: </p> </Col>
                    <Col xs={-4}>
                            <select value={this.state.pharmacy} onChange={value => this.setState({ pharmacy : value.target.value})}>
                                {PharmaciesTag}
                            </select>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <p> Reason :</p>
                        <textarea className="form-control"  rows="5" cols="30"
                                  placeholder="Type reason..." id="comment" value={this.state.vacationNote} onChange={text => this.setState({vacationNote : text.target.value})}/>
                    </Col>
                </Row>
                <Button onClick={this.sendData}> Submit </Button>
            </Container>
        )
    }


    sendData = () => {
        axios
            .post( process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/vacationRequest', {
                'period' : {
                    periodStart: this.state.startDate,
                    periodEnd: this.state.endDate
                },
                'employeeType' : 0, //props.role
                'employeeId' : 1, //props.Id
                'vacationNote' : this.state.vacationNote,
                'vacationRequestStatus' : 0
            })
            .then(res => {
                alert('You have successfully submitted the request!');
            })
            .catch(e => alert('Something\'s gona wrong!'));

    }
}
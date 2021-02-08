import React from "react";
import {Col, Container,  Row, Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";


export default class VacationRequest extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate : new Date(),
            endDate : new Date(),
            pharmacy : "",
            pharmacies : [],
            vacationNote : "",
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        if(this.state.user.type == "ROLE_pharmacist"){
            axios
                .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/pharmacist/getPharmacy/' + this.state.user.id, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => {
                    this.setState({
                        pharmacies : [res.data],
                        pharmacy : res.data
                    })
                })
                .catch(res => alert("Wrong!"));
        }else if (this.state.user.type == "ROLE_dermatologist"){
            axios
                .get(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/dermatologists/getPharmacy/' + this.state.user.id, {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
                .then(res => {
                    this.setState({
                        pharmacies : res.data
                    })
                })
                .catch(res => alert("Wrong!"));
        }
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }
    render() {
        const  PharmaciesTag = this.state.pharmacies.map((pharmacy, key) =>
            <option value={pharmacy.name}> {pharmacy.name} </option>
        );
        return (
            <Container>
                <br/>
                <h2> Create request for vacation or absence</h2>
                <br/>
                <Row>
                    <Col xs={2}> <p> Choose period: </p> </Col>
                    <Col xs={-4}> <DatePicker selected={this.state.startDate} minDate={new Date()} onChange={date => this.setState({startDate : date})} /> </Col>
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
        let periodStart = this.state.startDate;
        let periodEnd = this.state.endDate;

        //let momentDate = moment(date);
        let day = periodStart.getDate();
        let month = parseInt(periodStart.getMonth())+1;
        if (month < 10)
            month = "0" + month;
        if (parseInt(day)<10)
            day = "0"+day;
        let hours = parseInt(periodStart.getHours());
        if(hours < 10)
            hours = "0" + hours;
        let minutes = parseInt(periodStart.getMinutes());
        if(minutes < 10)
            minutes = "0" + minutes;

        let fullYearStart = periodStart.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":00";
        day = periodEnd.getDate();
        month = parseInt(periodEnd.getMonth())+1;
        if (month < 10)
            month = "0" + month;
        if (parseInt(day)<10)
            day = "0"+day;
         hours = parseInt(periodEnd.getHours());
        if(hours < 10)
            hours = "0" + hours;
         minutes = parseInt(periodEnd.getMinutes());
        if(minutes < 10)
            minutes = "0" + minutes;
        let fullYearEnd = periodEnd.getFullYear() + "-" + month + "-" + day  + " " + hours + ":" + minutes + ":00";

        axios
            .post( process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/vacationRequest/saveDto', {
                'period' : {
                    periodStart: fullYearStart,
                    periodEnd: fullYearEnd
                },
                'pharmacy' : this.state.pharmacy,
                'employeeType' : this.state.user.type,
                'employeeId' : this.state.user.id,
                'vacationNote' : this.state.vacationNote,
                'vacationRequestStatus' : 0
            }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert('You have successfully submitted the request!');
            })
            .catch(e => alert('Something\'s gonna wrong!'));

    }
}
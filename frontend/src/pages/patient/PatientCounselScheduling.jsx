import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import DateTime from "../../components/DateTime";
import {Container, Row} from "react-bootstrap";
import ScheduleCounsel from "../../components/ScheduleCounsel";
import axios from "axios";

class PatientCounselScheduling extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            pharmacies : []
        }
        this.search = this.search.bind(this)
    }

    search(dateTime) {
        axios
            .post('http://localhost:8080/api/scheduling/search', {
                'timeSlot' : dateTime,
                'employeeType' : 'pharmacist'
            })
            .then(res => {
                this.setState({
                    pharmacies : res.data.map(p => p.workingHours.pharmacy)
                })
            });
    }

    render() {
        return (
            <PatientLayout>
                <Container fluid>
                    <Row className={'mt-3 mb-4'}>
                        <h2>Choose what date & time fits you the best</h2>
                    </Row>
                    <DateTime search={this.search}/>
                    <ScheduleCounsel data={this.state.pharmacies}/>
                </Container>
            </PatientLayout>
        )
    }
}

export default PatientCounselScheduling
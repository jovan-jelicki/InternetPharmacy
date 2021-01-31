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
            pharmacies : [],
            pharmacists : [],
            dateTime : ''
        }
        this.search = this.search.bind(this)
        this.schedule = this.schedule.bind(this)
    }

    search(dateTime) {
        axios
            .post('http://localhost:8080/api/scheduling/search', {
                'timeSlot' : dateTime,
                'employeeType' : 'pharmacist'
            })
            .then(res => {
                this.setState({
                    'pharmacies' : [...new Set(res.data.map(x => x.pharmacyDTO))],
                    'pharmacists' : res.data,
                    'dateTime' : dateTime
                })
            })
    }

    schedule(pharmacyId, pharmacistId) {
        axios
        .post('http://localhost:8080/api/appointment/counseling', {
            'examinerId' : pharmacistId,
            'type' : 'pharmacist',
            'active' : true,
            'appointmentStatus' : 'available',
            'pharmacy' : {
                'id' : pharmacyId
            },
            'patient' : {
                'id' : 0
            },
            'period' : {
                'periodStart' : this.state.dateTime
            }
        })
        .then(res => console.log(res));
    }

    render() {
        return (
            <PatientLayout>
                <Container fluid>
                    <Row className={'mt-3 mb-4'}>
                        <h2>Choose what date & time fits you the best</h2>
                    </Row>
                    <DateTime search={this.search}/>
                    <ScheduleCounsel data={this.state.pharmacies} pharmacists={this.state.pharmacists} schedule={this.schedule}/>
                </Container>
            </PatientLayout>
        )
    }
}

export default PatientCounselScheduling
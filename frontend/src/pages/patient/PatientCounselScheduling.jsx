import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import DateTime from "../../components/DateTime";
import {Container, Row} from "react-bootstrap";
import ScheduleCounsel from "../../components/ScheduleCounsel";
import axios from "axios";
import { withRouter } from "react-router-dom";
import HelperService from './../../helpers/HelperService'

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

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))
    }

    search(dateTime) {
        axios
            .post(HelperService.getPath('/api/scheduling/search'), {
                'timeSlot' : dateTime,
                'employeeType' : 'ROLE_pharmacist',
                'patientId' : this.aut.id
            }, {
                headers : {
                    'Content-Type' : 'application/json',
                    Authorization : 'Bearer ' + this.aut.jwtToken 
                }
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
        .post(HelperService.getPath('/api/appointment/counseling'), {
            'examinerId' : pharmacistId,
            'type' : 'ROLE_pharmacist',
            'active' : true,
            'appointmentStatus' : 'available',
            'pharmacy' : {
                'id' : pharmacyId
            },
            'patient' : {
                'id' : this.aut.id
            },
            'period' : {
                'periodStart' : this.state.dateTime
            }
        }, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.props.history.push('/scheduled-appointments')
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
                    <ScheduleCounsel data={this.state.pharmacies} pharmacists={this.state.pharmacists} schedule={this.schedule}/>
                </Container>
            </PatientLayout>
        )
    }
}

export default withRouter(PatientCounselScheduling)
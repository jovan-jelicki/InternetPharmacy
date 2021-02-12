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
        if (this.aut == null || this.aut.type != 'ROLE_patient') {
            let path = process.env.REACT_APP_BACKEND_ADDRESS ? 'https://isa-pharmacy-frontend.herokuapp.com/unauthorized'
                : 'http://localhost:3000/unauthorized';
            window.location.replace(path);
        }
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
                console.log(res.data)
                this.setState({
                    //'pharmacies' : [...new Set(res.data.map(x => x.pharmacyDTO))],
                    'pharmacies' : this.removeDuplicates([...res.data.map(x => x.pharmacyDTO)]),
                    'pharmacists' : res.data,
                    'dateTime' : dateTime
                })
            })
    }

    removeDuplicates = (arr) => {
        return arr.filter((v, i, a) => a.findIndex(t => t.id == v.id) === i)
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
            axios
            .put(HelperService.getPath('/api/email/send'), {
                'to': 'ilija_brdar@yahoo.com',   
                'subject':"Counseling scheduled!",
                'body':'You have successfully scheduled counseling at ' + this.state.dateTime,
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.aut.jwtToken
                }
            })
            .then(res => {
                this.props.history.push('/scheduled-appointments')
            });
        })
        .catch(e => alert('You have been blocked due to 3 penalties.'));
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
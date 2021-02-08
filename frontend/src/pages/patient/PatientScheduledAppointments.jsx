import React from 'react'
import axios from 'axios'
import { Row } from 'react-bootstrap'
import PatientLayout from '../../layout/PatientLayout'
import AppointmentListing from '../../components/AppointmentListing'

class PatientScheduledAppointments extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            counselings : [],
            examinations : []
        }
        this.cancel = this.cancel.bind(this)
        this.cancelExamination = this.cancelExamination.bind(this)
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        axios
        .get('http://localhost:8080/api/scheduling/counseling-upcoming/' + this.aut.id, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                counselings : res.data
            })
        })

        axios
        .get('http://localhost:8080/api/scheduling/examination-upcoming/' + this.aut.id, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            alert('ok')
            this.setState({
                examinations : res.data
            })
        })
    }

    cancel(id) {
        axios
        .put('http://localhost:8080/api/appointment/cancel-counseling/' + id, {}, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                counselings : [...this.state.counselings.filter(c => c.id != id)]
            })
            
        })
        .catch(e => alert('It is not allowed to cancel 24h prior to the appointment'))
    }

    cancelExamination(id) {
        axios
        .put('http://localhost:8080/api/appointment/cancel-examination/' + id, {}, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                examinations : [...this.state.examinations.filter(c => c.id != id)]
            })
            
        })
        .catch(e => alert('It is not allowed to cancel 24h prior to the appointment'))
    }
 
    render() {
        return (
            <PatientLayout>
                <Row className={'ml-2 mt-5 mb-4'}>
                    <h2>Pharmacist Counselings</h2>
                </Row>
                <Row className={'m-2'}>
                    <AppointmentListing appointments={this.state.counselings} cancel={this.cancel} view={false}/>
                </Row>
                <Row className={'ml-2 mt-5 mb-4'}>
                    <h2>Dermatologist Examinations</h2>
                </Row>
                <Row className={'m-2'}>
                    <AppointmentListing appointments={this.state.examinations} cancel={this.cancelExamination} view={false}/>
                </Row>
            </PatientLayout>
        )
    }

}

export default PatientScheduledAppointments
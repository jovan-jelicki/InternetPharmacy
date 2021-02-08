import React from 'react'
import axios from 'axios'
import { Row } from 'react-bootstrap'
import PatientLayout from '../../layout/PatientLayout'
import AppointmentListing from '../../components/AppointmentListing'

class PatientPreviousAppointments extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            counselings : [],
            examinations : []
        }
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        axios
        .get('http://localhost:8080/api/scheduling/counseling-previous/' + this.aut.id, {
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
        .get('http://localhost:8080/api/scheduling/examination-previous/' + this.aut.id, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        }) 
        .then(res => {
            this.setState({
                examinations : res.data
            })
        })
    }

    render() {
        return (
            <PatientLayout>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <h2>Pharmacist Counselings</h2>
                    </Row>
                    <Row className={'m-2'}>
                        <AppointmentListing appointments={this.state.counselings} view={true}/>
                    </Row>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <h2>Dermatologist Examinations</h2>
                    </Row>
                    <Row className={'m-2'}>
                        <AppointmentListing appointments={this.state.examinations} view={true}/>
                    </Row>
            </PatientLayout>
        )
    }
}

export default PatientPreviousAppointments
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
    }

    componentDidMount() {
        axios
        .get('http://localhost:8080/api/scheduling/counseling/0')
        .then(res => {
            console.log(res.data)
            this.setState({
                counselings : res.data
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
                    <AppointmentListing appointments={this.state.counselings}/>
                </Row>
            </PatientLayout>
        )
    }

}

export default PatientScheduledAppointments
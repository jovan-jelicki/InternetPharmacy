import React from 'react'
import axios from 'axios'
import { Row } from 'react-bootstrap'
import PatientLayout from '../../layout/PatientLayout'
import AppointmentListing from '../../components/AppointmentListing'
import AppointmentSorting from '../../components/AppointmentSorting'
import HelperService from '../../helpers/HelperService'

class PatientPreviousAppointments extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            counselings : [],
            examinations : [],
            strategy : 'asc'
        }
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        if (this.aut == null || this.aut.type != 'ROLE_patient') {
            let path = process.env.REACT_APP_BACKEND_ADDRESS ? 'https://isa-pharmacy-frontend.herokuapp.com/unauthorized'
                : 'http://localhost:3000/unauthorized';
            window.location.replace(path);
        }

        axios
        .get(HelperService.getPath('/api/scheduling/counseling-previous/' + this.aut.id), {
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
        .get(HelperService.getPath('/api/scheduling/examination-previous/' + this.aut.id), {
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

    counselingSort = (item) => {
        if(item == 'price') {
            this.setState({
                counselings : [...this.state.counselings.sort((a, b) => {
                    if(this.state.strategy == 'asc')
                        return a.cost - b.cost
                    else
                        return b.cost - a.cost
                })]
            })
        }
        else if(item == 'date') {
            this.setState({
                counselings : [...this.state.counselings.sort((a, b) => {
                    let da = new Date(a.period.periodStart), db = new Date(b.period.periodStart)
                    if(this.state.strategy == 'asc')
                        return da - db
                    else
                        return db - da
                })]
            })
        }
    }

    setStrategy = (s) => {
        this.setState({
            strategy : s
        })
    }

    render() {
        return (
            <PatientLayout>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <h2>Pharmacist Counselings</h2>
                    </Row>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <AppointmentSorting sorting={this.counselingSort} strategy={this.setStrategy}/>
                    </Row>
                    <Row className={'m-2'}>
                        <AppointmentListing appointments={this.state.counselings} view={true}/>
                    </Row>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <h2>Dermatologist Examinations</h2>
                    </Row>
                    <Row className={'ml-2 mt-5 mb-4'}>
                        <AppointmentSorting/>
                    </Row>
                    <Row className={'m-2'}>
                        <AppointmentListing appointments={this.state.examinations} view={true}/>
                    </Row>
            </PatientLayout>
        )
    }
}

export default PatientPreviousAppointments
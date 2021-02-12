import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import GradingSegment from '../../components/GradingSegment'
import { Row } from 'react-bootstrap'
import axios from 'axios'
import HelperService from './../../helpers/HelperService'

class PatientGradingPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            dermatologists : [],
            pharmacists : [],
            pharmacies : [],
            medications : []
        }
        this.grade = this.grade.bind(this)
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        if (this.aut == null || this.aut.type != 'ROLE_patient') {
            let path = process.env.REACT_APP_BACKEND_ADDRESS ? 'https://isa-pharmacy-frontend.herokuapp.com/unauthorized'
                : 'http://localhost:3000/unauthorized';
            window.location.replace(path);
        }

        axios
        .get(HelperService.getPath('/api/grades/dermatologists/' + this.aut.id), {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            console.log(res.data)
            this.setState({
                dermatologists : res.data
            })
        })

        axios
        .get(HelperService.getPath('/api/grades/pharmacist/' + this.aut.id), {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                pharmacists : res.data
            })
        })

        axios
        .get(HelperService.getPath('/api/grades/medication/' + this.aut.id), {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                medications : res.data
            })
        })

        axios
        .get(HelperService.getPath('/api/grades/pharmacy/' + this.aut.id), {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.setState({
                pharmacies : res.data
            })
        })
    }

    grade(entity) {
        let gradedId = null
        if(entity.type == 'pharmacist' || entity.type == 'dermatologist') 
            gradedId = entity.employeeId
        else
            gradedId = entity.gradedId

        axios
        .post(HelperService.getPath('/api/grades'), {
            'id' : entity.gradeId,
            'gradedId' : gradedId,
            'gradeType' : entity.type,
            'patient' : {
                'id' : this.aut.id
            },
            'grade' : entity.grade
        }, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            console.log('Success')
        })
    }

    render() {
        return (
            <PatientLayout>
                <Row className={'m-2'}>
                    <h2>Dermatologists</h2>
                </Row>
                <Row className={'m-2'}>
                    <GradingSegment data={this.state.dermatologists} grade={this.grade}/>
                </Row>
                <Row className={'m-2'}>
                    <h2>Pharmacists</h2>
                </Row>
                <Row className={'m-2'}>
                    <GradingSegment data={this.state.pharmacists} grade={this.grade}/>
                </Row>
                <Row className={'m-2'}>
                    <h2>Pharmacies</h2>
                </Row>
                <Row className={'m-2'}>
                    <GradingSegment data={this.state.pharmacies} grade={this.grade}/>
                </Row>
                <Row className={'m-2'}>
                    <h2>Medications</h2>
                </Row>
                <Row className={'m-2'}>
                    <GradingSegment data={this.state.medications} grade={this.grade}/>
                </Row>
            </PatientLayout>
        )
    }
}

export default PatientGradingPage
import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import GradingSegment from '../../components/GradingSegment'
import { Row } from 'react-bootstrap'
import axios from 'axios'

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
        axios
        .get('http://localhost:8080/api/grades/dermatologists/0')
        .then(res => {
            console.log(res.data)
            this.setState({
                dermatologists : res.data
            })
        })

        axios
        .get('http://localhost:8080/api/grades/pharmacist/0')
        .then(res => {
            this.setState({
                pharmacists : res.data
            })
        })

        axios
        .get('http://localhost:8080/api/grades/medication/0')
        .then(res => {
            this.setState({
                medications : res.data
            })
        })

        axios
        .get('http://localhost:8080/api/grades/pharmacy/0')
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
        .post('http://localhost:8080/api/grades', {
            'id' : entity.gradeId,
            'gradedId' : gradedId,
            'gradeType' : entity.type,
            'patient' : {
                'id' : 0                    //TODO POPRAVITI
            },
            'grade' : entity.grade
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
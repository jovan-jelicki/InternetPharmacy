import React, { Component } from 'react'
import PatientLayout from './../../layout/PatientLayout'
import MedicationListing from './../../components/MedicationListing'

export default class PatientMedicationPage extends Component {
    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        if (this.aut == null || this.aut.type != 'ROLE_patient') {
            let path = process.env.REACT_APP_BACKEND_ADDRESS ? 'https://isa-pharmacy-frontend.herokuapp.com/unauthorized'
                : 'http://localhost:3000/unauthorized';
            window.location.replace(path);
        }
    }

    render() {
        return (
            <PatientLayout>
                <MedicationListing/>
            </PatientLayout>
        )
    }
}

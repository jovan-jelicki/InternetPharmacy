import React, { Component } from 'react'
import PatientLayout from './../../layout/PatientLayout'
import MedicationListing from './../../components/MedicationListing'

export default class PatientMedicationPage extends Component {
    render() {
        return (
            <PatientLayout>
                <MedicationListing/>
            </PatientLayout>
        )
    }
}

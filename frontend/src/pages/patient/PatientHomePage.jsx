import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import PharmacyListing from '../../components/pharmacy/PharmacyListing'

class PatientHomePage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <PatientLayout>
                <PharmacyListing/>
            </PatientLayout>
        )
    }
}

export default PatientHomePage
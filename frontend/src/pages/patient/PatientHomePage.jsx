import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import PharmacyListing from '../../components/pharmacy/PharmacyListing'

class PatientHomePage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div>
            <PatientLayout>
                <PharmacyListing/>
            </PatientLayout>
            </div>
        )
    }
}

export default PatientHomePage
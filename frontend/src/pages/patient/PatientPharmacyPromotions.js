import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import PharmacyPromotions from '../../components/pharmacy/PharmacyPromotions'

class PatientPharmacyPromotions extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <PatientLayout>
                <PharmacyPromotions/>
            </PatientLayout>
        )
    }
}

export default PatientPharmacyPromotions
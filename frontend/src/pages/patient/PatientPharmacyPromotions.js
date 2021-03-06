import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import PharmacyPromotions from '../../components/pharmacy/PharmacyPromotions'

class PatientPharmacyPromotions extends React.Component {
    constructor(props) {
        super(props)
    }

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
                <PharmacyPromotions/>
            </PatientLayout>
        )
    }
}

export default PatientPharmacyPromotions
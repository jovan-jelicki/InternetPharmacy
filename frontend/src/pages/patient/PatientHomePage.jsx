import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import PharmacyListing from '../../components/pharmacy/PharmacyListing'

class PatientHomePage extends React.Component {
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
            <div>
            <PatientLayout>
                <PharmacyListing/>
            </PatientLayout>
            </div>
        )
    }
}

export default PatientHomePage
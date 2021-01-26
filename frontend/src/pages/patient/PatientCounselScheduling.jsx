import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import DateTime from "../../components/DateTime";

class PatientCounselScheduling extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <PatientLayout>
                <DateTime/>
            </PatientLayout>
        )
    }
}

export default PatientCounselScheduling
import React from 'react'


class MedicationSpecification  extends React.Component {
    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        return (
            <div>
                <h1> Evo ga lek </h1>
                <p>{this.props.medication.name}</p>
            </div>
        );
    }
}

export default MedicationSpecification
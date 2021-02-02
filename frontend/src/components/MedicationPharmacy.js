import React from "react";
import {Button, Col, FormControl, Row} from "react-bootstrap";
import axios from "axios";

class MedicationPharmacy extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            pharmacy : [],
        }
    }

    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/pharmacy/getPharmacyByMedication/'+this.props.medication.id)
            .then((res) => {
                this.setState({
                    pharmacy : res.data
                })
                console.log(this.state.pharmacy);
            })
    }

    render() {
        return (
            <div>
                <h4> Apoteke </h4>
                <p >{this.props.medication.id}</p>
            </div>
        )
    }
}

export default MedicationPharmacy
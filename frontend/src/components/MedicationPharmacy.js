import React from "react";
import {Button, Col, FormControl, Row} from "react-bootstrap";
import axios from "axios";
import MedicationSearch from "./MedicationSearch";

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
            <table className="table table-hover" >
                <thead>
                <tr>
                    <th scope="col">Pharmacy</th>
                    <th scope="col">Address</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                <tbody styles={{'overflow':'scroll', 'display': 'block','height': '300px'}}>
                {this.state.pharmacy.map((pharmacy, index) => (
                    <tr>
                        <td>{pharmacy.name}</td>
                        <td >{pharmacy.address.country} {pharmacy.address.town} {pharmacy.address.street}</td>
                        <td>{pharmacy.medicationPrice}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        )
    }


}

export default MedicationPharmacy
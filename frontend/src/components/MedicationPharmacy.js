import React from "react";
import {Button, Col, FormControl, Row, Table} from "react-bootstrap";
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
            <div>
                <h4 >Pharmacys</h4>
                {this.state.pharmacy.length !=0 ?
                    <Table  hover variant="dark">
                        <thead style={{'color':'dimgrey '}}>
                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        {this.state.pharmacy.map((pharmacy, index) => (
                        <tbody styles={{'overflow':'scroll', 'display': 'block','height': '300px'}}>
                            <tr>
                                <td >{pharmacy.name}</td>
                                <td >{pharmacy.address.country} {pharmacy.address.town} {pharmacy.address.street}</td>
                                <td>{pharmacy.medicationPrice}</td>
                            </tr>
                        </tbody>
                        ))}
                    </Table>
                    :
                    <div>Medication isn't available</div>}
                </div>
        )
    }


}

export default MedicationPharmacy
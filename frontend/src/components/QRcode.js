import React from 'react';
import {Button, Container} from "react-bootstrap";
import UnregisteredLayout from "../layout/UnregisteredLayout"
import PharmacyListing from "../components/pharmacy/PharmacyListing"
import MedicationListing from "../components/MedicationListing"
import PatientLayout from "../layout/PatientLayout";

class QRcode extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            image: null
        };

        this.onImageChange = this.onImageChange.bind(this);
    }

    onImageChange = event => {
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            this.setState({
                image: URL.createObjectURL(img)
            });
        }
    };

    render() {
        return (
            <PatientLayout>
                <Container fluid>
                    <h3 style={{marginLeft:'1rem', marginTop:'2rem'}}>QR code</h3>

                    <div className="row"style={{marginTop: '1rem'}}>
                        <h>Select Image</h>
                        <input style={{marginLeft:'1rem'}} type="file" name="myImage" onChange={this.onImageChange} />
                    </div>
                    <img style={{height:300, width:300,marginLeft:'1rem'}} src={this.state.image} />

                </Container>
            </PatientLayout>

        );
    }
}export default QRcode;
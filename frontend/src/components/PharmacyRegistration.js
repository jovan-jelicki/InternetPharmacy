import React from "react";
import {Navbar, Form, Button} from "react-bootstrap";

export default class PharmacyRegistration extends React.Component {
    constructor() {
        super();
        this.state = {
            pharmacy: {
                name: '',
                address : {
                    street : "",
                    town : "",
                    country : "",
                    latitude : 51.507351,
                    longitude : -0.127758
                },
                description: '',
                dermatologist : [],
                pharmacist : [],
                medicationQuantity : [
                    {
                        medication:'',
                        quantity:''
                    }
                ],
                medicationReservation : [],
                grade : 0
            }

        }
        this.handleInputChange = this.handleInputChange.bind(this);

    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        const pharmacy = this.state.pharmacy;
        pharmacy[name] = value;

        this.setState({ pharmacy });
    }

    render() {
        return (
            <div>
                <h3 style={({ marginTop: '5rem', textAlignVertical: "center",textAlign: "center"})}>Pharmacy registration</h3>
                <div className="row" style={({ marginTop: '3rem' })} >
                    <label className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.name} name="name" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Pharmacy name" />
                        { this.state.submitted && this.state.errors.pharmacy.name.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.name}</span>}
                    </div>

                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Street</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.address.street} name="street" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Enter Street" />
                        { this.state.submitted && this.state.errors.pharmacy.address.street.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address.street}</span>}
                    </div>
                    <label >Town</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.address.town} name="town" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Enter Town" />
                        { this.state.submitted && this.state.errors.pharmacy.address.town.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address.town}</span>}
                    </div>
                    <label >Country</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.address.country} name="country" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Enter Country" />
                        { this.state.submitted && this.state.errors.pharmacy.address.country.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address.country}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Description</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.description} name="name" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Description" />
                        { this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Dermatologist</label>
                    <div className="col-sm-3 mb-2">
                        <select className="custom-select" value={this.state.pharmacy.dermatologist} name="dermatologist" id="inlineFormCustomSelect" onChange={this.inputChange}>

                        </select>
                        { this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Pharmacist</label>
                    <div className="col-sm-3 mb-2">
                        <select className="custom-select" value={this.state.pharmacy.pharmacist} name="pharmacist" id="inlineFormCustomSelect" onChange={this.inputChange}>

                        </select>
                        { this.state.submitted && this.state.errors.pharmacy.pharmacist.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.pharmacist}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Add Medications</label>
                    <div className="col-sm-3 mb-2">
                        <select className="custom-select" value={this.state.pharmacy.medicationQuantity.medication} name="medication"  onChange={this.inputChange}>
                        </select>
                        { this.state.submitted && this.state.errors.pharmacy.medicationQuantity.medication.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.medicationQuantity.medication}</span>}
                    </div>
                    <div className="col-sm-3 mb-2">
                        <input type="number" value={this.state.pharmacy.medicationQuantity.quantity} name="quantity" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Quantity" />

                        { this.state.submitted && this.state.errors.pharmacy.medicationQuantity.quantity.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.medicationQuantity.quantity}</span>}
                    </div>
                </div>
                <div className="row">
                    <div className="col-sm-5 mb-2">
                    </div>
                    <div className="col-sm-4">
                        <Button variant="primary" onClick={this.submitForm} >Done</Button>
                    </div>
                </div>

            </div>
        );
    }
}
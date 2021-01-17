import React from "react";
import {Button} from "react-bootstrap";
import Script from 'react-load-script';
import Select from "react-select";

const options = [
    { value: 'Marko', label: 'Marko Markovic' },
    { value: 'Zoran', label: 'Zoran Petrovic' },
    { value: 'Ana', label: 'Ana Matic' },
];

export default class PharmacyRegistration extends React.Component {
    constructor() {
        super();
        this.state = {
            pharmacy: {
                name: '',
                description: '',
                dermatologist : [],
                pharmacist : [],
                address : {
                    street : "",
                    town : "",
                    country : "",
                    latitude : 51.507351,
                    longitude : -0.127758
                },
                medicationReservation : [],
                grade : 0,
                pharmacyAdmin: [],

            },
            selectedOption: null,
            numberOfRows : 1,
            address2 : {
                street : "",
                town : "",
                country : "",
                latitude :"",
                longitude : ""
            },
        }
        this.handleInputChange = this.handleInputChange.bind(this);

    }
    handleDermatologistChange = selectedOption => {
        const newitems = this.state.pharmacy.dermatologist
        newitems.push(selectedOption)

        this.setState({
            dermatologist:newitems
        });
        console.log(`dermatologist selected`, selectedOption);
    };

    handlePharmacistChange = selectedOption => {
        const newitems = this.state.pharmacy.pharmacist
        newitems.push(selectedOption)

        this.setState({
            pharmacist:newitems
        });
        console.log(`Option selected:`, selectedOption);
    };
    handleAdminChange = selectedOption => {
        const newitems = this.state.pharmacy.pharmacyAdmin
        newitems.push(selectedOption)

        this.setState({
            pharmacyAdmin:newitems
        });
        console.log(`Option selected:`, selectedOption);
    };

    handleInputChange = (event) => {

        const { name, value } = event.target;
        //console.log(name);
        const address = this.state.pharmacy.address;
        const medicationQuantity = this.state.pharmacy.medicationQuantity;

        if(name=="medication"){
            medicationQuantity[name]=value;
        }else if(name=="quantity"){
            medicationQuantity[name]=value;
        }
        const pharmacy = this.state.pharmacy;
        pharmacy[name] = value;
        pharmacy[address]=address;
        pharmacy[medicationQuantity]=address;
        console.log(this.state.pharmacy)
        this.setState({ pharmacy });
    }


    handleSubmit = () => {
        console.log(this.state.medicationOrder);
    }
//, componentRestrictions: { country: "rs" }
    //                        <input type="text" value={this.state.pharmacy.address.street} name="street" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Enter Street" />
    handleScriptLoad = () => {
        var input = document.getElementById('street');
        var options = {
            types: ['geocode'] //this should work !
        };
        const google = window.google

        this.street = new google.maps.places.Autocomplete(input, options);
        this.street.addListener('place_changed', this.handlePlaceSelect);
    }

    handlePlaceSelect = () => {
        const addressObject = this.street.getPlace();
        try {
            console.log( addressObject.address_components)

            const address = addressObject.address_components;

            if (address) {
                this.setState(
                    {
                        //query: addressObject.formatted_address,
                        address2: {
                            street: address[1].long_name + " " + address[0].long_name,
                            town: address[2].long_name,
                            country: address[address.length-1].long_name,
                            latitude:addressObject.geometry.location.lat(),
                            longitude: addressObject.geometry.location.lng()
                        },
                    }
                );
            }
            this.state.pharmacy.address=this.state.address2;

        }
        catch{
        //treba da printa gresku
        }
    }



    render() {
        const { selectedOption } = this.state;
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
                        <Script type="text/javascript"url="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFrua9P_qHcmF253UAXnw1wHnIC7nD2DY&libraries=places" onLoad={this.handleScriptLoad}/>
                        <input type="text" id="street" placeholder="Enter Street" value={this.query}/>


                        { this.state.submitted && this.state.errors.pharmacy.address.street.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address.street}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Description</label>
                    <div className="col-sm-3 mb-2">
                        <input type="text" value={this.state.pharmacy.description} name="description" onChange={(e) => { this.handleInputChange(e)} } className="form-control" placeholder="Description" />
                        { this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>

                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Dermatologist</label>
                    <div className="col-sm-3 mb-2">
                        <Select isMulti isSearchable placeholder="Choose..." value={selectedOption}
                            onChange={this.handleDermatologistChange}
                            options={options}
                        />
                        { this.state.submitted && this.state.errors.pharmacy.description.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.description}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Pharmacist</label>
                    <div className="col-sm-3 mb-2">
                        <Select isMulti isSearchable placeholder="Choose..." value={selectedOption}
                            onChange={this.handlePharmacistChange}
                            options={options}
                        />
                        { this.state.submitted && this.state.errors.pharmacy.pharmacist.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.pharmacist}</span>}
                    </div>
                </div>
                <div className="row" style={({ marginTop: '1rem' })} >
                    <label className="col-sm-2 col-form-label">Pharmacy admin</label>
                    <div className="col-sm-3 mb-2">
                        <Select isMulti isSearchable placeholder="Choose..." value={selectedOption}
                                onChange={this.handleAdminChange}
                            options={options}
                        />
                        { this.state.submitted && this.state.errors.pharmacy.pharmacyAdmin.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.pharmacyAdmin}</span>}
                    </div>
                </div>

                <div className="row" style={({ marginTop: '1rem' })} >
                    <div className="row">
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div className="col-sm-4">
                            <Button variant="primary" onClick={this.submitForm} >Done</Button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }


}

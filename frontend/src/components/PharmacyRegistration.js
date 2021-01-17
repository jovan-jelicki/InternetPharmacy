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
            streetP:"",
            townP : "",
            countryP : "",
            errors: {
                pharmacy: {
                    name: 'Enter name',
                    description: 'Enter description',
                    dermatologist: 'Select dermatologist',
                    pharmacist: 'Select pharmacist',
                    address: 'Choose address',
                    pharmacyAdmin: 'Select admin',
                },
            }
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
        this.selectErrors(selectedOption,"dermatologist")
    };

    handlePharmacistChange = selectedOption => {
        const newitems = this.state.pharmacy.pharmacist
        newitems.push(selectedOption)

        this.setState({
            pharmacist:newitems
        });
        console.log(`Option selected:`, selectedOption);
        this.selectErrors(selectedOption,"pharmacist")
    };
    handleAdminChange = selectedOption => {
        const newitems = this.state.pharmacy.pharmacyAdmin
        newitems.push(selectedOption)

        this.setState({
            pharmacyAdmin:newitems
        });
        console.log(`Option selected:`, selectedOption);
        this.selectErrors(selectedOption,"pharmacyAdmin")

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
        this.validationErrorMessage(event);

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
            //console.log( addressObject.address_components)
            const address = addressObject.address_components;
            if (address) {
                if(this.setAddressParams(address)) {
                    this.setState(
                        {
                            //query: addressObject.formatted_address,
                            pharmacy: {
                                address: {
                                    street: this.state.streetP,
                                    town: this.state.townP,
                                    country: this.state.countryP,
                                    latitude: addressObject.geometry.location.lat(),
                                    longitude: addressObject.geometry.location.lng()
                                }
                            },
                        }
                    );
                }
            }else{
                this.addressErrors(false)
            }
            console.log("ispisi")
            console.log(this.state.pharmacy)
        }
        catch{
        //treba da printa gresku
            this.addressErrors(false)
        }
    }

    setAddressParams(address) {
        var street, number, city, country,completeAddress,i;
        for( i=0;i<address.length;i++) {
            if (address[i].types == "route") {
                street = address[i].long_name;
            }else if(address[i].types == "street_numbe") {
                number = address[i].long_name;
            }else  if(address[i].types[0] == "locality") {
                city = address[i].long_name;
            }else  if(address[i].types[0] == "country") {
                country = address[i].long_name;
            }
        }
        completeAddress=street+" "+number+" "+city+" "+country;
        console.log("ADRESA"+completeAddress)

        if(street==undefined || street=="" || city==undefined || city=="" || country==undefined || country==""){
            console.log("NESTO NE VALJA")
                this.addressErrors(false)
                return false;
        }else{
            console.log("PROSAO")
            this.addressErrors(true)
            this.state.townP=city;
            this.state.countryP=country;
            if(number==undefined)
                this.state.streetP=street;
            else
                this.state.streetP=street+number;
        }
        return  true;
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
                        { this.state.submitted && this.state.errors.pharmacy.address.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.address}</span>}
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

                        { this.state.submitted && this.state.errors.pharmacy.dermatologist.length > 0 &&  <span className="text-danger">{this.state.errors.pharmacy.dermatologist}</span>}
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

    submitForm = async (event) => {
        this.setState({ submitted: true });
        const pharmacy = this.state.pharmacy;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
        } else {
            console.log('Invalid Form')
        }
    }
    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.pharmacy).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }

    selectErrors(value,role){
        let errors = this.state.errors;
        switch (role){
            case 'dermatologist':
                errors.pharmacy.dermatologist = value.length < 1 ? 'Select dermatolgistttt' : '';
                break;
            case 'pharmacist':
                errors.pharmacy.pharmacist = value.length < 1 ? 'Select pharmacist' : '';
                break;
            case 'pharmacyAdmin':
                errors.pharmacy.pharmacyAdmin = value.length < 1 ?  'Select pharmacy admin' : '';
                break;
        }
        this.setState({ errors });
    }
    addressErrors(bool) {
        let errors = this.state.errors;
        if(bool==false) {
            //.log("Nisam dobro prosla")
            errors.pharmacy.address = 'Please choose valid address';
        }else{
            //console.log("DObro sam prosla ");
            errors.pharmacy.address = ' ';
        }
            this.setState({ errors });
    }

    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;
        switch (name) {
            case 'name':
                errors.pharmacy.name = value.length < 1 ? 'Enter Name' : '';
                break;
            case 'description':
                errors.pharmacy.description = value.length < 1 ? 'Enter Description' : '';
                break;
           //case 'address':
            //    errors.pharmacy.address = this.isAddressValid(value) ?  'Enter Address' : '';
             //   break;

            default:
                break;
        }

        this.setState({ errors });
    }


}

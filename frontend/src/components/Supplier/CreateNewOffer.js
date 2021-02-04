import React from "react";
import DatePicker from "react-datepicker";
import {Button, Modal} from "react-bootstrap";


export default class CreateNewOffer extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            modalClose:false,
            // order : this.props.order,
            medicationOrder : {
                pharmacyAdmin : {
                    firstName : 'Mirko',
                    lastName : 'Mirkovic'
                },
                deadLine : '21.3.2021.',
                medicationQuantity: {

                },
                status : 'pending',
            },

            medicationOffer:   {
                supplier : "",
                price : 1,
                shippingDate : '',
                status : ''
            },
            errors: {
                medicationOffer: {
                    price: 'Enter price',
                    shippingDate: 'Choose shipping date',
                }
            }
        }
    }
    handleInputChange = (event) => {

        const { name, value } = event.target;
        const medicationOffer = this.state.medicationOffer;
        medicationOffer[name] = value;

        this.setState({ medicationOffer });
        this.validationErrorMessage(event);
    }

    changeDatePicker = (date) => {
        let errors = this.state.errors;
        errors.medicationOffer.shippingDate = this.state.errors.medicationOffer.shippingDate.length < 1 ? 'Choose shipping date' : '';
        this.setState({ errors });

        this.setState({
                medicationOffer : {
                    ...this.state.medicationOffer,
                    shippingDate : date
                }
            }
        );
    }

    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;
        errors.medicationOffer.price = value.length < 1 ? 'Enter price' : '';
        this.setState({ errors });
    }


    submitForm = async (event) => {
        console.log(this.state.medicationOffer)


        this.setState({ submitted: true });
        const medicationOffer = this.state.medicationOffer;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
            this.setState({
            })
        } else {
            console.log('Invalid Form')
        }
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.medicationOffer).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }


    render() {
        console.log(this.props.order)
        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'silver', color: 'rgb(0, 92, 230)'}}>
                <div>
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-md-12">
                                <div className="card">
                                    <h5 className="card-header">
                                        Order
                                    </h5>
                                    <div className="card-body">
                                        <p className="card-text">
                                            Kreirao : {this.props.order.pharmacyAdmin.firstName + " " + this.props.order.pharmacyAdmin.lastName}
                                            <br/>
                                            Status : {this.props.order.status}
                                            <br/>
                                            Rok isporuke : {this.props.order.deadline.split("T")[0]}
                                            <br/>
                                            <br/>
                                            Lekovi
                                            <ul>
                                                {this.props.order.medicationQuantity.map((e, key) => {
                                                    return <option key={key}
                                                                   value={e.medication}>{e.medication.name} : {e.quantity}</option>

                                                })
                                                }
                                            </ul>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="card" style={{ background: '#ABB8C3', color: 'rgb(0, 92, 230)', marginTop:'5rem', marginBottom:'3rem'}}>
                    <h2 style={{marginTop: '1rem', marginLeft:'12rem'}}>Offer</h2>

                    <div className="row"style={{marginTop: '1rem'}}>
                        <label  className="col-sm-4 col-form-label">Price</label>
                        <div className="col-sm-6 mb-2">
                            <input type="number"  name="price" className="form-control" id="price" placeholder="Enter price" onChange={(e) => { this.handleInputChange(e)} } className="form-control"/>
                            { this.state.submitted && this.state.errors.medicationOffer.price.length > 0 &&  <span className="text-danger">{this.state.errors.medicationOffer.price}</span>}

                        </div>
                        <div className="col-sm-4">
                        </div>
                    </div>
                    <div className="row"style={{marginTop: '1rem'}}>
                        <label  className="col-sm-4 col-form-label">Shipping date</label>
                        <div className="col-sm-6 mb-2">
                            <DatePicker  selected={this.state.medicationOffer.shippingDate} minDate={new Date()} onChange={date => this.changeDatePicker(date)} />
                            { this.state.submitted && this.state.errors.medicationOffer.shippingDate.length > 0 &&  <span className="text-danger">{this.state.errors.medicationOffer.shippingDate}</span>}

                        </div>
                        <div className="col-sm-4">
                        </div>
                    </div>
                </div>
                <div className="row" style={{marginTop: '1rem'}}>
                    <div className="col-sm-5 mb-2">
                    </div>
                    <div className="row">

                        <Button style={{marginLeft: '7rem'}} variant="primary" onClick={this.submitForm} >Submit</Button>
                    </div>
                </div>

            </div>
        );
    }

}

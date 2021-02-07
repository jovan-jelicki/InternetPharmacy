import React from "react";
import {Button, Col, Container, Form, FormControl} from "react-bootstrap";
import axios from "axios";

export default class LoyaltyProgram extends React.Component {
    constructor() {
        super();
        this.state = {
            loyalty: {
                category: '',
                minPoints: '',
                maxPoints: '',
                discount: '',
            },
            errors:{
                loyalty: {
                   // category: 'Choose category',
                    minPoints: 'Enter min points',
                    maxPoints: 'Enter max points',
                    discount: 'Enter discount',
                }
            },
            submitted: false,
        }
    }

    async sendData() {
        axios
            .post('http://localhost:8080/api/loyaltyScale/save', {
                'category': 0,
                'minPoints': this.state.loyalty.minPoints,
                'maxPoints' : this.state.loyalty.maxPoints,
                'discount' : this.state.loyalty.discount,
            })
            .then(res => {
                alert("Successfully added!");

            }).catch(() => {
            alert("Prooooblem!")
        })

    }


    handleInputChange = (event) => {
        const { name, value } = event.target;
        const loyalty = this.state.loyalty;
        loyalty[name] = value;

        this.setState({ loyalty });

        console.log(this.state.loyalty)
        this.validationErrorMessage(event);
    }


    handleCategorySelected= (event) => {
        const target = event.target;

        let value = event.target.value;
        //console.log(value)
        this.setState({
            loyalty: {
                ...this.state.loyalty,
                category: value
            }
        })
        this.state.loyalty.category=value;
        console.log(this.state.loyalty.category)
        this.validationErrorMessage(event);
    }

    submitForm = async (event) => {
        this.setState({ submitted: true });
       // this.state.submitted=true;
        console.log(this.state.submitted)
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.log('Valid Form')
           this.sendData();
        } else {
            console.log('Invalid Form')
        }
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.loyalty).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }

    validationErrorMessage = (event) => {
        const { name, value } = event.target;
        let errors = this.state.errors;
        console.log(name)

        switch (name) {
            case 'category':
                errors.loyalty.category = value.length ? '' : 'Choose category';
                break;
            case 'minPoints':
                errors.loyalty.minPoints = value.length < 1 ? 'Enter min points' : '';
                break;
            case 'maxPoints':
                errors.loyalty.maxPoints = value.length < 1 ? 'Enter max points' : '';
                break;
            case 'discount':
                errors.loyalty.discount = value.length < 1 ? 'Enter discount' : '';
                break;
            default:
                break;
        }
        this.setState({ errors });
    }


    render() {
        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <div className="container">
                    <h1 style={({marginTop: '1rem', textAlignVertical: "center", textAlign: "center"})} className="display-4">Loyalty program</h1>
                </div>

                <div className="row" style={{marginTop: '3rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Choose category</label>
                    <div className="col-sm-3 mb-2">
                         <Form.Control placeholder="Category" as={"select"} value={this.state.category} onChange={(e)=>{this.handleCategorySelected(e)}}>
                            <option disabled={false} selected="selected">Choose</option>
                                <option key="regular" value="regular">Regular</option>
                                <option key="silver" value="silver">Silver</option>
                                <option key="gold" value="gold">Gold</option>
                        </Form.Control>
                    </div>
                </div>
                <div className="row" style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter min points</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" value={this.state.loyalty.minPoints} id="minPoints" name="minPoints" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Min points"/>
                        {this.state.submitted && this.state.errors.loyalty.minPoints> 0 && <span className="text-danger">{this.state.errors.loyalty.minPoints}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter max points</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number"  min={0} value={this.state.loyalty.maxPoints} id="maxPoints" name="maxPoints" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Max points"/>
                        {this.state.submitted && this.state.errors.loyalty.maxPoints> 0 && <span className="text-danger">{this.state.errors.loyalty.maxPoints}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter discount</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" value={this.state.loyalty.discount} id="discount" name="discount" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Discount"/>
                        {this.state.submitted && this.state.errors.loyalty.discount> 0 && <span className="text-danger">{this.state.errors.loyalty.discount}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <div className="row">
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div className="col-sm-4">
                            <Button variant="primary" onClick={this.submitForm}>Define</Button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
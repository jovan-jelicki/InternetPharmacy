import React from "react";
import {Alert, Button, Col, Container, Form, FormControl} from "react-bootstrap";
import axios from "axios";
import HelperService from "../../helpers/HelperService";


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
            program:{
                appointmentPoints:'',
                consultingPoints:''
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
            loyScales:[],
            loyPrograms:[],
            regularCategory:[],
            silverCategory:[],
            goldCategory:[],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}


        }
    }

    async componentDidMount() {
       this.fetchLoyaltyScales()
        this.fetchLoyaltyProgram()
    }

    fetchLoyaltyScales(){
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/loyaltyScale/"
         //   : 'http://localhost:8080/api/loyaltyScale/';
        axios
            .get(HelperService.getPath('/api/loyaltyScale'),{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    loyScales : res.data
                })
                this.getCategories();
            })
    }

    getCategories=()=>{
        let scales=this.state.loyScales;
        for (var j = 0, l = scales.length; j < l; j++) {
            if(scales[j].category=="regular"){
                this.setState({
                    regularCategory:scales[j]
                })
            }
            if(scales[j].category=="silver"){
                this.setState({
                    silverCategory:scales[j]
                })
            }
            if(scales[j].category=="gold"){
                this.setState({
                    goldCategory:scales[j]
                })
            }
        }
    }



    fetchLoyaltyProgram(){
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/loyaltyProgram/"
          //  : 'http://localhost:8080/api/loyaltyProgram';
        axios
            .get(HelperService.getPath('/api/loyaltyProgram'),{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    loyPrograms : res.data
                })
                //console.log("PROGRAM")
                //console.log(this.state.loyPrograms);
            })
    }

    async sendData() {
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/loyaltyScale/save"
          //  : 'http://localhost:8080/api/loyaltyScale/save';
        axios
            .post(HelperService.getPath('/api/loyaltyScale/save'), {
                'category': this.state.loyalty.category,
                'minPoints': this.state.loyalty.minPoints,
                'maxPoints' : this.state.loyalty.maxPoints,
                'discount' : this.state.loyalty.discount,
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert("Successfully added!");
                this.fetchLoyaltyScales()
            }).catch(() => {
            alert("Prooooblem!")
        })
    }

    async sendProgram() {
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/loyaltyProgram/save"
         //   : 'http://localhost:8080/api/loyaltyProgram/save';
        axios
            .post(HelperService.getPath('/api/loyaltyProgram/save'), {
                'appointmentPoints': this.state.program.appointmentPoints,
                'consultingPoints': this.state.program.consultingPoints,
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                alert("Successfully added!");
                this.fetchLoyaltyProgram()
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

    handleInputProgramChange = (event) => {
        const { name, value } = event.target;
        const program = this.state.program;
        program[name] = value;

        this.setState({ program });

       // console.log(this.state.loyalty)
       // this.validationErrorMessage(event);
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
        if (this.validateForm(this.state.errors) &&  this.checkValues()) {
            console.log('Valid Form')
           this.sendData();
        } else {
            console.log('Invalid Form')
        }
    }

    checkValues(){
        let valid = true;
        alert(this.state.loyalty.maxPoints +"bla"+ this.state.loyalty.minPoints)
        let maxBr=Number(this.state.loyalty.maxPoints)
        let minBr=Number(this.state.loyalty.minPoints)
        if( maxBr<= minBr){
            valid=false;
            alert("Please check min and max value of points.")
        }
        return valid;
    }

    submitProgramForm = async (event) => {
        this.setState({ submitted: true });
        event.preventDefault();
       // if (this.validateForm(this.state.errors)) {
            console.log('Valid Form')
            this.sendProgram();
       // } else {
       //     console.log('Invalid Form')
       // }
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
                <div style={{height:20, marginLeft:20, color:'dimgray '}}>
                    <h5 >Loyalty scales:</h5>
                    {this.state.loyScales.map((e, key) => {
                          return  <div className="row">
                                <div className="container-fluid">
                                        <div className="col-md-3" style={{background:'silver'}}>
                                            <div className="card" >
                                                <h5 className="card-header" style={{color:'darkslategrey',background:'lightgrey '}}>Category: {e.category}</h5>
                                                <div className="card-body" style={{background:'silver', color:'cadetblue'}}>
                                                    <p className="card-text">
                                                        Min points: {e.minPoints}
                                                        <br/>
                                                        Max points : {e.maxPoints}
                                                        <br/>
                                                        <br/>
                                                        Discount: {e.discount}
                                                    </p>
                                                </div>
                                            </div>
                                    </div>
                                </div>
                            </div>

                        })
                    }
                    <div style={{height:20,marginTop:20, color:'dimgray '}}>
                        <h5>Loyalty program</h5>
                        {this.state.loyPrograms.map((e, key) => {
                           return  <div className="row">
                                <div className="container-fluid">
                                    <div className="col-md-3" style={{background:'silver'}}>
                                        <div className="card" >
                                            <h5 className="card-header" style={{color:'darkslategrey',background:'lightgrey '}}>Category: {e.category}</h5>
                                            <div className="card-body" style={{background:'silver', color:'cadetblue'}}>
                                                <p className="card-text">
                                                    Appointment points : {e.appointmentPoints}
                                                    <br/>
                                                    Consulting points: {e.consultingPoints}
                                                    <br/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>



                        })
                        }
                    </div>
                    </div>

                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center"})}>Edit loyalty scale for users</h3>
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
                        <input type="number" min="0" value={this.state.loyalty.minPoints} id="minPoints" name="minPoints" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Min points"/>
                        {this.state.submitted && this.state.errors.loyalty.minPoints> 0 && <span className="text-danger">{this.state.errors.loyalty.minPoints}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter max points</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" min="0" value={this.state.loyalty.maxPoints} id="maxPoints" name="maxPoints" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Max points"/>
                        {this.state.submitted && this.state.errors.loyalty.maxPoints> 0 && <span className="text-danger">{this.state.errors.loyalty.maxPoints}</span>}
                    </div>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter discount</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" min="0" max="100" value={this.state.loyalty.discount} id="discount" name="discount" onChange={(e) => { this.handleInputChange(e)}} className="form-control" placeholder="Discount"/>
                        {this.state.submitted && this.state.errors.loyalty.discount> 0 && <span className="text-danger">{this.state.errors.loyalty.discount}</span>}
                    </div>
                </div>
                <div style={({ marginLeft:850, marginBottom:30, height:20, width:500})}>
                    <Alert variant='success' show={true}  style={({textAlignVertical: "center", textAlign: "center"})}>
                        Be careful to define consecutive category points!
                    </Alert>
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



                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center", marginTop:200})}>Add loyalty program</h3>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter appointemnt points</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" value={this.state.program.appointmentPoints} id="appointmentPoints" name="appointmentPoints" onChange={(e) => { this.handleInputProgramChange(e)}} className="form-control" placeholder="Appointment points"/>
                    </div>
                </div>
                <div className="row"
                     style={{marginTop: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <label className="col-sm-2 col-form-label">Enter consulting points</label>
                    <div className="col-sm-3 mb-2">
                        <input type="number" value={this.state.program.consultingPoints} id="consultingPoints" name="consultingPoints" onChange={(e) => { this.handleInputProgramChange(e)}} className="form-control" placeholder="Consulting points"/>
                    </div>
                </div>
                <div className="row"
                     style={{margin: '1rem', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <div className="row">
                        <div className="col-sm-5 mb-2">
                        </div>
                        <div className="col-sm-4">
                            <Button variant="primary" onClick={this.submitProgramForm}>Define</Button>
                        </div>
                    </div>

                </div>
            </div>
        );
    }
}
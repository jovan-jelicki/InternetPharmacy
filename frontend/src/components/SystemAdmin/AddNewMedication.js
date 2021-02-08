import React from "react";
import Script from "react-load-script";
import {Button, Form, FormControl, Row} from "react-bootstrap";
import axios from "axios";

export default class AddNewMedication extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medication:{
                name:'',
                dose:'',
                note:'',
                manufacturer:'',
                loyaltyPoints:''
            },
            type:'',
            shape:'',
            medIngredients:[{
                id:'',
            }],
            ingredientsResult:[{
                id:'',
                name:'',
            }],
            medSideEffects:[{
                id:'',
            }],
            medAlternatives:[{
                id:'',
            }],
            medIssue:'',
            errors:{
                medication: {
                    name: 'Enter name',
                    type: 'Select type',
                    dose: 'Enter dose',
                    shape: 'Select shape',
                    ingredient: 'Select ingredients',
                    sideEffects: 'Select side effects',
                    medIssue:'Select medication issue',
                    manufacturer:'Enter manufacturers credentials',
                    loyaltyPoints:'Enter loyalty points'
                }
            },
            ingredients :[],
            alternatives:[],
            sideEffects:[],
            ingredientBackup:[],
            validForm: false,
            submitted: false,
        }
    }

    async componentDidMount() {
        await axios.get("http://localhost:8080/api/ingredients/").then(res => {
            this.setState({
                ingredients : res.data
            });
        })
        await axios.get("http://localhost:8080/api/medications/").then(res => {
            this.setState({
                alternatives : res.data
            });
        })
        await axios.get("http://localhost:8080/api/sideEffects/getAll").then(res => {
            this.setState({
                sideEffects : res.data
            });
        })
        console.log("side")
        console.log(this.state.sideEffects);
    }

    async sendParams() {
        console.log(this.state.shape)
        console.log(this.state.medIngredients)
        console.log(this.state.medAlternatives)
        console.log(this.state.medSideEffects)
        console.log(this.state.medIssue)

       // console.log(this.state.medication)
        axios
            .post('http://localhost:8080/api/medications', {
                'id':'',
                'name': this.state.medication.name,
                'dose' :this.state.medication.dose,
                'type' : this.state.type,
                'medicationShape':this.state.shape,
                'ingredient':this.state.medIngredients,
                'alternatives':this.state.medAlternatives,
                'sideEffect':this.state.medSideEffects,
                'manufacturer':this.state.medication.manufacturer,
                'medicationIssue':this.state.medIssue,
                'note':this.state.medication.note,
                'loyaltyPoints':this.state.medication.loyaltyPoints
            })
            .then(res => {
                alert("Successfully registered!");

            }).catch(() => {
            alert("Pharmacy was not registered successfully!")
        })

    }


    handleMultiChange =(event) =>{
        var options = event.target.options;
        var value = [];
        for (var i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        if(event.target.name=="sideEffects") {
            this.state.medication.sideEffects.push(value);
        }else if(event.target.name=="ingredients") {
            this.state.medication.ingredient.push(value);
        }if(event.target.name=="alternatives") {
            this.state.medication.alternatives.push(value);
        }
        this.validationErrorMessage(event)

        this.setState({meditation: value});
        console.log(this.state.medication)

    }
    handleInputChange = (event) => {
        const { name, value } = event.target;
        const medication = this.state.medication;
        medication[name] = value;
        console.log(this.state.medication)

        this.setState({ medication });
        this.validationErrorMessage(event);
    }

    submitForm = async (event) => {
        this.setState({submitted: true});
        const medication = this.state.medication;
        event.preventDefault();
        if (this.validateForm(this.state.errors)) {
            console.info('Valid Form')
        } else {
            this.sendParams();
            console.log('Invalid Form')
        }
    }

    validationErrorMessage = (event) => {
        const {name, value} = event.target;
        let errors = this.state.errors;
        console.log(name)
        switch (name) {
            case 'name':
                errors.medication.name = value.length < 1 ? 'Enter Name' : '';
                break;
            case 'dose':
                errors.medication.dose = value.length < 1 ? 'Enter Dose' : '';
                break;
            case 'type':
                errors.medication.type = value.length < 1 ? 'Select type' : '';
                break;
            case 'shape':
                errors.medication.shape = value.length < 1 ? 'Select shape' : '';
                break;
            case 'ingredients':
                errors.medication.ingredient = value.length < 1 ? 'Select ingredients' : '';
                break;
            case 'sideEffects':
                errors.medication.sideEffects = value.length < 1 ? 'Select sideEffects' : '';
                break;
            case 'manufacturer':
                errors.medication.manufacturer = value.length < 1 ? 'Enter manufacturers credentials' : '';
                break;
            case 'medIssue':
                errors.medication.medIssue = value.length < 1 ? 'Select medication issue' : '';
                break;
            case 'loyaltyPoints':
                errors.medication.loyaltyPoints = value.length < 1 ? 'Enter loyalty points' : '';
                break;
            default:
                break;
        }

        this.setState({errors});
    }

    validateForm = (errors) => {
        let valid = true;
        Object.entries(errors.medication).forEach(item => {
            console.log(item)
            item && item[1].length > 0 && (valid = false)
        })
        return valid;
    }
    onTypeChange=(event) => {
        var option = event.target.id
        this.setState({
            type:option
        })
        this.state.type=option;
        console.log(this.state.type)
        console.log(event)

        this.validationErrorMessage(event)
    }

    onShapeChange=(event) => {
        var option = event.target.id
        this.setState({
            shape:option
        })
        this.state.shape=option;
        console.log(this.state.shape)
        this.validationErrorMessage(event)

    }

    onIssueChange=(event) => {
        var option = event.target.id
        this.setState({
            medIssue:option
        })
        this.state.medIssue=option;
        console.log(this.state.medIssue)
        this.validationErrorMessage(event)

    }

    onIngredientChange=(event) => {
        var option = event.target.id
        console.log(option)
        let ingredients = this.state.ingredients
        let ingr=[]
        ingredients.forEach(ingredient => {
            console.log(ingredients)
            if (ingredient.name === option)
                ingredient.isChecked =  event.target.checked
            if(ingredient.isChecked){
                ingr.push(ingredient)
            }
        })
        console.log("SASTOJCI")
        console.log(ingr)

        this.state.medIngredients=ingr;
        console.log(this.state.medIngredients)


        //this.setState({ingredientBackup: ingr})

        //console.log("IDD")
        //console.log(this.state.medIngredients.id)

        this.validationErrorMessage(event)

    }

    onAlternativeChange=(event) => {
        var option = event.target.id
        let alternatives = this.state.alternatives
        let alters=[]
        alternatives.forEach(alternative => {
            if (alternative.name === option)
                alternative.isChecked =  event.target.checked
            if(alternative.isChecked){
                alters.push(alternative)
            }
        })
        this.state.medAlternatives=alters;
        //this.setState({ingredientBackup: ingr})
        console.log(this.state.medAlternatives)
        this.validationErrorMessage(event)
    }
    onSideEffectsChange=(event) => {
        var option = event.target.id
        let sideEffects = this.state.sideEffects
        let effects=[]
        sideEffects.forEach(sideEffect => {
            if (sideEffect.name === option)
                sideEffect.isChecked =  event.target.checked
            if(sideEffect.isChecked){
                effects.push(sideEffect)
            }
        })
        this.state.medSideEffects=effects;
        //this.setState({ingredientBackup: ingr})
        console.log(this.state.medSideEffects)
    }


        render() {
        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center"})}>Add new medication</h3>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Name</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.medication.name} name="name" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="name" placeholder="Enter name" />
                        { this.state.submitted && this.state.errors.medication.name.length > 0 && <span className="text-danger">{this.state.errors.medication.name}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Manufacturer</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.medication.manufacturer} name="manufacturer" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="manufacturer" placeholder="Enter manufacturers credentials" />
                        { this.state.submitted && this.state.errors.medication.manufacturer.length > 0 && <span className="text-danger">{this.state.errors.medication.manufacturer}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Type</label>
                    <div className="col-sm-6 mb-2">
                        <fieldset>
                            <Form >
                                <Form.Group as={Row} >
                                    <Row sm={10} style={{'marginLeft':'1rem'}} >
                                        <Form.Check multiple style={{'marginLeft':'1rem'}} type="radio" label="antibiotic" name="type"id="antibiotic" onChange={this.onTypeChange} />
                                        <Form.Check multiple style={{'marginLeft':'1rem'}} type="radio" label="anesthetic" name="type"id="anesthetic" onChange={this.onTypeChange} />
                                        <Form.Check multiple  style={{'marginLeft':'1rem'}} type="radio" label="antihistamine" name="type" id="antihistamine" onChange={this.onTypeChange} />
                                    </Row>
                                </Form.Group>
                            </Form>
                        </fieldset>
                        {this.state.submitted && this.state.errors.medication.type.length > 0 &&  <span className="text-danger">{this.state.errors.medication.type}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Dose</label>
                    <div className="col-sm-6 mb-2">
                        <input type="number" value={this.state.medication.dose} name="dose" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="dose" placeholder="Enter dose" />
                        { this.state.submitted && this.state.errors.medication.dose.length > 0 && <span className="text-danger">{this.state.errors.medication.dose}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label" style={{fontWeight: "bolder"}}>Medication shape</label>
                    <div className="col-sm-6 mb-2">
                        <fieldset>
                            <Form>
                                <Form.Group as={Row}>
                                    <Row sm={10} style={{marginLeft:'1rem', marginTop:'0.6rem'}}>
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="powder" name="shape"id="powder" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="capsule" name="shape"id="capsule" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="pill" name="shape" id="pill" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="ointment" name="shape" id="ointment" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="gel" name="shape" id="gel" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="solution" name="shape" id="solution" onChange={this.onShapeChange} />
                                        <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="syrup" name="shape" id="syrup" onChange={this.onShapeChange} />
                                    </Row>
                                </Form.Group>
                            </Form>
                        </fieldset>
                        {this.state.submitted && this.state.errors.medication.shape.length > 0 &&  <span className="text-danger">{this.state.errors.medication.shape}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Medication issue</label>
                    <div className="col-sm-6 mb-2">
                        <fieldset>
                            <Form >
                                <Form.Group as={Row} >
                                    <Row sm={10} style={{'marginLeft':'1rem'}} >
                                        <Form.Check multiple style={{'marginLeft':'1rem'}} type="radio" label="withPrescription" name="medIssue" id="withPrescription" onChange={this.onIssueChange} />
                                        <Form.Check multiple style={{'marginLeft':'1rem'}} type="radio" label="withoutPrescription" name="medIssue" id="withoutPrescription" onChange={this.onIssueChange} />
                                    </Row>
                                </Form.Group>
                            </Form>
                        </fieldset>
                        {this.state.submitted && this.state.errors.medication.medIssue.length > 0 &&  <span className="text-danger">{this.state.errors.medication.medIssue}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label className="col-sm-2 col-form-label" style={{fontWeight: "bolder"}}>Ingredients</label>
                    <div className="col-sm-6 mb-2">
                        {this.state.ingredients.map(ingredient =>
                                <fieldset>
                                    <Form>
                                        <Form.Group as={Row}>
                                            <Row sm={10} style={{'marginLeft':'1rem', marginTop:'0.6rem'}}>
                                                <Form.Check style={{'marginLeft':'1rem'}} type="checkbox" label={ingredient.name}  name="ingredients" id={ingredient.name}  onChange={this.onIngredientChange} />
                                            </Row>
                                        </Form.Group>
                                    </Form>
                                </fieldset>
                            )}
                        { this.state.submitted && this.state.errors.medication.ingredient.length > 0 &&  <span className="text-danger">{this.state.errors.medication.ingredient}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label" style={{fontWeight: "bolder"}}>Alternatives</label>
                    <div className="col-sm-6 mb-2">
                        {this.state.alternatives.map(alternative =>
                            <fieldset>
                                <Form>
                                    <Form.Group as={Row}>
                                        <Row sm={10} style={{'marginLeft':'1rem', marginTop:'0.6rem'}}>
                                            <Form.Check style={{'marginLeft':'1rem'}} type="checkbox" label={alternative.name}  name="alternatives" id={alternative.name}  onChange={this.onAlternativeChange} />
                                        </Row>
                                    </Form.Group>
                                </Form>
                            </fieldset>
                        )}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label" style={{fontWeight: "bolder"}}>Side effects</label>
                    <div className="col-sm-6 mb-2">
                        {this.state.sideEffects.map(sideEffect =>
                            <fieldset>
                                <Form>
                                    <Form.Group as={Row}>
                                        <Row sm={10} style={{'marginLeft':'1rem', marginTop:'0.6rem'}}>
                                            <Form.Check style={{'marginLeft':'1rem'}} type="checkbox" label={sideEffect.name}  name="sideEffects" id={sideEffect.name}  onChange={this.onSideEffectsChange} />
                                        </Row>
                                    </Form.Group>
                                </Form>
                            </fieldset>
                        )}
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Note</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.medication.note} name="note" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="note" placeholder="Enter note" />
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label"  style={{fontWeight: "bolder"}}>Loyalty points</label>
                    <div className="col-sm-6 mb-2">
                        <input type="number" value={this.state.medication.loyaltyPoints} name="loyaltyPoints" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="loyaltyPoints" placeholder="Enter loyalty points" />
                        { this.state.submitted && this.state.errors.medication.loyaltyPoints.length > 0 && <span className="text-danger">{this.state.errors.medication.loyaltyPoints}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem'}}>
                    <div className="col-sm-5 mb-2">
                    </div>
                    <div className="col-sm-4">
                        <Button variant="primary" onClick={this.submitForm} >Submit</Button>
                    </div>
                </div>

            </div>
        );
    }
}
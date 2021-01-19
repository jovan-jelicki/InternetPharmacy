import React from "react";
import Script from "react-load-script";
import {Button, FormControl} from "react-bootstrap";

export default class AddNewMedication extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            medication:{
                name:'',
                type:'',
                dose:'',
                shape:'',
                ingredient:[
                    {
                        name:''
                    }
                ],
                sideEffects:[
                    {
                        name:''
                    }
                ],
                alternatives:[
                    {
                        name:''
                    }
                ]

            },
            errors:{
                medication: {
                    name: '',
                    type: '',
                    dose: '',
                    shape: '',
                    ingredient: '',
                    sideEffects: '',
                    alternatives:''
                }
            },
            validForm: false,
            submitted: false,
            ingredient:''
        }
    }



    handleInputChange = (event) => {
        const { name, value } = event.target;
        const medication = this.state.medication;
        medication[name] = value;
        console.log(this.state.medication)

        this.setState({ medication });
      //  this.validationErrorMessage(event);
    }

    render() {
        let ingredients = ["saharoza", "dekstroza","aspirin"];
        const  ingredientsTag = ingredients.map((ingredient, key) =>
            <option value={ingredient}> {ingredient} </option>
        );

        let shape = ["powder","capsule","pill","ointment","gel","solution","syrup"];
        const  shapeTag = shape.map((shape, key) =>
            <option value={shape}> {shape} </option>
        );

        let sideEffects = ["povracanje","znojenje","alergija"];
        const  sideEffectsTag = sideEffects.map((sideEffects, key) =>
            <option value={sideEffects}> {sideEffects} </option>
        );

        let alternatives = ["aspirin","brufen","nimulid"];
        const  alternativesTag = alternatives.map((alternatives, key) =>
            <option value={alternatives}> {alternatives} </option>
        );

        let type = ["antibiotic","anesthetic","antihistamine"];
        const  typeTag = type.map((type, key) =>
            <option value={type}> {type} </option>
        );

        return (
            <div className="jumbotron jumbotron-fluid"  style={{ background: 'rgb(232, 244, 248 )', color: 'rgb(0, 92, 230)'}}>
                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center"})}>Add new medication</h3>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Name</label>
                    <div className="col-sm-6 mb-2">
                        <input type="text" value={this.state.medication.name} name="name" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="name" placeholder="Enter name" />
                        { this.state.submitted && this.state.errors.medication.name.length > 0 && <span className="text-danger">{this.state.errors.medication.name}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Type</label>
                    <div className="col-sm-6 mb-2">
                        <select multiple={false}  value={this.state.medication.type} onChange={(e) => { this.handleInputChange(e)} }  >
                            {typeTag}
                        </select>
                        { this.state.submitted && this.state.errors.medication.type.length > 0 && <span className="text-danger">{this.state.errors.medication.type}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>
                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Dose</label>
                    <div className="col-sm-6 mb-2">
                        <input type="number" value={this.state.medication.dose} name="dose" onChange={(e) => { this.handleInputChange(e)} }  className="form-control" id="dose" placeholder="Enter dose" />
                        { this.state.submitted && this.state.errors.medication.dose.length > 0 && <span className="text-danger">{this.state.errors.medication.dose}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Medication shape</label>
                    <div className="col-sm-6 mb-2">
                        <select multiple={false} value={this.state.medication.shape} onChange={(e) => { this.handleInputChange(e)} }  >
                            {shapeTag}
                        </select>
                        {this.state.submitted && this.state.errors.medication.shape.length > 0 &&  <span className="text-danger">{this.state.errors.medication.shape}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label className="col-sm-2 col-form-label">Ingredients</label>
                    <div className="col-sm-6 mb-2">
                        <select multiple={true}  value={this.state.medication.ingredient} onChange={(e) => { this.handleInputChange(e)} }  >
                            {ingredientsTag}
                        </select>
                        { this.state.submitted && this.state.errors.medication.ingredient.length > 0 &&  <span className="text-danger">{this.state.errors.medication.ingredient}</span>}

                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Side effects</label>
                    <div className="col-sm-6 mb-2">
                        <select multiple={true}  value={this.state.medication.sideEffects} onChange={(e) => { this.handleInputChange(e)} }  >
                            {sideEffectsTag}
                        </select>
                        { this.state.submitted && this.state.errors.medication.sideEffects.length > 0 &&  <span className="text-danger">{this.state.errors.medication.sideEffects}</span>}
                    </div>
                    <div className="col-sm-4">
                    </div>
                </div>

                <div className="row"style={{marginTop: '1rem', marginLeft:'20rem'}}>
                    <label  className="col-sm-2 col-form-label">Alternatives</label>
                    <div className="col-sm-6 mb-2">
                        <select multiple={true}  value={this.state.medication.alternatives} onChange={(e) => { this.handleInputChange(e)} }  >
                            {alternativesTag}
                        </select>
                        { this.state.submitted && this.state.errors.medication.alternatives.length > 0 &&  <span className="text-danger">{this.state.errors.medication.alternatives}</span>}
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
import React from "react";
import {Button, Col, Modal, Row} from "react-bootstrap";
import DatePicker from "react-datepicker";

export default class ChooseTherapy extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal : false
        }
    }

    render() {
        const Drugs = this.props.medications.map((medication, index) =>
            <option value={medication.name} key={index}> {medication.name} </option>
        );
        return(
            <div>
                <Row>
                    <Col xs={2}> <p> Choose period: </p> </Col>
                    <Col xs={-4}> <DatePicker selected={this.props.dateStartTherapy}  name="dateStartTherapy" minDate={new Date()} onChange={this.setStartDate} /> </Col>
                    <Col xs={-4}> <DatePicker selected={this.props.dateEndTherapy} name="dateEndTherapy"   minDate={new Date()} onChange={this.setEndDate} /> </Col>
                </Row>

                <br/>
                <select value={this.props.medication.name}  onChange={this.chooseMedication}>
                    <option disabled> Choose medication ...</option>
                    {Drugs}
                </select>
                <br/>
                {this.props.medication.Id !== undefined ?
                    <div style={{ display : "flex"}}>
                        <div  className="m-2 bg-primary p-2" style={{ height: 45 , width : 200}}>
                            <Button onClick={this.removeMedication} variant="primary" className="mr-3 p-0" style={{width: '1rem'}} >X</Button>
                            <label  className='text-light'>{this.props.medication.name}</label>
                        </div>
                        <Button style={{width: 120, height : 40, marginTop : 10}} onClick={this.handleModal} variant="secondary"> Information </Button>
                        <Button style={{width: 120, height : 40, marginTop : 10, marginLeft : 2}} variant="secondary"> Prescribe </Button>
                    </div>
                    : <div>Nema leka</div>}
                {this.showModalDialog()}
                <br/>
            </div>
        )
    }

    showModalDialog = () => {
        if(this.props.medication.Id !== undefined) {
             const Ingredients = this.props.medication.ingredient.map((ingredient, key) =>
                <label style={{marginLeft: 5}}>{ingredient.name}</label>
            )
            const SideEffect = this.props.medication.sideEffect.map((effect, key) =>
                <label style={{marginLeft: 5}}>{effect.name}</label>
            )
            return (
                <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header>
                        <Modal.Title>{this.props.medication.name}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.props.medication.type !== undefined &&  <label> Medication type : {this.props.medication.type}</label> }<br/>
                        {this.props.medication.dose !== undefined && <label> Dose : {this.props.medication.dose}</label> } <br/>
                        {this.props.medication.medicationShape !== undefined &&  <label> Medication shape : {this.props.medication.medicationShape}</label> } <br/>
                        {this.props.medication.manufacturer !== undefined && <label> Manufacturer : {this.props.medication.manufacturer}</label> }<br/>
                        {this.props.medication.medicationIssue !== undefined && <label> Medication issue : {this.props.medication.medicationIssue}</label> }<br/>
                        {this.props.medication.note !== undefined && <label> Note : {this.props.medication.note}</label> }<br/>
                        <label> Ingredients : </label> {Ingredients} <br/>
                        <label> Side effects : </label> {SideEffect}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            )
        }
    }

    setStartDate = (date) => {
        this.props.setStartDate(date);
    }
    setEndDate = (date) => {
        this.props.setEndDate(date);
    }
    handleModal = () => {
        this.setState({
                showModal: !this.state.showModal
            }
        )
    }
    chooseMedication = (event) => {
        this.props.chooseMedication(event);
    }

    removeMedication = () => {
        this.props.removeMedication();
    }
}
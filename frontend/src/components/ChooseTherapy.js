import React from "react";
import {Button, Col, Modal, Row} from "react-bootstrap";
import DatePicker from "react-datepicker";
import MedicationSpecification from "./MedicationSpecification";

export default class ChooseTherapy extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal : false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
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
                {this.props.medication.id !== undefined ?
                    <div style={{ display : "flex"}}>
                        <div  className="m-2 bg-primary p-2" style={{ height: 45 ,'borderRadius' : 5, width : 200}}>
                            <Button onClick={this.removeMedication} variant="primary" className="mr-3 p-0" style={{width: '1rem'}} >x</Button>
                            <label  className='text-light'>{this.props.medication.name}</label>
                        </div>
                        <Button style={{height : 40, marginTop : 10}} onClick={this.handleModal} variant="secondary"> Information </Button>
                        <Button style={{width: 120, height : 40, marginTop : 10, marginLeft : 2}} onClick={this.createEPrescription} variant="secondary"> Prescribe </Button>
                    </div>
                    : <div>--</div>}
                {this.showModalDialog()}
                <br/>
            </div>
        )
    }

    createEPrescription = () => {

        this.props.createEPrescription();
    }
    showModalDialog = () => {
        if(this.props.medication.id !== undefined) {
             const Ingredients = this.props.medication.ingredient.map((ingredient, key) =>
                <label style={{marginLeft: 5}}>{ingredient.name}</label>
            )
            const SideEffect = this.props.medication.sideEffect.map((effect, key) =>
                <label style={{marginLeft: 5}}>{effect.name}</label>
            )
            const Alternatives = this.props.medication.alternatives.map((alt, key) =>
                <label style={{marginLeft: 5}}>{alt.name}</label>
            )
            return (
                <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header  style={{'background':'gray'}}>
                        <Modal.Title>{this.props.medication.name}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body  style={{'background':'gray'}}>
                        <MedicationSpecification medication={this.props.medication} />
                    </Modal.Body>
                    <Modal.Footer  style={{'background':'gray'}}>
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
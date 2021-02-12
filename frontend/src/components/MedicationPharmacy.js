import React from "react";
import {Button, Col, FormControl, Row, Table, Modal} from "react-bootstrap";
import axios from "axios";
import MedicationSearch from "./MedicationSearch";
import DateTimePicker from 'react-datetime-picker';
import NumericInput from 'react-numeric-input';
import helpers from "./../helpers/AuthentificationService"
import HelperService from './../helpers/HelperService'


class MedicationPharmacy extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            pharmacy : [],
            showModal : false,
            selectedPharmacy : null,
            quantity : 1,
            totalPice : 0,
            pickUpDate : new Date(),
            isDiscounted : false
        }
    }

    async componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        await axios
            .get(HelperService.getPath('/api/pharmacy/getPharmacyByMedication/' + this.props.medication.id) /*, {
                headers : {
                    'Content-Type' : 'application/json',
                    Authorization : 'Bearer ' + this.aut.jwtToken 
                }
            }*/)
            .then((res) => {
                this.setState({
                    pharmacy : res.data
                })
                //console.log(this.state.pharmacy);
            });

    }

    render() {
        return (
            <div>
                <h4 >Pharmacies</h4>
                {this.state.pharmacy.length !=0 ?
                    <Table  hover variant="dark">
                        <thead style={{'color':'dimgrey '}}>
                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Price</th>
                            <th>{' '}</th>
                        </tr>
                        </thead>
                        {this.state.pharmacy.map((pharmacy, index) => (
                        <tbody styles={{'overflow':'scroll', 'display': 'block','height': '300px'}}>
                            <tr>
                                <td >{pharmacy.name}</td>
                                <td >{pharmacy.address.country} {pharmacy.address.town} {pharmacy.address.street}</td>
                                <td>{pharmacy.medicationPrice}</td>
                                <td>{helpers.isLoggedIn() && <Button variant={'outline-light'} onClick={() => this.handleModal(pharmacy)}>Reserve</Button>}</td>
                            </tr>
                        </tbody>
                        ))}
                    </Table>
                    :
                    <div>Medication isn't available</div>}

                    <Modal show={this.state.showModal} onHide={this.closeModal}  >
                    <Modal.Header closeButton>
                        <Modal.Title>Make a reservation</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <label>Enter a picku up date</label>
                        <DateTimePicker className="form-control"
                            onChange={(value, e) => this.handleDateChange(value, e)}
                            value={this.state.pickUpDate}
                            format={'dd.MM.y'} disableClock={true}
                            minDate={new Date()}
                        /><br/>
                        <label>Enter medication quantity (you can reserve up to 20)</label>
                        <NumericInput className="form-control" onChange={this.handleInputChange}
                        min={1} max={20} value={this.state.quantity} strict={true}/><br/>
                        {this.state.isDiscounted &&
                            <label><b>Total price : <span style={{'color' : 'red'}}>${this.state.totalPrice / 2} </span><br/>
                                <span style={{'color' : 'blue'}}>*half a price because you are subscribed to a promotion</span></b></label>}

                        {!this.state.isDiscounted && <label><b>Total price :
                            <span style={{'color' : 'red'}}>${this.state.totalPrice}</span></b></label>}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="dark" onClick={this.reserve}>
                            Confirm
                        </Button>
                        <Button variant="outline-secondary" onClick={this.closeModal}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>  
        )
    }

    handleDateChange = (value, e) => {
        this.setState({
            pickUpDate : value
        })
    }

    handleInputChange = async (value) => {
        await this.setState({
            quantity : value,
        })

        await this.setState({
            totalPrice : this.calculateTotalPrice()
        })
    }

    handleModal = async (pharmacy) => {
        await this.setState({
            showModal : true,
            selectedPharmacy : pharmacy,
        })

        await this.setState({
            totalPrice : this.calculateTotalPrice()
        })

        this.checkIfPatientHasPromotion();
    }

    closeModal = () => {
        this.setState({
            showModal : false,
            quantity : 1
        })
    }

    calculateTotalPrice = () => {
        return this.state.selectedPharmacy.medicationPrice * this.state.quantity
    }

    reserve = () => {
        const {selectedPharmacy, quantity, pickUpDate} = this.state
        axios
        .put(HelperService.getPath('/api/medicationReservation/reserve'), {
            'pharmacyId' : selectedPharmacy.id,
            'medicationReservation' : {
                'discounted' : this.state.isDiscounted,
                'medicationQuantity' : {
                    'quantity' : quantity,
                    'medication' : this.props.medication
                },
                'patient' : {
                    'id' : this.aut.id
                },
                'status' : 'requested',
                'pickUpDate' : this.getDate()
            }
        }, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        }).
        then(res => {
            axios
            .put(HelperService.getPath('/api/email/send'), {
                'to': 'ilija_brdar@yahoo.com',   
                'subject':"Medication reserved!",
                'body':'You have successfully reserved medication. Present this ID to your pharmacist: ' + res.data
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.aut.jwtToken
                }
            })
            .then(res => {
                alert('success')
            });
        })
        .catch(e => alert('Error: Not enough medication.'))
    }

    getDate = () => {
        const dateTime = this.state.pickUpDate
        const year = dateTime.getFullYear()
        const month = this.extendDate(dateTime.getMonth() + 1)
        const day = this.extendDate(dateTime.getDate())

        return `${year}-${month}-${day} 00:00:00`
    }

    extendDate = (component) => {
        return (component < 10) ? '0' + component : component
    }

    checkIfPatientHasPromotion = () => { //todo change patient id dynamically
        const path = HelperService.getPath("/api/promotion/checkPatientSubscribedToPromotion/" +
            this.state.selectedPharmacy.id + "/" + this.aut.id + "/" + this.props.medication.id);
        axios.get(path, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
            .then((res) => {
                this.setState({
                    isDiscounted : res.data
                });
            })
    }

}

export default MedicationPharmacy
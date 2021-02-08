import React from 'react';
import {Button, Col, Form, FormControl, Modal} from "react-bootstrap";
import axios from "axios";
import moment from "moment";
import DatePicker from "react-datepicker";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";

export default class PriceHistory extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            priceLists : [],
            userType : 'pharmacyAdmin',
            medication : this.props.priceListingHistory,
            showAddPriceListModal : false,
            addPriceList : {
                cost : "",
                period : {
                    periodStart : "",
                    periodEnd : ""
                },
                medicationId : this.props.priceListingHistory.medicationId,
                pharmacyId : this.props.priceListingHistory.pharmacyId
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp
        })
        this.fetchHistoryPriceLists();
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Price History</h1>
                <Button variant={"outline-info"} onClick={() => this.props.showCurrentPriceLists("showCurrentPriceLists")}>{"←"}</Button>
                <br/><br/>
                <div className="col-md-12">
                    <div className="card">
                        <h5 className="card-header">
                            Medication
                        </h5>

                        <div className="card-body">
                            <p className="card-text">
                                Name : {this.state.medication.medicationName}
                                <Button style={{marginLeft:'5rem'}} variant={"outline-success"} onClick={this.handleModal}>{"new price period"}</Button>
                                <br/>
                                Current price : {this.state.medication.cost}
                                <br/>
                                Period start : {moment(this.state.medication.period.periodStart).format('DD.MM.YYYY')}
                                <br/>
                                Period start : {moment(this.state.medication.period.periodEnd).format('DD.MM.YYYY')}
                                <br/>
                            </p>
                        </div>
                    </div>
                </div>
                <br/><br/>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Medication name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Start</th>
                        <th scope="col">End</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.priceLists.map((priceList, index) => (
                        <tr key={index}>
                            <th scope="row">{index+1}</th>
                            <td>{priceList.medicationName}</td>
                            <td>{priceList.cost}</td>
                            <td>{moment(priceList.period.periodStart).format('DD.MM.YYYY')}</td>
                            <td>{moment(priceList.period.periodEnd).format('DD.MM.YYYY')}</td>


                        </tr>
                    ))}
                    </tbody>
                </table>

                <Modal show={this.state.showAddPriceListModal} onHide={this.handleModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Add price period</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form style={{marginLeft : 2}}>
                            <Form.Row>
                                <Col>
                                    <label>Medication name : {this.state.medication.medicationName}</label>
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Price</label>
                                </Col>
                                <Col>
                                    <FormControl type="text" value={this.state.addPriceList.cost} onChange={this.changeCost}/>
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Period start</label>
                                </Col>
                                <Col>
                                    <DatePicker selected={this.state.addPriceList.period.periodStart} dateFormat="dd MMMM yyyy"  name="priceDateStart" minDate={new Date()} onChange={this.setPriceDateStart} />
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <Col>
                                    <label>Period end</label>
                                </Col>
                                <Col>
                                    <DatePicker selected={this.state.addPriceList.period.periodEnd} dateFormat="dd MMMM yyyy"  name="priceDateEnd" minDate={new Date()} onChange={this.setPriceDateEnd} />
                                </Col>
                            </Form.Row>
                            <br/>
                            <Form.Row>
                                <label style={{marginLeft : '1rem', marginRight : '1rem'}}><span style={{color : 'palevioletred'}}>* if there is a gap in price periods the default price of medication will be 400</span></label>
                            </Form.Row>
                        </Form>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.closeModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.submitAddPriceList}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }

    changeCost = (event) => {
        this.setState({
            addPriceList : {
                ...this.state.addPriceList,
                cost : event.target.value
            }
        })
    }

    fetchHistoryPriceLists = () => {
        axios
            .get(HelperService.getPath("/api/pricelist/getMedicationPriceListHistoryByPharmacy/" + this.state.pharmacyId + "/" + this.state.medication.medicationId), {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    priceLists : res.data
                })
            });
    }

    handleModal = () => {
        this.setState({
            showAddPriceListModal : !this.state.showAddPriceListModal
        });
    }

    setPriceDateStart = (date) => {
        let addPriceList = this.state.addPriceList;
        addPriceList.period.periodStart = date;
        this.setState({
            addPriceList : addPriceList
        })
    }
    setPriceDateEnd = (date) => {
        let addPriceList = this.state.addPriceList;
        addPriceList.period.periodEnd = date;
        this.setState({
            addPriceList : addPriceList
        })
    }

    convertDates = (periodDate) => {
        return moment(periodDate).format('YYYY-MM-DD');
    }

    submitAddPriceList = () => {
        let temp = this.state.addPriceList;
        temp.period.periodStart = this.convertDates(temp.period.periodStart) + " 12:00:00";
        temp.period.periodEnd = this.convertDates(temp.period.periodEnd) + " 12:00:00";
        console.log(temp);

        if (parseInt(this.state.addPriceList.cost) < 0) {
            alert("Price has to be positive number!");
            return;
        }

        axios.put(HelperService.getPath("/api/pricelist/newPriceList"), temp, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                this.successfulAdd();
            })
            .catch(() => {
                this.unsuccessfulAdd();
            })
    }

    successfulAdd = () => {
        alert("New price list added successfully!");
        axios
            .get(HelperService.getPath("/api/pricelist/getMedicationPriceListHistoryByPharmacy/" + this.state.pharmacyId + "/" + this.state.medication.medicationId), {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    priceLists : res.data,
                    addPriceList : {
                        cost : "",
                        period : {
                            periodStart : "",
                            periodEnd : ""
                        },
                        medicationId : this.props.priceListingHistory.medicationId,
                        pharmacyId : this.props.priceListingHistory.pharmacyId
                    },
                    showAddPriceListModal : !this.state.showAddPriceListModal
                })
        });
    }

    unsuccessfulAdd = () => {
        alert("New price list was not added successfully!");
        this.setState({
            addPriceList : {
                cost : "",
                period : {
                    periodStart : "",
                    periodEnd : ""
                },
                medicationId : this.props.priceListingHistory.medicationId,
                pharmacyId : this.props.priceListingHistory.pharmacyId
            },
            showAddPriceListModal : !this.state.showAddPriceListModal
        })
    }

    closeModal = () => {
        this.setState({
            addPriceList : {
                cost : "",
                period : {
                    periodStart : "",
                    periodEnd : ""
                },
                medicationId : this.props.priceListingHistory.medicationId,
                pharmacyId : this.props.priceListingHistory.pharmacyId
            },
            showAddPriceListModal : !this.state.showAddPriceListModal
        })
    }
}
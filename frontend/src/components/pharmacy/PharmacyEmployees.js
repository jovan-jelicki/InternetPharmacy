import React from 'react';
import {Button, Col} from "react-bootstrap";
import {Modal} from "react-bootstrap";
import {Navbar} from "react-bootstrap";
import {Form} from "react-bootstrap";
import {FormControl, Row} from "react-bootstrap";
import moment from 'moment';
import TimePicker from 'react-time-picker';
import Registration from "../../pages/Registration";
import CreatePharmacist from "./CreatePharmacist";
import axios from "axios";




export default class PharmacyEmployees extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            dermatologists: [],
            pharmacists: [],
            showModalAddDermatologist : false,
            showModalCreatePharmacist : false,
            userType : "pharmacyAdmin",
            searchPharmacist : {
                firstName : '',
                lastName : ''
            },
            backupPharmacists : [],
            backupDermatologists : [],
            notWorkingDermatologists : [],
            dermatologistForAdding : {
                id : 0,
                workingHours : []
            },
            workingHours : {
                period : {
                    periodStart : "",
                    periodEnd : ""
                },
                pharmacy : {
                    id : 1 //todo change pharmacy ID
                }
            }
        }
    }
// definise slobodne termine,pretražuje, kreira i uklanja farmaceute/dermatologe
    async componentDidMount() {
        let dermatologists = [
            {
                firstName : "Mirko",
                lastName : "Jugovic",
                grade : 5,
            },
            {
                firstName : "Maja",
                lastName : "Jugovic",
                grade : 3,
            }
        ];
        let pharmacists = [
            {
                firstName : "Jelena",
                lastName : "Matic",
                grade : 3.25,
            },
            {
                firstName : "Maja",
                lastName : "Berovic",
                grade : 4.1,
            }
        ];

        await this.fetchPharmacists();

        await this.fetchDermatologists();

        console.log(this.state.pharmacists);
        this.fetchDermatologistNotWorkingInThisPharmacy();
    }

    render() {
        const format = "HH:mm"
        return (
           <div style={({ marginLeft: '1rem' })}>
               <br/><br/>
               <h1>Dermatolozi</h1>
               
               <Button variant="success" onClick={this.openModalAddDermatologist}>Dodaj dermatologa</Button>
               <br/><br/>

               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" />
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" />
                           <Button variant="outline-success">Search</Button>
                       </Form>
                   </Navbar.Collapse>
               </Navbar>

               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">Ime</th>
                       <th scope="col">Prezime</th>
                       <th scope="col">Ocena</th>
                   </tr>
                   </thead>
                   <tbody>
               {this.state.dermatologists.map((dermatologist, index) => (
                   <tr>
                       <th scope="row">{index+1}</th>
                       <td>{dermatologist.firstName}</td>
                       <td>{dermatologist.lastName}</td>
                       <td>{dermatologist.grade}</td>
                       <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="primary" onClick={this.handleModalAddDermatologist}>
                                Zakazi pregled
                           </Button>
                       </td >
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="warning" onClick={this.handleModalAddDermatologist}>
                               Definisi slobodne termine
                           </Button>
                       </td>
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="danger" onClick={() => this.deleteDermatologist(dermatologist)}>
                               Izbrisi dermatologa
                           </Button>
                       </td>
                   </tr>
               ))}
                   </tbody>
               </table>


               <br/><br/>
               <h1>Farmaceuti</h1>
               <Button variant="success" onClick={this.handleModalCreatePharmacist}>Kreiraj farmaceuta</Button>
               <br/><br/>
               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" name={'firstName'} value={this.state.searchPharmacist.firstName} onChange={this.changeSearchPharmacist}/>
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" name={'lastName'} value={this.state.searchPharmacist.lastName} onChange={this.changeSearchPharmacist}/>
                           <Button variant="outline-success" onClick={this.searchPharmacist}>Search</Button>
                           <Button variant="outline-info" onClick={this.resetSearchPharmacist}>Reset</Button>

                       </Form>
                   </Navbar.Collapse>
               </Navbar>
               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">Ime</th>
                       <th scope="col">Prezime</th>
                       <th scope="col">Ocena</th>
                       <th scope="col">Pocetak smene</th>
                       <th scope="col">Kraj smene</th>

                   </tr>
                   </thead>
                   <tbody>
                   {this.state.pharmacists.map((pharmacist, index) => (
                       <tr>
                           <th scope="row">{index+1}</th>
                           <td>{pharmacist.firstName}</td>
                           <td>{pharmacist.lastName}</td>
                           <td>{pharmacist.grade}</td>
                           <td>{pharmacist.workingHours.period.periodStart}</td>
                           <td>{pharmacist.workingHours.period.periodEnd}</td>
                           <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="primary" onClick={this.handleModalAddDermatologist}>
                                   Zakazi savetovanje
                               </Button>
                           </td >
                           <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="warning" onClick={this.handleModalAddDermatologist}>
                                   Definisi slobodne termine
                               </Button>
                           </td>
                           <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="danger" onClick={() => this.deletePharmacist(pharmacist)}>
                                   Izbrisi farmaceuta
                               </Button>
                           </td>
                       </tr>
                   ))}
                   </tbody>
               </table>

               {this.renderModalAddDermatologist()}
               {this.renderModalCreatePharmacist()}

           </div>
        );
    }
// <select  name="medication" onChange={this.handleInputChange} value={this.state.inputOrder.medication}>
// <option disabled>select medication</option>
// {this.props.medications.map((medication) => <option key={medication.id} value={medication.name}>{medication.name}</option>)}
// </select>

    handleDermatologistForAddingChange = async (event) => {
        const target = event.target;
        let value = event.target.value;

        const path = "http://localhost:8080/api/dermatologists/" + value;
        await axios.get(path).then(res => {
            this.setState({
                dermatologistForAdding : res.data
            });

        })

        console.log(this.state.dermatologistForAdding);


    }
    renderModalAddDermatologist = () => {
        return (
            <Modal show={this.state.showModalAddDermatologist} onHide={this.handleModalAddDermatologist}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Dermatologist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Row>
                            <Col>
                                <Form.Control placeholder="Dermatologist" as={"select"} value={this.state.dermatologistForAdding.id} onChange={this.handleDermatologistForAddingChange}>
                                    <option disabled={true} selected="selected">Choose</option>
                                    {this.state.notWorkingDermatologists.map(dermatologist =>
                                    <option key={dermatologist.id} value={dermatologist.id}>{dermatologist.firstName + " " + dermatologist.lastName}</option>
                                    )}
                                </Form.Control>
                            </Col>
                        </Form.Row>
                        <br/>
                        <Form.Row>
                            <div style={({ marginLeft: '1rem' })}>
                                <label style={({ marginRight: '1rem' })}>Select start of work time : </label>
                                <TimePicker  name="periodStart" value={this.state.workingHours.period.periodStart} onChange={this.setPeriodStart}/>
                            </div>
                        </Form.Row>
                        <br/>
                        <Form.Row>
                            <div style={({ marginLeft: '1rem' })}>
                                <label style={({ marginRight: '1rem' })}>Select end of work time : </label>
                                <TimePicker  name="periodEnd" value={this.state.workingHours.period.periodEnd} onChange={this.setPeriodEnd}/>
                            </div>
                        </Form.Row>

                        <div style={this.state.dermatologistForAdding.workingHours.length !== 0 ? {display : 'block'} : {display : 'none'}}>
                            <br/>
                            <h3>Work time in other pharmacies</h3>
                            <table class="table table-sm">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Start</th>
                                    <th scope="col">End</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>{"11:00"}</td>
                                    <td>{"13:00"}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleModalAddDermatologist}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={this.addDermatologist}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

    openModalAddDermatologist = async () => {
        await this.fetchDermatologistNotWorkingInThisPharmacy();
        if (this.state.notWorkingDermatologists.length !== 0)
            this.handleModalAddDermatologist();
        else
            alert("All available dermatologists are already working in this pharmacy!");
    }

    addDermatologist = async () => {
        let finalDermatologist = this.state.dermatologistForAdding;
        let workingHours = this.state.workingHours;
        workingHours.period.periodStart = '2017-01-13 ' + workingHours.period.periodStart + ":00";
        workingHours.period.periodEnd = '2017-01-13 ' + workingHours.period.periodEnd + ":00";
        finalDermatologist.workingHours.push(this.state.workingHours);
        console.log(finalDermatologist);
        axios.put("http://localhost:8080/api/dermatologists", finalDermatologist).then(() => {
                this.setState({
                    dermatologistForAdding : {
                        id : 0,
                        workingHours : []
                    }
                });
                this.handleModalAddDermatologist();
                this.fetchDermatologists();
            }
        )
    }

    renderModalCreatePharmacist = () => {
        return (
            <Modal show={this.state.showModalCreatePharmacist} onHide={this.handleModalCreatePharmacist}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Pharmacist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreatePharmacist closeModal = {this.handleModalCreatePharmacist} fetchPharmacists = {this.fetchPharmacists}/>
                </Modal.Body>
            </Modal>
        );
    }

    handleModalAddDermatologist = () => {
        this.setState({
            showModalAddDermatologist : !this.state.showModalAddDermatologist
        });
    }

    handleModalCreatePharmacist = () => {
        this.setState({
            showModalCreatePharmacist : !this.state.showModalCreatePharmacist
        });
    }

    deleteDermatologist = (dermatologist) => {
        let isBoss = window.confirm('Are you sure you want to delete ' + dermatologist.firstName + ' ' + dermatologist.lastName + ' from your employees list?');
        alert( isBoss ); // true if OK is pressed
    }

    deletePharmacist = (dermatologist) => {
        let isBoss = window.confirm('Are you sure you want to delete ' + dermatologist.firstName + ' ' + dermatologist.lastName + ' from your employees list?');
        alert( isBoss ); // true if OK is pressed
    }

    fetchPharmacists = async () => {
        axios
            .get('http://localhost:8080/api/pharmacist/getByPharmacy/1') //todo change pharmacy id
            .then(res => {
                this.setState({
                    pharmacists : res.data,
                    backupPharmacists : res.data
                })
            });
    }

    fetchDermatologists = async () => {
        axios
            .get('http://localhost:8080/api/dermatologists/getAllDermatologistWorkingInPharmacy/1')//todo change pharmacy ID
            .then(res => {
                this.setState({
                    dermatologists : res.data,
                    backupDermatologists : res.data
                })
            });
    }

    changeSearchPharmacist = (event) => {
        const { name, value } = event.target;
        const searchPharmacist = this.state.searchPharmacist;
        searchPharmacist[name] = value;

        this.setState({ searchPharmacist });
    }
    resetSearchPharmacist = () => {
        this.setState({
            searchPharmacist : {
                firstName : '',
                lastName : ''
            },
            pharmacists : this.state.backupPharmacists
        })
    }

    searchPharmacist = async () => {
        let filterPharmacists = await this.state.pharmacists.filter(pharmacist => {
            if (this.state.searchPharmacist.firstName !== '' && this.state.searchPharmacist.lastName !== '')
                return pharmacist.firstName.includes(this.state.searchPharmacist.firstName) && pharmacist.lastName.includes(this.state.searchPharmacist.lastName);
            else if (this.state.searchPharmacist.firstName !== '')
                return pharmacist.firstName.includes(this.state.searchPharmacist.firstName);
            else if (this.state.searchPharmacist.lastName !== '')
                return pharmacist.lastName.includes(this.state.searchPharmacist.lastName);
            return true;
        });
        this.setState({
            pharmacists : filterPharmacists
        })
    }

    fetchDermatologistNotWorkingInThisPharmacy = async () => {
        await axios.get("http://localhost:8080/api/dermatologists/getAllDermatologistNotWorkingInPharmacy/1").then( //todo change pharmacy id
            res => {
                this.setState({
                    notWorkingDermatologists : res.data,

                })
            }
        )
        if (this.state.notWorkingDermatologists.length !== 0)
            this.setState({
                dermatologistForAdding : this.state.notWorkingDermatologists[0],
                workingHours : {
                    period : {
                        periodStart : "",
                        periodEnd : ""
                    },
                    pharmacy : {
                        id : 1 //todo change pharmacy ID
                    }
                }
            })
        else {
            this.setState({
                dermatologistForAdding : {
                    id : 0,
                    workingHours : []
                },
                workingHours : {
                    period : {
                        periodStart : "",
                        periodEnd : ""
                    },
                    pharmacy : {
                        id : 1 //todo change pharmacy ID
                    }
                }
            })
        }
    }

    setPeriodStart = (date) => {
        const workingHours = this.state.workingHours;
        workingHours.period.periodStart = date;
        this.setState({
            workingHours : workingHours
        })
    }

    setPeriodEnd = (date) => {
        const workingHours = this.state.workingHours;
        workingHours.period.periodEnd = date;
        this.setState({
            workingHours : workingHours
        })
    }
}
import React from 'react';
import {Button, Col} from "react-bootstrap";
import {Modal} from "react-bootstrap";
import {Navbar} from "react-bootstrap";
import {Form} from "react-bootstrap";
import {FormControl, Row} from "react-bootstrap";
import moment from 'moment';
import TimePicker from 'react-time-picker';
import Registration from "../../pages/Registration";
import CreatePharmacistModal from "./CreatePharmacistModal";
import axios from "axios";
import AddAppointmentModal from "./AddAppointmentModal";
import Dropdown from "react-dropdown";
import StarRatings from "react-star-ratings";


const options = [
    'Any', '1 - 2', '2 - 3', '3 - 4', ' > 4'
];
const defaultOption = options[0];

export default class PharmacyEmployees extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            dermatologists: [],
            pharmacists: [],
            showModalAddDermatologist : false,
            showModalCreatePharmacist : false,
            showModalAddAppointment : false,
            userType : "pharmacyAdmin",
            dermatologistModalAddAppointment : {},
            searchPharmacist : {
                firstName : '',
                lastName : '',
                filterGradesPharmacist : options[0]
            },
            searchDermatologist : {
                firstName : '',
                lastName : '',
                filterGradesDermatologist : options[0]
            },
            backupPharmacists : [],
            backupDermatologists : [],
            notWorkingDermatologists : [],
            dermatologistForAdding : {
                workingHours : [
                    {
                        id : -1,
                        period : {
                            periodStart : "",
                            periodEnd : ""
                        }
                    }
                ]
            },
            workingHours : {
                period : {
                    periodStart : "",
                    periodEnd : ""
                },
                pharmacy : {
                    id : 1 //todo change pharmacy ID
                }
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        await this.fetchPharmacyId();
        await this.fetchPharmacists();
        await this.fetchWorkingDermatologists();
    }

    render() {
        return (
           <div style={({ marginLeft: '1rem' })}>
               <br/><br/>
               <h1>Dermatologists</h1>
               
               <Button variant="success" onClick={this.openModalAddDermatologist}>Add dermatologist</Button>
               <br/><br/>

               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" name={'firstName'} value={this.state.searchDermatologist.firstName} onChange={this.changeSearchDermatologist}/>
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" name={'lastName'} value={this.state.searchDermatologist.lastName} onChange={this.changeSearchDermatologist}/>
                           <label style={{marginRight : '1rem'}}>Filter by grade : </label>
                           <select  name="filterGradesDermatologist" onChange={this.handleFilterGradesDermatologistChange} value={this.state.searchDermatologist.filterGradesDermatologist}>
                               {options.map((option,index) => <option key={index} value={option}>{option}</option>)}
                           </select>
                           <Button variant="outline-success" onClick={this.searchDermatologist} style={{marginLeft : '1rem'}}>Search</Button>
                           <Button variant="outline-info" onClick={this.resetSearchDermatologist} style={{marginLeft : '1rem'}}>Reset</Button>
                       </Form>
                   </Navbar.Collapse>
               </Navbar>

               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">First name</th>
                       <th scope="col">Last name</th>
                       <th scope="col">Grade</th>
                       <th scope="col">Working hours</th>
                   </tr>
                   </thead>
                   <tbody>
               {this.state.dermatologists.map((dermatologist, index) => (
                   <tr key={index}>
                       <th scope="row">{index+1}</th>
                       <td>{dermatologist.firstName}</td>
                       <td>{dermatologist.lastName}</td>
                       <td>
                           <StarRatings
                               starDimension={'25px'}
                               rating={dermatologist.grade}
                               starRatedColor='gold'
                               numberOfStars={5}
                           />
                       </td>
                       <td>{moment(dermatologist.workingHours.filter(workingHour => workingHour.pharmacy.id === 1)[0].period.periodStart).format('hh:mm a')
                        + "  -  " + moment(dermatologist.workingHours.filter(workingHour => workingHour.pharmacy.id === 1)[0].period.periodEnd).format('hh:mm a')}</td>

                       <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="primary" onClick={this.openModalAddDermatologist}>
                                Schedule appointment
                           </Button>
                       </td >
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="warning" onClick={(e) => this.handleModalAddAppointment(dermatologist)}>
                               Define available appointments
                           </Button>
                       </td>
                       <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                           <Button variant="danger" onClick={() => this.deleteDermatologist(dermatologist)}>
                               Delete dermatologist
                           </Button>
                       </td>
                   </tr>
               ))}
                   </tbody>
               </table>


               <br/><br/>
               <h1>Pharmacists</h1>
               <Button variant="success" onClick={this.handleModalCreatePharmacist}>Create pharmacist</Button>
               <br/><br/>
               <Navbar bg="light" expand="lg">
                   <Navbar.Toggle aria-controls="basic-navbar-nav" />
                   <Navbar.Collapse id="basic-navbar-nav">
                       <Form inline>
                           <FormControl type="text" placeholder="Search by first name" className="mr-sm-2" name={'firstName'} value={this.state.searchPharmacist.firstName} onChange={this.changeSearchPharmacist}/>
                           <FormControl type="text" placeholder="Search by last name" className="mr-sm-2" name={'lastName'} value={this.state.searchPharmacist.lastName} onChange={this.changeSearchPharmacist}/>
                           <label style={{marginRight : '1rem'}}>Filter by grade : </label>
                           <select  name="filterGradesPharmacist" onChange={this.handleFilterGradesChange} value={this.state.searchPharmacist.filterGradesPharmacist}>
                               {options.map((option,index) => <option key={index} value={option}>{option}</option>)}
                           </select>
                           <Button variant="outline-success" onClick={this.searchPharmacist} style={{marginLeft : '1rem'}}>Search</Button>
                           <Button variant="outline-info" onClick={this.resetSearchPharmacist} style={{marginLeft : '1rem'}}>Reset</Button>

                       </Form>
                   </Navbar.Collapse>
               </Navbar>
               <table className="table table-hover">
                   <thead>
                   <tr>
                       <th scope="col">#</th>
                       <th scope="col">First name</th>
                       <th scope="col">Last name</th>
                       <th scope="col">Grade</th>
                       <th scope="col">Working hours</th>
                   </tr>
                   </thead>
                   <tbody>
                   {this.state.pharmacists.map((pharmacist, index) => (
                       <tr key={index}>
                           <th scope="row">{index+1}</th>
                           <td>{pharmacist.firstName}</td>
                           <td>{pharmacist.lastName}</td>
                           <td>
                               <StarRatings
                                   starDimension={'25px'}
                                   rating={pharmacist.grade}
                                   starRatedColor='gold'
                                   numberOfStars={5}
                               />
                           </td>
                           <td>{moment(pharmacist.workingHours.period.periodStart).format('hh:mm a') + "  -  " +
                            moment(pharmacist.workingHours.period.periodEnd).format('hh:mm a')}</td>
                           <td style={this.state.userType === 'patient' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="primary" onClick={this.handleModalAddDermatologist}>
                                   Schedule counseling
                               </Button>
                           </td >
                           <td style={this.state.userType === 'pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}}>
                               <Button variant="danger" onClick={() => this.deletePharmacist(pharmacist)}>
                                   Delete pharmacist
                               </Button>
                           </td>
                       </tr>
                   ))}
                   </tbody>
               </table>

               {this.renderModalAddDermatologist()}
               {this.renderModalCreatePharmacist()}
               {this.renderModalAddAppointment()}

           </div>
        );
    }
// <select  name="medication" onChange={this.handleInputChange} value={this.state.inputOrder.medication}>
// <option disabled>select medication</option>
// {this.props.medications.map((medication) => <option key={medication.id} value={medication.name}>{medication.name}</option>)}
// </select>

    handleFilterGradesDermatologistChange = (event) => {
        const target = event.target;
        let value = event.target.value;

        this.setState({
            searchDermatologist : {
                ...this.state.searchDermatologist,
                filterGradesDermatologist : value
            }
        })
    }

    handleFilterGradesChange = (event) => {
        const target = event.target;
        let value = event.target.value;

        this.setState({
            searchPharmacist : {
                ...this.state.searchPharmacist,
                filterGradesPharmacist : value
            }
        })
    }

    handleDermatologistForAddingChange = async (event) => {
        const target = event.target;
        let value = event.target.value;

        const path = "http://localhost:8080/api/dermatologists/getOneWithWorkingHours/" + value;
        await axios.get(path,
            {
                headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        }).then(res => {
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
                                <TimePicker  name="periodStart" format="h:m a" value={this.state.workingHours.period.periodStart} onChange={this.setPeriodStart}/>
                            </div>
                        </Form.Row>
                        <br/>
                        <Form.Row>
                            <div style={({ marginLeft: '1rem' })}>
                                <label style={({ marginRight: '1rem' })}>Select end of work time : </label>
                                <TimePicker  name="periodEnd" format="h:m a" value={this.state.workingHours.period.periodEnd} onChange={this.setPeriodEnd}/>
                            </div>
                        </Form.Row>

                        <div style={this.state.dermatologistForAdding.workingHours  !== undefined  ? {display : 'block'} : {display : 'none'}}>
                            <br/>
                            <h3>Work time in other pharmacies</h3>
                            <table class="table table-sm">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Working hours</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    this.state.dermatologistForAdding.workingHours.map((workingHour, index) => (
                                    <tr key={index}>
                                        <td>{index + 1}</td>
                                        <td>{moment(workingHour.period.periodStart).format('hh:mm a') + "  -  " +
                                        moment(workingHour.period.periodEnd).format('hh:mm a')}</td>
                                    </tr>
                                    ))
                                }

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

    validateWorkingHoursDifference = (startTime, endTime) => {
        startTime = moment(startTime + ":00", "HH:mm:ss a");
        endTime = moment(endTime + ":00", "HH:mm:ss a");
        let duration = moment.duration(endTime.diff(startTime));

        return Math.abs(parseInt(duration.asMinutes())) < 120;
    }

    addDermatologist = async () => {
        let finalDermatologist = this.state.dermatologistForAdding;
        let workingHours = this.state.workingHours;
        if (this.validateWorkingHoursDifference(workingHours.period.periodStart, workingHours.period.periodEnd)) {
            alert("Dermatologist should work at least 2 hours.");
            return;
        }
        workingHours.period.periodStart = '2017-01-13 ' + workingHours.period.periodStart + ":00";
        workingHours.period.periodEnd = '2017-01-13 ' + workingHours.period.periodEnd + ":00";
        finalDermatologist.workingHours.push(workingHours);
        console.log(finalDermatologist);
        await axios.put("http://localhost:8080/api/dermatologists/addDermatologistToPharmacy", finalDermatologist,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then(() => {
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
                            id : this.state.pharmacyId
                        }
                    }
                });
                alert("Dermatologist added successfully!");
                this.handleModalAddDermatologist();
                this.fetchWorkingDermatologists();
            }
        ).catch(() => {
            alert("Dermatologist was not added!");
            this.handleModalAddDermatologist();
            this.setState({
                workingHours : {
                    period : {
                        periodStart : "",
                        periodEnd : ""
                    },
                    pharmacy : {
                        id : 2 //todo change pharmacy ID
                    }
                }
            });
        })
        await this.setState({
            dermatologistForAdding : {
                workingHours : [
                    {
                        id : -1,
                        period : {
                            periodStart : "",
                            periodEnd : ""
                        }
                    }
                ]
            }
        });
    }

    renderModalCreatePharmacist = () => {
        return (
            <Modal show={this.state.showModalCreatePharmacist} onHide={this.handleModalCreatePharmacist}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Pharmacist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreatePharmacistModal closeModal = {this.handleModalCreatePharmacist} fetchPharmacists = {this.fetchPharmacists}/>
                </Modal.Body>
            </Modal>
        );
    }

    renderModalAddAppointment = () => {
        return (
            <Modal show={this.state.showModalAddAppointment} onHide={this.handleModalAddAppointment}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Appointment</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <AddAppointmentModal closeModal = {this.handleModalAddAppointment} dermatologist={this.state.dermatologistModalAddAppointment}/>
                </Modal.Body>
            </Modal>
        )
    }

    handleModalAddAppointment = (dermatologist) => {
        this.setState({
            showModalAddAppointment : !this.state.showModalAddAppointment,
            dermatologistModalAddAppointment : dermatologist
        });
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
        if (isBoss) {
            axios.put("http://localhost:8080/api/dermatologists/deleteDermatologistFromPharmacy/" + this.state.pharmacyId, dermatologist,
            {
                headers: {
                    'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
                .then(res => {
                    if (res.status === 200) {
                        alert("Dermatologist deleted successfully!");
                        this.fetchWorkingDermatologists();
                    }
                })
                .catch(() => {
                    alert("Dermatologist cannot be deleted due to scheduled appointments.");
                })
        }
    }

    deletePharmacist = (pharmacist) => {
        let isBoss = window.confirm('Are you sure you want to delete ' + pharmacist.firstName + ' ' + pharmacist.lastName + ' from your employees list?');
        if (isBoss) {
            const path = "http://localhost:8080/api/pharmacist/" + pharmacist.id;
            axios.delete(path, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then((res) => {
                if (res.status === 200) {
                    alert("Pharmacist deleted successfully!");
                    this.fetchPharmacists();
                }
            }).catch(() => {
                alert("Pharmacist cannot be deleted due to scheduled appointments.");
            })
        }
    }

    fetchPharmacists = async () => {
        axios
            .get('http://localhost:8080/api/pharmacist/getByPharmacy/' + this.state.pharmacyId,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                }) //todo change pharmacy id
            .then(res => {
                this.setState({
                    pharmacists : res.data,
                    backupPharmacists : res.data
                })
            });
    }

    fetchWorkingDermatologists = async () => {
        axios
            .get('http://localhost:8080/api/dermatologists/getAllDermatologistWorkingInPharmacy/' + this.state.pharmacyId,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })//todo change pharmacy ID
            .then(res => {
                this.setState({
                    dermatologists : res.data,
                    backupDermatologists : res.data
                })
            });
    }

    changeSearchDermatologist = (event) => {
        const { name, value } = event.target;
        const searchDermatologist = this.state.searchDermatologist;
        searchDermatologist[name] = value;

        this.setState({ searchDermatologist });
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
                lastName : '',
                filterGradesPharmacist : options[0]
            },

            pharmacists : this.state.backupPharmacists
        })
    }

    resetSearchDermatologist= () => {
        this.setState({
            searchDermatologist : {
                firstName : '',
                lastName : '',
                filterGradesDermatologist : options[0]
            },

            dermatologists : this.state.backupDermatologists
        })
    }

    searchPharmacist = async () => {
        let minRequiredGrade = 0;
        let maxRequiredGrade = 0;
        if (this.state.searchPharmacist.filterGradesPharmacist === options[1]) {
            minRequiredGrade = 1;
            maxRequiredGrade = 2;
        }
        else if (this.state.searchPharmacist.filterGradesPharmacist === options[2]) {
            minRequiredGrade = 2;
            maxRequiredGrade = 3;
        }
        else if (this.state.searchPharmacist.filterGradesPharmacist === options[3]) {
            minRequiredGrade = 3;
            maxRequiredGrade = 4;
        }
        else if (this.state.searchPharmacist.filterGradesPharmacist === options[4]) {
            minRequiredGrade = 4;
            maxRequiredGrade = 5;
        }

        let filterPharmacists = await this.state.backupPharmacists.filter(pharmacist => {
            if (this.state.searchPharmacist.firstName !== '' && this.state.searchPharmacist.lastName !== '')
                return pharmacist.firstName.toLowerCase().includes(this.state.searchPharmacist.firstName.toLowerCase()) && pharmacist.lastName.toLowerCase().includes(this.state.searchPharmacist.lastName.toLowerCase());
            else if (this.state.searchPharmacist.firstName !== '')
                return pharmacist.firstName.toLowerCase().includes(this.state.searchPharmacist.firstName.toLowerCase());
            else if (this.state.searchPharmacist.lastName !== '')
                return pharmacist.lastName.toLowerCase().includes(this.state.searchPharmacist.lastName.toLowerCase());
            return true;
        });

        let filterPharmacistsGrades = filterPharmacists.filter(pharmacist => {
            if (minRequiredGrade === 0 && maxRequiredGrade === 0)
                return pharmacist;
            return pharmacist.grade >= minRequiredGrade && pharmacist.grade <= maxRequiredGrade;
        })
        this.setState({
            pharmacists : filterPharmacistsGrades
        })
    }

    searchDermatologist = async () => {
        let minRequiredGrade = 0;
        let maxRequiredGrade = 0;
        if (this.state.searchDermatologist.filterGradesDermatologist === options[1]) {
            minRequiredGrade = 1;
            maxRequiredGrade = 2;
        }
        else if (this.state.searchDermatologist.filterGradesDermatologist === options[2]) {
            minRequiredGrade = 2;
            maxRequiredGrade = 3;
        }
        else if (this.state.searchDermatologist.filterGradesDermatologist === options[3]) {
            minRequiredGrade = 3;
            maxRequiredGrade = 4;
        }
        else if (this.state.searchDermatologist.filterGradesDermatologist === options[4]) {
            minRequiredGrade = 4;
            maxRequiredGrade = 5;
        }

        let filterDermatologists = await this.state.backupDermatologists.filter(dermatologist => {
            if (this.state.searchDermatologist.firstName !== '' && this.state.searchDermatologist.lastName !== '')
                return dermatologist.firstName.toLowerCase().includes(this.state.searchDermatologist.firstName.toLowerCase()) && dermatologist.lastName.toLowerCase().includes(this.state.searchDermatologist.lastName.toLowerCase());
            else if (this.state.searchDermatologist.firstName !== '')
                return dermatologist.firstName.toLowerCase().includes(this.state.searchDermatologist.firstName.toLowerCase());
            else if (this.state.searchDermatologist.lastName !== '')
                return dermatologist.lastName.toLowerCase().includes(this.state.searchDermatologist.lastName.toLowerCase());
            return true;
        });

        let filterDermatologistsGrades = filterDermatologists.filter(dermatologist => {
            if (minRequiredGrade === 0 && maxRequiredGrade === 0)
                return dermatologist;
            return dermatologist.grade >= minRequiredGrade && dermatologist.grade <= maxRequiredGrade;
        })
        this.setState({
            dermatologists : filterDermatologistsGrades
        })
    }

    fetchDermatologistNotWorkingInThisPharmacy = async () => {
        await axios.get("http://localhost:8080/api/dermatologists/getAllDermatologistNotWorkingInPharmacy/" + this.state.pharmacyId,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            }).then( //todo change pharmacy id
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

    fetchPharmacyId = async () => {
        await axios.get("http://localhost:8080/api/pharmacyAdmin/getPharmacyAdminPharmacy/" + this.state.user.id,
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    pharmacyId : res.data
                })
            });
    }
}
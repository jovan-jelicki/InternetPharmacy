import React from 'react'
import PatientLayout from '../../layout/PatientLayout'
import EPrescriptionSort from './../../components/EPrescriptionSort'
import {Col, Card, Row, Table, Accordion} from "react-bootstrap";
import axios from 'axios'

class PatientEPrescriptionPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            prescriptions : [],
            status : 'All'
        }
    }

    componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        axios
        .get('http://localhost:8080/api/eprescriptions/patient/' + this.aut.id, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })
        .then(res => {
            this.backup = res.data
            this.setState({
                prescriptions : res.data
            })
        })
    }

    sortByDate = (method) => {
        this.setState({
            prescriptions : [...this.state.prescriptions.sort((a, b) => {
                let da = new Date(a.dateIssued), db = new Date(b.dateIssued)
                if(method == 'asc')
                    return da - db
                else
                    return db - da
            })]
        })
    }

    filterByStatus = () => {
        if(this.state.status == 'All')
            this.setState({
                prescriptions : [...this.backup]
            })
        else
            this.setState({
                prescriptions : [...this.backup.filter(p => p.status == this.state.status)]
            })
    }

    render() {
        const prescriptions = this.state.prescriptions.map((p, index) => {
            const medications = p.medicationQuantity.map((q, index) => {
                return (
                    <tr>
                        <td>{index + 1}</td>
                        <td>{q.medication.name}</td>
                        <td>{q.quantity}</td>
                        <td>{q.medication.dose}</td>
                        <td>{q.medication.note}</td>
                    </tr>
                )
            })
            return (
                <Col xs={12}>
                <Accordion defaultActiveKey="1" className={'mt-3 mb-3'} key={index}>
                    <Card bg={'dark'} text={'white'}>
                        <Accordion.Toggle as={Card.Header} eventKey="0">
                            <Row>{this.formatDate(p.dateIssued)}</Row>
                            <Row>{p.status}</Row>
                        </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                            <Card text={'white'} bg={'dark'}>
                            <Card.Body>
                                <h4>Prescribed medications</h4>
                                <Table striped bordered hover variant="dark">
                                    <thead>
                                        <tr>
                                        <th>#</th>
                                        <th>Medication</th>
                                        <th>Quantity</th>
                                        <th>Dose</th>
                                        <th>Medication Note</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {medications}
                                    </tbody>
                                </Table>
                            </Card.Body>
                            </Card>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
                </Col>
            )
        })

        return (
            <PatientLayout>
                <h2 className="mt-3 mb-3">ePrescription History</h2>
                <label>Filter by Status</label>
                <select value={this.state.status} onChange={this.changeStatus}>
                    <option>All</option>
                    <option>successful</option>
                    <option>pending</option>
                    <option>denied</option>
                </select>
                <EPrescriptionSort sorting={this.sortByDate}/>
                <Row>
                    {prescriptions}
                </Row>
            </PatientLayout>
        )
    }

    formatDate = (dateTime) => {
        const parts = dateTime.substring(0, 10).split('-')
        return parts[2] + '. ' + parts[1] + '. ' + parts[0] + '.'
    }

    changeStatus = async (e) => {
        await this.setState({
            status : e.target.value
        })

        this.filterByStatus()
    }
}

export default PatientEPrescriptionPage
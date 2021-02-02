import React from 'react';
import { PureComponent } from 'react';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
} from 'recharts';
import {Button, Col} from "react-bootstrap";
import {Modal} from "react-bootstrap";
import {Navbar} from "react-bootstrap";
import {Form} from "react-bootstrap";
import {FormControl, Row} from "react-bootstrap";
import axios from "axios";

const data = [
    {
        name: 'Jan',  pv: 2400
    },
    {
        name: 'Feb',  pv: 1398
    },
    {
        name: 'Mar',  pv: 9800
    },
    {
        name: 'Apr',  pv: 3908
    },
    {
        name: 'May',  pv: 4800
    },
    {
        name: 'June',  pv: 3800
    },
    {
        name: 'July',  pv: 4300
    },
];


const options = [
    'Monthly', 'Quarterly', 'Yearly'
];
const defaultOption = options[0];


export default class PharmacyCharts extends React.Component{
    constructor() {
        super();
        this.state = {
            appointmentsReportOptions: options[0],
            appointmentsReportData: []
        }
    }

    componentDidMount() {
        this.renderAppointmentMonthlyReport();
    }

    static jsfiddleUrl = 'https://jsfiddle.net/alidingling/30763kr7/';

    render() {
        return (
            <div className="container-fluid">
                <br/><br/>
                <h1>Appointment report</h1>
                <br/>

                <Navbar bg="light" expand="lg">
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Form inline>
                            <select  name="appointmentsReportOptions" onChange={this.handleAppointmentsReportOptions} value={this.state.appointmentsReportOptions}>
                                {options.map((option,index) => <option key={index} value={option}>{option}</option>)}
                            </select>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>
                <br/>
                <BarChart
                    width={800}
                    height={300}
                    data={this.state.appointmentsReportData}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="appointments" fill="#8884d8" />
                </BarChart>



                <br/><br/>
                <h1>Medications report</h1>
                <br/>
                <BarChart
                    width={500}
                    height={300}
                    data={data}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="pv" fill="#8884d8" />

                </BarChart>

                <br/><br/>
                <h1>Income report</h1>
                <br/>
                <BarChart
                    width={500}
                    height={300}
                    data={data}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="pv" fill="#8884d8" />

                </BarChart>
            </div>
        );
    }

    handleAppointmentsReportOptions = async (event) => {
        const target = event.target;
        let value = event.target.value;

        await this.setState({
            appointmentsReportOptions : value
        });

        if (this.state.appointmentsReportOptions === options[0]) {
            this.renderAppointmentMonthlyReport();
        }
        else if (this.state.appointmentsReportOptions === options[1]) {
            this.renderAppointmentQuarterlyReport();
        }
    }

    renderAppointmentMonthlyReport = () => {
        axios.get("http://localhost:8080/api/appointment/getAppointmentsMonthlyReport/1")
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  appointments:reportDTO.data
                    };
                    temp.push(item);
                });

                this.setState({
                    appointmentsReportData : temp
                })
            })
    }

    renderAppointmentQuarterlyReport = () => {
        axios.get("http://localhost:8080/api/appointment/getAppointmentsQuarterlyReport/1")
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  appointments:reportDTO.data
                    };
                    temp.push(item);
                });

                this.setState({
                    appointmentsReportData : temp
                })
            })
    }
}

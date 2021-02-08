import React from 'react';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,LineChart, Line
} from 'recharts';

import {Button, Col, Navbar} from "react-bootstrap";
import {Form} from "react-bootstrap";
import axios from "axios";
import DatePicker from "react-datepicker";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";


const options = [
    'Monthly', 'Quarterly', 'Yearly'
];
const defaultOption = options[0];


export default class PharmacyCharts extends React.Component{
    constructor() {
        super();
        this.state = {
            appointmentsReportOptions: options[0],
            appointmentsReportData: [],
            medicationConsumptionReportOptions: options[0],
            medicationConsumptionReportData: [],
            incomePeriod : {
                periodStart : "",
                periodEnd : ""
            },
            incomePeriodData : [],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : -1
        }
    }

    async componentDidMount() {
        let temp = await PharmacyAdminService.fetchPharmacyId();
        this.setState({
            pharmacyId : temp
        })
        this.renderAppointmentMonthlyReport();
        this.renderMedicationConsumptionMonthlyReport();
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
                <Navbar bg="light" expand="lg">
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Form inline>
                            <select  name="medicationConsumptionReportOptions" onChange={this.handleMedicationConsumptionReportOptions} value={this.state.medicationConsumptionReportOptions}>
                                {options.map((option,index) => <option key={index} value={option}>{option}</option>)}
                            </select>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>
                <br/>

                <BarChart
                    width={800}
                    height={300}
                    data={this.state.medicationConsumptionReportData}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="consumption" fill="#8884d8" />

                </BarChart>


                <h1>Income report</h1>
                <br/>
                <Navbar bg="light" expand="lg">
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Form inline>
                            <label style={{marginRight : '1rem'}}>Income period start</label>
                            <DatePicker selected={this.state.incomePeriod.periodStart} dateFormat="dd MMMM yyyy"  name="priceDateStart" maxDate={new Date()} onChange={this.setIncomeDateStart} />
                            <label style={{marginRight : '1rem', marginLeft : '3rem'}}>Income period end</label>
                            <DatePicker selected={this.state.incomePeriod.periodEnd} dateFormat="dd MMMM yyyy"  name="priceDateStart" maxDate={new Date()} onChange={this.setIncomeDateEnd} />
                            <Button type="button" className="btn btn-secondary" style={{marginLeft : '4rem'}} onClick={this.generateIncomeReport}>Generate</Button>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>
                <br/><br/>
                <LineChart
                    width={1500}
                    height={400}
                    data={this.state.incomePeriodData}
                    margin={{
                        top: 5, right: 30, left: 20, bottom: 5,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="income" stroke="#8884d8" activeDot={{ r: 8 }} />
                    <Line type="monotone" dataKey="expense" stroke="#82ca9d" />
                </LineChart>
            </div>
        );
    }

    handleAppointmentsReportOptions = async (event) => {
        const target = event.target;
        let value = event.target.value;

        await this.setState({
            appointmentsReportOptions : value
        });

        if (this.state.appointmentsReportOptions === options[0])
            this.renderAppointmentMonthlyReport();

        else if (this.state.appointmentsReportOptions === options[1])
            this.renderAppointmentQuarterlyReport();

        else
            this.renderAppointmentYearlyReport();
    }

    handleMedicationConsumptionReportOptions = async (event) => {
        const target = event.target;
        let value = event.target.value;

        await this.setState({
            medicationConsumptionReportOptions : value
        });

        if (this.state.medicationConsumptionReportOptions === options[0])
            this.renderMedicationConsumptionMonthlyReport();
        else if (this.state.medicationConsumptionReportOptions === options[1])
            this.renderMedicationConsumptionQuarterlyReport();

        else
            this.renderMedicationConsumptionYearlyReport();
    }

    renderAppointmentMonthlyReport = () => {
        axios.get("http://localhost:8080/api/appointment/getAppointmentsMonthlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
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
        axios.get("http://localhost:8080/api/appointment/getAppointmentsQuarterlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
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

    renderAppointmentYearlyReport = () => {
        axios.get("http://localhost:8080/api/appointment/getAppointmentsYearlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
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


    renderMedicationConsumptionMonthlyReport = () => {
        axios.get("http://localhost:8080/api/pharmacy/getMedicationsConsumptionMonthlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  consumption:reportDTO.data
                    };
                    temp.push(item);
                });

                this.setState({
                    medicationConsumptionReportData : temp
                })
            })
    }

    renderMedicationConsumptionQuarterlyReport = () => {
        axios.get("http://localhost:8080/api/pharmacy/getMedicationsConsumptionQuarterlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  consumption:reportDTO.data
                    };
                    temp.push(item);
                });

                this.setState({
                    medicationConsumptionReportData : temp
                })
            })
    }

    renderMedicationConsumptionYearlyReport = () => {
        axios.get("http://localhost:8080/api/pharmacy/getMedicationsConsumptionYearlyReport/" + this.state.pharmacyId, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  consumption:reportDTO.data
                    };
                    temp.push(item);
                });

                this.setState({
                    medicationConsumptionReportData : temp
                })
            })
    }

    convertDates = (periodDate) => {
        return moment(periodDate).format('YYYY-MM-DD');
    }

    generateIncomeReport = () => {
        // let temp = this.state.incomePeriod;
        // temp.periodStart = this.convertDates(temp.periodStart) + " 12:00:00";
        // temp.periodEnd = this.convertDates(temp.periodEnd) + " 12:00:00";

        axios.post("http://localhost:8080/api/pharmacy/getPharmacyIncomeReportByPeriod", {
            periodStart : this.convertDates(this.state.incomePeriod.periodStart) + " 12:00:00",
            periodEnd : this.convertDates(this.state.incomePeriod.periodEnd) + " 12:00:00",
            pharmacyId : this.state.pharmacyId
        }, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                let temp = [];
                res.data.map(reportDTO => {
                    let item = {
                        name: reportDTO.chartName,  income:reportDTO.income, expense : reportDTO.expense
                    };
                    temp.push(item);
                });

                this.setState({
                    incomePeriodData : temp
                })
            })
            .catch(() => {
                alert("End date must be after income start date.");
            })
    }

    setIncomeDateStart = (date) => {
        let incomePeriod = this.state.incomePeriod;
        incomePeriod.periodStart = date;
        this.setState({
            incomePeriod : incomePeriod
        })
    }

    setIncomeDateEnd = (date) => {
        let incomePeriod = this.state.incomePeriod;
        incomePeriod.periodEnd = date;
        this.setState({
            incomePeriod : incomePeriod
        })
    }
}


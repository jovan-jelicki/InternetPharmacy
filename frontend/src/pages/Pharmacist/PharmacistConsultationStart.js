import React from "react";

import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";
import axios from "axios";


export default class PharmacistConsultationStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startedConsultation : !!localStorage.getItem("startedConsultation") ? JSON.parse(localStorage.getItem("startedConsultation")) : false,
            appointment : !!localStorage.getItem("consultation") ? JSON.parse(localStorage.getItem("consultation")) : {},
            appointments : [],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }
    componentDidMount() {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/getAllScheduledByExaminer"
            : 'http://localhost:8080/api/appointment/getAllScheduledByExaminer';

        axios
            .post(path, {
                    'id' : this.state.user.id, //this.props.id
                    'type' : this.state.user.type //this.props.role
                }, {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
                .then(res => {
                    this.setState({
                        appointments : res.data
                    })
                })
                .catch(res => alert("Wrong!"));
    }

    render() {
        return (
            <div>
                {this.handleContent()}
            </div>
        )
    }
    handleContent = () => {
        if(!this.state.startedConsultation)
            return ( <ScheduledAppointments renderParent={this.renderParent} events={this.state.appointments}/>)
        else if(this.state.startedConsultation)
            return (<Appointment appointment={this.state.appointment}  renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedConsultation : content,
            appointment : !!localStorage.getItem("consultation") ? JSON.parse(localStorage.getItem("consultation")) : {}
        })
        this.componentDidMount();
    }
}

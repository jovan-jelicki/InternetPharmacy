import React from "react";

import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";
import axios from "axios";


export default class PharmacistConsultationStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startedConsultation : !!localStorage.getItem("startedConsultation") ? JSON.parse(localStorage.getItem("startedConsultation")) : false,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {},
            appointments : [],
        }
    }
    componentDidMount() {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/getAllScheduledByExaminer', {
                    'id' : 3, //this.props.id
                    'type' : 1 //this.props.role
                } )
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
            return ( <ScheduledAppointments renderParent={this.renderParent} role={this.props.role} Id={this.props.Id} events={this.state.appointments}/>)
        else if(this.state.startedConsultation)
            return (<Appointment appointment={this.state.appointment} role={this.props.role} Id={this.props.Id} renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedConsultation : content,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {}
        })
    }
}

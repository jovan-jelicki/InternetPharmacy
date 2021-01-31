import React from "react";
import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";
import axios from "axios";

export default class DermatologistAppointmentStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startedAppointment : !!localStorage.getItem("startedAppointment") ? JSON.parse(localStorage.getItem("startedAppointment")) : false,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {},
            dermatologistEvents : [],
        }
    }

    componentDidMount() {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/getAllScheduledByExaminer', {
                'id' : 3, //this.props.id
                'type' : 0 //this.props.role
            } )
            .then(res => {
                this.setState({
                    dermatologistEvents : res.data
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
        if(!this.state.startedAppointment)
            return ( <ScheduledAppointments renderParent={this.renderParent} role={this.props.role} Id={this.props.Id} events={this.state.dermatologistEvents}/>)
        else if(this.state.startedAppointment)
            return (<Appointment appointment={this.state.appointment} role={this.props.role} Id={this.props.Id} renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedAppointment : content,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {}
        })
        this.componentDidMount();

    }


}
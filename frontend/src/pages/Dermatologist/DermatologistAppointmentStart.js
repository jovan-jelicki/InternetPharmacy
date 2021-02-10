import React from "react";
import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";
import axios from "axios";

export default class DermatologistAppointmentStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            startedAppointment : !!localStorage.getItem("startedAppointment") ? JSON.parse(localStorage.getItem("startedAppointment")) : false,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {},
            dermatologistEvents : [],
        }
    }

    componentDidMount() {

        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/appointment/getAllScheduledByExaminer"
            : 'http://localhost:8080/api/appointment/getAllScheduledByExaminer';
        axios
            .post(path, {
                'id' : this.state.user.id,
                'type' : this.state.user.type
            } ,
                {  headers: {
                        'Content-Type': 'application/json',
                        Authorization : 'Bearer ' + this.state.user.jwtToken
                    }
                })
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
            return ( <ScheduledAppointments renderParent={this.renderParent} events={this.state.dermatologistEvents}/>)
        else if(this.state.startedAppointment)
            return (<Appointment appointment={this.state.appointment}  renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedAppointment : content,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {}
        })
        this.componentDidMount();

    }


}
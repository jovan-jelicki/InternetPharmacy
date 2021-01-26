import React from "react";
import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";

export default class DermatologistAppointmentStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startedAppointment : !!localStorage.getItem("startedAppointment") ? JSON.parse(localStorage.getItem("startedAppointment")) : false,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {},
            dermatologistEvents : [
                {
                    id: 0,
                    patient : { Id : "1",
                        firstName : "Pera",
                        lastName: "Peric"},
                    period : {periodStart: new Date(2021, 0, 20, 10 , 30 , 0 ),
                        periodEnd: new Date(2021, 0, 20, 11, 30, 0)},
                    desc: 'Pre-meeting meeting, to prepare for the meeting'
                },
                {
                    id: 0,
                    patient : { Id : "2",
                        firstName : "Jova",
                        lastName: "Jovic"},
                    period : {periodStart: new Date(2021, 1, 20, 10 , 30 , 0 ),
                        periodEnd: new Date(2021, 1, 20, 11, 30, 0)},
                    desc: 'Pre-meeting meeting, to prepare for the meeting'
                }
            ],
        }
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
            return (<Appointment appointment={this.state.appointment} renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedAppointment : content,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {}
        })
    }


}
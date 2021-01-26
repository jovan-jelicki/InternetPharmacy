import React from "react";

import ScheduledAppointments from "../../components/ScheduledAppointments";
import Appointment from "../../components/Appointment";


export default class PharmacistConsultationStart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startedConsultation : !!localStorage.getItem("startedConsultation") ? JSON.parse(localStorage.getItem("startedConsultation")) : false,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {},
            pharmacistEvents : [
                {
                    id: 0,
                    patient : { Id : "0",
                                firstName : "Pera",
                                lastName: "Peric"},
                    period : {periodStart: new Date(2021, 0, 20, 10 , 30 , 0 ),
                              periodEnd: new Date(2021, 0, 20, 11, 30, 0)},
                    desc: 'Pre-meeting meeting, to prepare for the meeting'
                },
                {
                    id: 0,
                    patient : { Id : "1",
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
        if(!this.state.startedConsultation)
            return ( <ScheduledAppointments renderParent={this.renderParent} role={this.props.role} Id={this.props.Id} events={this.state.pharmacistEvents}/>)
        else if(this.state.startedConsultation)
            return (<Appointment appointment={this.state.appointment} renderParent={this.renderParent}/>)

    }

    renderParent = (content) => {
        this.setState({
            startedConsultation : content,
            appointment : !!localStorage.getItem("appointment") ? JSON.parse(localStorage.getItem("appointment")) : {}
        })
    }
}

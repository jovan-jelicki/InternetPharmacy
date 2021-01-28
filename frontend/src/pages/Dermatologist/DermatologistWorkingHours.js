import React from "react";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import WorkingHoursCalendar from "../../components/WorkingHoursCalendar";

export default class DermatologistWorkingHours extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myEvents : []

        }
    }

    render() {

        return (
            <WorkingHoursCalendar events={this.state.myEvents} />
        );
    }
}
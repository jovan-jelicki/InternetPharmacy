import React from "react";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import WorkingHoursCalendar from "../../components/WorkingHoursCalendar";

export default class DermatologistWorkingHours extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myEvents : [
                {
                    id: 0,
                    title: 'Appointment 1',
                    allDay: false,
                    start: new Date(2021, 0, 20, 10 , 30 , 0 ),
                    end: new Date(2021, 0, 20, 11, 30, 0),
                    desc: 'Pre-meeting meeting, to prepare for the meeting'
                },
                {
                    allDay: false,
                    start: new Date(2021, 1, 20, 12 , 30 , 0 ),
                    end: new Date(2021, 1, 20, 13, 30, 0),
                    title: 'Appointment 2',
                    desc: 'Pre-meeting meeting, to prepare for the meeting'
                },
            ]
        }
    }

    render() {

        return (
            <WorkingHoursCalendar events={this.state.myEvents} />
        );
    }
}
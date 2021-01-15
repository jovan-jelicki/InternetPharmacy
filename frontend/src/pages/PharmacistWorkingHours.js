import React from "react";
import { Calendar, momentLocalizer , Views} from 'react-big-calendar'
import moment from 'moment'
import 'react-big-calendar/lib/css/react-big-calendar.css';


export default class PharmacistWorkingHours extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myEvents : []
        }
    }

    render() {

        const dummyEvents = [
            {
                id: 0,
                title: 'All Day Event very long title',
                allDay: false,
                start: new Date(2021, 0, 20, 10 , 30 , 0 ),
                end: new Date(2021, 0, 20, 11, 30, 0),
                desc: 'Pre-meeting meeting, to prepare for the meeting'
            },
            {
                allDay: false,
                start: new Date(2021, 0, 20, 12 , 30 , 0 ),
                end: new Date(2021, 0, 20, 13, 30, 0),
                title: 'All Day Event',
            },
        ];
        const MyCalendar = (
            <div >
                <Calendar
                    localizer={momentLocalizer(moment)}
                    events={dummyEvents}
                    popup = {false}
                    views={Object.keys(Views).map(k => Views[k])}
                    step={20}
                    timeslots={2}
                    showMultiDayTimes={true}
                    components={{
                        timeSlotWrapper: this.ColoredDateCellWrapper,
                    }}
                    style={{ height: 550}}
                />
            </div>
        );

        return (
            <div height="200px">
                <br/>
                {MyCalendar}
            </div>
        );
    }

    ColoredDateCellWrapper = ({ children }) =>
        React.cloneElement(React.Children.only(children), {
            style: {
                backgroundColor: 'lightblue',
            },
        })
}
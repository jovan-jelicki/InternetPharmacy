import React from "react";
import { Calendar, momentLocalizer , Views} from 'react-big-calendar'
import moment from "moment";
import {Button, Modal} from "react-bootstrap";

export default class WorkingHoursCalendar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal : false,
            event : ""
        }
    }

    render() {
        const MyCalendar = (
            <div >
                <Calendar
                    localizer={momentLocalizer(moment)}
                    events={this.props.events}
                    popup = {false}
                    views={Object.keys(Views).map(k => Views[k])}
                    step={20}
                    timeslots={2}
                    showMultiDayTimes={true}
                    onDoubleClickEvent={event => this.handleModal(event)}
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
                <Modal backdrop="static" show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header>
                        <Modal.Title>{this.state.event.title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.event.desc} <br/>
                        Date-time: {moment(this.state.event.start).format('DD.MM.YYYY hh:mm')} -- {moment(this.state.event.end).format('DD.MM.YYYY hh:mm')}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleModal}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>

        )
    }
    handleModal = (event) => {
        this.setState({
            showModal : !this.state.showModal,
            event : event
        });
    }

    ColoredDateCellWrapper = ({ children }) =>
        React.cloneElement(React.Children.only(children), {
            style: {
                backgroundColor: 'lightblue',
            },
        })
}
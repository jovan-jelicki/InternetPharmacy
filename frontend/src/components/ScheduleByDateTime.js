import React from "react";
import {Button, Col, Row} from "react-bootstrap";
import DateTimePicker from "react-datetime-picker";

export default class ScheduleByDateTime extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            message : "",
            timeForScheduling : "",
        }
    }

    render() {
        return(
            <div>
                <Row>
                    <Col xs={50}>
                        <p> Choose date: </p>
                        <DateTimePicker value={this.state.timeForScheduling} onChange={this.setTimeForNewAppointment}/>
                    </Col>
                    <br/>
                    <Col>
                        <Button  style={{ height : 40, marginTop : 10, float : "right"}} variant="primary" >Schedule</Button>
                    </Col>
                </Row>
            </div>
        )
    }
}
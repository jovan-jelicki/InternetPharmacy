import React from 'react';
import DateTimePicker from 'react-datetime-picker';
import {Button, Row, Col} from "react-bootstrap";

class DateTime extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            date : new Date()
        }
        this.handleInputChange = this.handleInputChange.bind(this)
        this.search = this.search.bind(this)
    }

    handleInputChange(value, e) {
        this.setState({
            date : value
        })
    }

    search() {
        console.log(this.state.date)
    }

    render() {
        return (
            <Row class={'m-5'}>
                <Col>
                    <DateTimePicker
                        onChange={(value, e) => this.handleInputChange(value, e)}
                        value={this.state.date}
                        format={'dd.MM.y H:mm'}
                    />
                </Col>
                <Col>
                    <Button variant={'dark'} onClick={this.search}>Search</Button>
                </Col>
            </Row>
        )
    }
}

export default DateTime
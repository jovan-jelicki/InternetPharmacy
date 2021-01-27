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
        this.extendDate = this.extendDate.bind(this)
    }

    handleInputChange(value, e) {
        this.setState({
            date : value
        })
    }

    search() {
        const dateTime = this.state.date
        const year = dateTime.getFullYear()
        const month = this.extendDate(dateTime.getMonth() + 1)
        const day = this.extendDate(dateTime.getDate())
        const hour = this.extendDate(dateTime.getHours())
        const minute = this.extendDate(dateTime.getMinutes())
        const second = this.extendDate(dateTime.getSeconds())

        this.props.search(`${year}-${month}-${day} ${hour}:${minute}:${second}`)
    }

    extendDate(component) {
        return (component < 10) ? '0' + component : component
    }

    render() {
        return (
            <Row className={'p-4'} style={{'background-color' : '#D6DBDF', 'margin-left' : '-30px',
                'margin-right' : '-30px'}}>
                <Col xs={{span: 2, offset: 4}}>
                    <DateTimePicker
                        onChange={(value, e) => this.handleInputChange(value, e)}
                        value={this.state.date}
                        format={'dd.MM.y H:mm'}
                    />
                </Col>
                <Col xs={'1'}>
                    <Button variant={'outline-dark'} onClick={this.search}>Search</Button>
                </Col>
            </Row>
        )
    }
}

export default DateTime
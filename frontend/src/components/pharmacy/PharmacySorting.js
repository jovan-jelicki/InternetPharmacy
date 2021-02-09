import React from 'react'
import { Row, Form } from "react-bootstrap";

class PharmacySorting extends React.Component {
    constructor(props) {
        super(props)
    }

    onTypeChange=(event) => {
        this.props.sorting(event.target.id)   
    }

    render() {
        return (
            <Row>
               <Form.Group as={Row}>
                        <label style={{'marginLeft':'2rem'}}> Sort by </label>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="name" name="formHorizontalRadios"id="name" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="city" name="formHorizontalRadios" id="city" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="grade" name="formHorizontalRadios" id="grade" onChange={this.onTypeChange}/>
                        </Row>
                </Form.Group>
            </Row>
        )
    }
}

export default PharmacySorting
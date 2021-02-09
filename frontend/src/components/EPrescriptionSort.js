import React from 'react'
import { Row, Form } from "react-bootstrap";

class EPrescriptionSort extends React.Component {
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
                        <label style={{'marginLeft':'2rem'}}> Sort by date</label>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="ascending" name="formHorizontalRadios"id="asc" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="descending" name="formHorizontalRadios" id="desc" onChange={this.onTypeChange} />
                        </Row>
                    </Form.Group>
            </Row>
        )
    }
}

export default EPrescriptionSort
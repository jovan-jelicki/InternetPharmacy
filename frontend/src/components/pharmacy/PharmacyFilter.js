import React from 'react'
import { Row, Form } from "react-bootstrap";

class PharmacyFilter extends React.Component {
    constructor(props) {
        super(props)
    }

    onTypeChange=(event) => {
        this.props.gradeFilter(event.target.id)   
    }

    render() {
        return (
            <Row>
               <Form.Group as={Row}>
                        <label style={{'marginLeft':'2rem'}}> Grade higher than </label>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="5" name="formHorizontalRadios"id="5" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="4" name="formHorizontalRadios" id="4" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="3" name="formHorizontalRadios" id="3" onChange={this.onTypeChange}/>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="2" name="formHorizontalRadios" id="2" onChange={this.onTypeChange}/>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="1" name="formHorizontalRadios" id="1" onChange={this.onTypeChange}/>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="all" name="formHorizontalRadios"id="0" onChange={this.onTypeChange} />
                        </Row>
                    </Form.Group>
            </Row>
        )
    }
}

export default PharmacyFilter
import React from 'react'
import { Row, Form } from "react-bootstrap";

class PharmacyFilter extends React.Component {
    constructor(props) {
        super(props)
    }

    onTypeChange=(event) => {
        this.props.sorting(event.target.id)   
    }

    setStrat = (e) => {
        this.props.strategy(e.target.id)
    }

    render() {
        return (
            <div>
            <Row>
               <Form.Group as={Row}>
                        <label style={{'marginLeft':'2rem'}}> Sort by </label>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="date" name="formHorizontalRadios"id="date" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="price" name="formHorizontalRadios" id="price" onChange={this.onTypeChange} />
                        </Row>
                </Form.Group>

            </Row>
            {/* /*
            <Row>
            <Form.Group as={Row}>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="ascending" name="formHorizontalRadios"id="asc" onChange={this.setStrat} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="descending" name="formHorizontalRadios" id="desc" onChange={this.setStrat} />
                        </Row>
                </Form.Group>
            </Row> */}
            </div>
        )
    }
}

export default PharmacyFilter
import React from 'react'
import {Row, Button, FormControl, Col, Container, Form} from 'react-bootstrap'


class MedicationFilter extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name : '',
            selectedOption:''
        }
        this.onTypeChange=this.onTypeChange.bind(this)

    }

    handleInputChange= (event)=> {
        const target = event.target
        this.setState({
            [target.name] : target.value
        })
    }
    onTypeChange=(event) => {
        console.log(event.target.id)
        var option = event.target.id
        this.setState({
            selectedOption: option,
        })

        this.props.onTypeChange({
            selectedOption :  option,
        })
    }

    render() {
        return (
            <div>
            <fieldset>
                <Form>
                    <Form.Group as={Row}>
                        <label style={{'marginLeft':'1rem'}}> Type </label>
                        <Row sm={10} style={{'marginLeft':'3rem'}}>
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="all" name="formHorizontalRadios"id="all" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="antibiotic" name="formHorizontalRadios"id="antibiotic" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="anesthetic" name="formHorizontalRadios" id="anesthetic" onChange={this.onTypeChange} />
                            <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="antihistamine" name="formHorizontalRadios" id="antihistamine" onChange={this.onTypeChange}/>
                        </Row>
                    </Form.Group>
                </Form>
            </fieldset>
            </div>
        )
    }
}

export default MedicationFilter
import React from 'react'
import { Row, Button, FormControl, Col, Container } from 'react-bootstrap'


class MedicationSearch extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name : '',
        }
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
    }

    handleInputChange= (event)=> {
        const target = event.target
        this.setState({
            [target.name] : target.value
        })
    }

    search=() =>{
        this.props.search({
            name : this.state.name,
        })
    }

    cancel=()=> {
        this.props.cancel()
    }

    render() {
        return (
            <Row>
                <Col xs={{ span: 2.5}} className={'ml-3'}>
                    <FormControl name="name" className="mt-2 mb-2" value={this.state.name}
                                 placeholder={'Medication Name'} onChange={this.handleInputChange}/>
                </Col>
                <Col className={'pt-2'} xs={{ span: 0.5  }}>
                    <Button variant={'outline-dark'} onClick={this.cancel}>Cancel</Button>
                </Col>
                <Col className={'pt-2'} xs={{ span: 0.5 }}>
                    <Button variant={'dark'} onClick={this.search}>Go</Button>
                </Col>
            </Row>
        )
    }
}

export default MedicationSearch
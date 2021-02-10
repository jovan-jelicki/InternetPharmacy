import React from 'react'
import { Row, Button, FormControl, Col, Container } from 'react-bootstrap'
import SearchLocationInput from '../AutoCompleteInput'

class PharmacySearch extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name : '',
            street : '',
            town : '',
            country : ''
        }
        this.handleInputChange = this.handleInputChange.bind(this)
        this.getLocation = this.getLocation.bind(this)
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
    }

    handleInputChange(event) {
        const target = event.target
        this.setState({
            [target.name] : target.value
        })
    }

    getLocation({address_components}) {
        let street, town, country
        address_components.forEach(component => {
            if (component.types.includes('route'))
                street = component.long_name
            else if (component.types.includes('locality'))
                town = component.long_name
            else if (component.types.includes('country'))
                country = component.long_name
        })

        this.setState({
            street: street || '',
            town : town || '',
            country : country || ''
        })
    }

    search() {
        this.props.search({
            name : this.state.name,
            location : {
                street : this.state.street,
                town : this.state.town,
                country : this.state.country
            }
        })
        this.setState({
            name : '',
            street : '',
            town : '',
            country : ''
        })
    }

    cancel() {
        this.props.cancel()
        this.setState({
            name : '',
            street : '',
            town : '',
            country : ''
        })
    }

    render() {
        return (
            <Row>
                <Col xs={{ span: 2.5}} className={'ml-3'}>
                    <FormControl name="name" className="mt-2 mb-2" value={this.state.name}
                            placeholder={'Pharmacy Name'} onChange={this.handleInputChange}/>
                </Col>
                <Col className={'pt-2'} xs={{ span: 2.5 }}>
                    <SearchLocationInput getLocation={this.getLocation}/>
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

export default PharmacySearch
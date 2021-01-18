import React from 'react';
import {Card, Col, Row, Badge} from "react-bootstrap";
import PharmacySearch from './PharmacySearch';
import axios from 'axios';

export default class PharmacyListing extends React.Component {
    constructor() {
        super();
        this.state = {
            pharmacies : [],
        }
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
        this.searchValidation = this.searchValidation.bind(this)
    }

    async componentDidMount() {
        axios
        .get('http://localhost:8080/api/pharmacy')
        .then((res) => {
            this.setState({
                pharmacies : res.data
            })
        })

        this.pharmaciesBackup = [...this.state.pharmacies]
    }

    cancel() {
        console.log(this.pharmaciesBackup)
        this.setState({
            pharmacies : this.pharmaciesBackup
        })
    }

    search({name, location}) {
        this.setState({
            pharmacies : this.state.pharmacies.filter(p => this.searchValidation(p, name, location))
        })
    }

    searchValidation(pharmacy, name, location) {
        console.log(location)
        if(name !== '' && !pharmacy.name.toLowerCase().includes(name.toLowerCase()))
            return false
        if(location.street !== '' && !pharmacy.address.toLowerCase().includes(location.street.toLowerCase()))
            return false
        if(location.town !== '' && !pharmacy.address.toLowerCase().includes(location.town.toLowerCase()))
            return false
        if(location.country !== '' && !pharmacy.address.toLowerCase().includes(location.country.toLowerCase()))
            return false    
        return true
    }


    render() {
        const pharmacies = this.state.pharmacies.map((pharmacy, index) => {
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country 
            return (
                <Col xs={4} >
                <Card bg={'dark'} key={index} text={'white'} style={{ width: '25rem', height: '20rem' }} className="mb-2">
                    <Card.Body>
                    <Card.Title>{pharmacy.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{address}</Card.Subtitle>
                        <Card.Text>
                        {pharmacy.description}
                        </Card.Text>
                    </Card.Body>
                </Card>
                </Col>
            )
        })
        return (
            <div>
                <Row >    
                        <h2 className={'mt-5 ml-3'} id="pharmacies">Pharmacies</h2> 
                </Row>
                <PharmacySearch search={this.search} cancel={this.cancel}/>
                <Row className={'mt-4'}>
                    {pharmacies}
                </Row>
            </div>
            
        )
    }

}
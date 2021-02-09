import React from 'react';
import {Card, Col, Row, Alert, Button} from "react-bootstrap";
import PharmacySearch from './PharmacySearch';
import PharmacyFilter from './PharmacyFilter';
import axios from 'axios';
import StarRatings from 'react-star-ratings'
import helpers from './../../helpers/AuthentificationService'


export default class PharmacyListing extends React.Component {
    constructor() {
        super();
        this.state = {
            pharmacies : [],
        }
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
        this.gradeFilter = this.gradeFilter.bind(this)
    }

    async componentDidMount() {
        this.aut = JSON.parse(localStorage.getItem('user'))

        await axios
        .get('http://localhost:8080/api/pharmacy' /*{
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        })*/)
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
        this.aut = JSON.parse(localStorage.getItem('user'))

        axios
        .post('http://localhost:8080/api/pharmacy/search', {
            'name' : name,
            'street' : location.street,
            'town' : location.town,
            'country': location.country
        }/*, {
            headers : {
                'Content-Type' : 'application/json',
                Authorization : 'Bearer ' + this.aut.jwtToken 
            }
        }*/)
        .then((res) => {
            this.setState({
                pharmacies : res.data
            })
        })
    }

    gradeFilter(grade) {
        this.setState({
            pharmacies : [...this.pharmaciesBackup.filter(p => p.grade >= grade)]
        })
    }

    render() {
        console.log(helpers.isLoggedIn())
        const pharmacies = this.state.pharmacies.map((pharmacy, index) => {
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country 
            return (
                <Col xs={4} >
                <Card bg={'dark'} key={index} text={'white'} style={{ width: '25rem', height: '25rem' }} className="mb-2">
                    <Card.Body>
                    <Card.Title>{pharmacy.name}</Card.Title>
                        <Card.Subtitle className="mb-5 mt-2 text-muted">{address}</Card.Subtitle>
                        <Card.Text>
                        {pharmacy.description}
                        <br/>
                        {helpers.isLoggedIn() && <Button variant={'outline-light'}>Visit & Schedule</Button>}
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                    <StarRatings 
                            starDimension={'25px'}
                            rating={pharmacy.grade}
                            starRatedColor='yellow'
                            numberOfStars={5}
                        />
                    </Card.Footer>
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
                <PharmacyFilter gradeFilter={this.gradeFilter}/>
                {this.state.pharmacies.length != 0 ?
                    <Row className={'mt-4'}>
                            {pharmacies}
                    </Row>
                    :
                    <Alert variant='dark'  show={true}  style={({textAlignVertical: "center", textAlign: "center", marginLeft:'5rem',marginRight:'5rem', backgroundColor:'darkgray'})}>
                    No records found. Try again.
                    </Alert>
                }
            </div>
            
        )
    }

}
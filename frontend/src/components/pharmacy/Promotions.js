import React from 'react';
import {Button} from "react-bootstrap";
import axios from "axios";
import moment from "moment";

export default class Promotions extends React.Component{
    constructor() {
        super();
        this.state = {
            promotions : [],
            userType : 'pharmacyAdmin'
        }
    }

    componentDidMount() {
        this.fetchPromotions();
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    <div style={{marginLeft : '1rem'}}>
                        <br/><br/>
                        <h1>Actions & promotions</h1>

                        <br/>
                        <div className="row">
                        {this.state.promotions.map((promotion, index) => (
                            <div className="row">
                                <div className="col-md-8">
                                    <div className="card">
                                        <div className="card-block" >
                                            <h5 className="card-title">
                                                {promotion.content}
                                            </h5>
                                            <p className="card-text" >
                                                {'Valid from ' + moment(promotion.period.periodStart).format("DD.MM.YYYY.").toString() + ' to ' + moment(promotion.period.periodEnd).format('DD.MM.YYYY').toString()}
                                            </p>
                                            <p>
                                                <Button variant={"success"} style={{marginLeft : '1rem'}}>Subscribe</Button>
                                                <Button variant={"danger"} style={{marginLeft : '1rem'}}>Cancel subscription</Button>
                                            </p>
                                        </div>
                                    </div>
                                    <br/><br/>
                                </div>
                            </div>
                        ))}
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    fetchPromotions = () => {
        axios.get("http://localhost:8080/api/promotion/getCurrentPromotionsByPharmacy/1")
            .then((res) => {
                this.setState({
                    promotions : res.data
                })
            })
    }
}
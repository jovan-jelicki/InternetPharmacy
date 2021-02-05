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
                        <h1>Akcije & Promocije</h1>

                        <br/>
                        <div className="row">
                        {this.state.promotions.map((promotion, index) => (
                            <div className="row">
                                <div className="col-md-8">
                                    <div className="card">
                                        {/*<img className="card-img-top" alt="Bootstrap Thumbnail First"*/}
                                        {/*     src="https://www.layoutit.com/img/people-q-c-600-200-1.jpg"/>*/}
                                        <div className="card-block">
                                            <h5 className="card-title">
                                                {promotion.content}
                                            </h5>
                                            <p className="card-text">
                                                {'Vazi od ' + moment(promotion.period.periodStart).format("DD.MM.YYYY.").toString() + ' do ' + moment(promotion.period.periodEnd).format('DD.MM.YYYY').toString()}
                                            </p>
                                            <p>
                                                <Button variant={"success"} style={{marginLeft : '1rem'}}>Pretplati se</Button>
                                                <Button variant={"danger"} style={{marginLeft : '1rem'}}>Otkazi</Button>
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
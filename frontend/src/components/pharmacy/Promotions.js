import React from 'react';
import {Button} from "react-bootstrap";

export default class Promotions extends React.Component{
    constructor() {
        super();
        this.state = {
            promotions : [],
            userType : 'pharmacyAdmin'
        }
    }

    componentDidMount() {
        let promotions = [
            {
                content : "Svakog petka 20% popusta na sve!",
                periodStart : '21.5.2021.',
                periodEnd : '01.09.2021.'
            },
            {
                content : "Svake srede 5% popusta na sve!",
                periodStart : '11.2.2021.',
                periodEnd : '11.09.2021.'
            }
        ];

        this.setState({
            promotions : promotions
        })
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    <div>
                        <br/><br/>
                        <h1>Akcije & Promocije</h1>

                        <button type="button" class="btn btn-info">Kreiraj promociju</button>
                        <div className="row">





                        {this.state.promotions.map((promotion, index) => (
                            <div className="row">

                                <div className="col-md-4">
                                    <div className="card">
                                        <img className="card-img-top" alt="Bootstrap Thumbnail First"
                                             src="https://www.layoutit.com/img/people-q-c-600-200-1.jpg"/>
                                        <div className="card-block">
                                            <h5 className="card-title">
                                                {promotion.content}
                                            </h5>
                                            <p className="card-text">
                                                {'Vazi od ' + promotion.periodStart + ' do ' + promotion.periodEnd}
                                            </p>
                                            <p>
                                                <Button variant={"success"} >Pretplati se</Button>
                                                <Button variant={"danger"}>Otkazi</Button>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
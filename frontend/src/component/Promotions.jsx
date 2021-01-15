import React from 'react';
import {Button} from "react-bootstrap";

export default class Promotions extends React.Component{
    constructor() {
        super();
    }

    render() {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-12">
                        <h3>
                            Akcije i promocije
                        </h3>
                        <div className="row">
                            <div className="col-md-4">
                                <div className="card">
                                    <img className="card-img-top" alt="Bootstrap Thumbnail First"
                                         src="https://www.layoutit.com/img/people-q-c-600-200-1.jpg"/>
                                    <div className="card-block">
                                        <h5 className="card-title">
                                            Card title
                                        </h5>
                                        <p className="card-text">
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit
                                            non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies
                                            vehicula ut id elit.
                                        </p>
                                        <p>
                                            <a className="btn btn-primary" href="#">Action</a> <a className="btn"
                                                                                                  href="#">Action</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-4">
                                <div className="card">
                                    <img className="card-img-top" alt="Bootstrap Thumbnail Second"
                                         src="https://www.layoutit.com/img/city-q-c-600-200-1.jpg"/>
                                    <div className="card-block">
                                        <h5 className="card-title">
                                            Card title
                                        </h5>
                                        <p className="card-text">
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit
                                            non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies
                                            vehicula ut id elit.
                                        </p>
                                        <p>
                                            <a className="btn btn-primary" href="#">Action</a> <a className="btn"
                                                                                                  href="#">Action</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-4">
                                <div className="card">
                                    <img className="card-img-top" alt="Bootstrap Thumbnail Third"
                                         src="https://www.layoutit.com/img/sports-q-c-600-200-1.jpg"/>
                                    <div className="card-block">
                                        <h5 className="card-title">
                                            Card title
                                        </h5>
                                        <p className="card-text">
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit
                                            non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies
                                            vehicula ut id elit.
                                        </p>
                                        <p>
                                            <a className="btn btn-primary" href="#">Action</a> <a className="btn"
                                                                                                  href="#">Action</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
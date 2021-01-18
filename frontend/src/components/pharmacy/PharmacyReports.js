import React from 'react';
import {Button} from "react-bootstrap";

export default class PharmacyReports extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin'
        }
    }

    componentDidMount() {
    }

    render() {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div>
                        <h3>
                            Reports
                        </h3>

                        <button type="button" class="btn btn-info">Kreiraj promociju</button>
                        <div className="row">





                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
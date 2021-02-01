import React from 'react';
import {Button} from "react-bootstrap";
import axios from "axios";
import moment from "moment";

export default class PriceHistory extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            priceLists : [],
            userType : 'pharmacyAdmin',
            medication : this.props.priceListingHistory
        }
    }

    componentDidMount() {
        this.fetchHistoryPriceLists();
    }

    render() {
        return (
            <div style={({ marginLeft: '1rem' })}>
                <br/><br/>
                <h1>Price History</h1>
                <Button variant={"outline-info"} onClick={() => this.props.showCurrentPriceLists("showCurrentPriceLists")}>{"←"}</Button>
                <br/><br/>
                <div className="col-md-12">
                    <div className="card">
                        <h5 className="card-header">
                            Medication
                        </h5>

                        <div className="card-body">
                            <p className="card-text">
                                Name : {this.state.medication.medicationName}
                                <br/>
                                Current price : {this.state.medication.cost}
                                <br/>
                                Period start : {moment(this.state.medication.period.periodStart).format('DD.MM.YYYY')}
                                <br/>
                                Period start : {moment(this.state.medication.period.periodEnd).format('DD.MM.YYYY')}
                                <br/>
                            </p>
                        </div>
                    </div>
                </div>
                <br/><br/>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Medication name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Start</th>
                        <th scope="col">End</th>

                    </tr>
                    </thead>
                    <tbody>
                    {this.state.priceLists.map((priceList, index) => (
                        <tr>
                            <th scope="row">{index+1}</th>
                            <td>{priceList.medicationName}</td>
                            <td>{priceList.cost}</td>
                            <td>{moment(priceList.period.periodStart).format('DD.MM.YYYY')}</td>
                            <td>{moment(priceList.period.periodEnd).format('DD.MM.YYYY')}</td>


                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    fetchHistoryPriceLists = () => {
        const path = " http://localhost:8080/api/pricelist/getMedicationPriceListHistoryByPharmacy/1/" + this.state.medication.medicationId;
        axios
            .get(path) //todo change pharmacyId
            .then(res => {
                this.setState({
                    priceLists : res.data
                })
            });

    }
}
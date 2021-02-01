import React from 'react';
import {Button, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import axios from "axios";
import moment from "moment";


export default class PriceList extends React.Component{
    constructor() {
        super();
        this.state = {
            userType : 'pharmacyAdmin',
            priceLists : []
        }
    }

    componentDidMount() {
        this.fetchPriceLists();
    }

    render() {
        return (
            <div className="container-fluid">
                <div>
                    <br/><br/>
                    <h1>Pharmacy price list</h1>

                    <br/>

                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Medication name</th>
                            <th scope="col">Current price</th>
                            <th scope="col">Start</th>
                            <th scope="col">End</th>
                            <th scope="col">History</th>

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
                                <td>
                                    <Button type={"info"} >view history</Button>
                                </td>
                               
                            </tr>
                        ))}
                        </tbody>
                    </table>



                </div>
            </div>
        );
    }

    fetchPriceLists = () => {
        axios
            .get('http://localhost:8080/api/pricelist/getCurrentMedicationPriceListByPharmacy/1') //todo change pharmacyId
            .then(res => {
                this.setState({
                    priceLists : res.data
                })
            });

    }
}
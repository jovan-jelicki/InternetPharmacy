import React from 'react';
import {Button, Form, FormControl, Modal, Navbar} from "react-bootstrap";
import axios from "axios";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";


export default class CurrentPriceLists extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            priceLists : [],
            mode : "showCurrentPriceLists",
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : this.props.pharmacyId
        }
    }

    async componentDidMount() {
        // let temp = await PharmacyAdminService.fetchPharmacyId();
        // this.setState({
        //     pharmacyId : temp
        // })
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
                            <tr key={index}>
                                <th scope="row">{index+1}</th>
                                <td>{priceList.medicationName}</td>
                                <td>{priceList.cost}</td>
                                <td>{moment(priceList.period.periodStart).format('DD.MM.YYYY')}</td>
                                <td>{moment(priceList.period.periodEnd).format('DD.MM.YYYY')}</td>
                                <td>
                                    <Button type={"info"} onClick={() => this.handleHistoryButtonClick(priceList)}>view history</Button>
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
            .get(HelperService.getPath('/api/pricelist/getCurrentMedicationPriceListByPharmacy/' + this.state.pharmacyId), {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    priceLists : res.data
                })
            });
    }

    handleHistoryButtonClick = (priceList) => {
        this.props.showHistoryClick(priceList);
        this.props.showHistory("showPriceHistory");
    }
}
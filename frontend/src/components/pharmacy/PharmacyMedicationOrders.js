import React from 'react';
import {Button, Modal, FormControl, Navbar} from "react-bootstrap";
import MedicationOrdersList from "./MedicationOrdersList";
import MedicationOffers from "./MedicationOffers";
import CreateOrder from "./CreateOrder";

export default class PharmacyMedicationOrders extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userType : 'pharmacyAdmin',
            medicationOrders : [],
            clickedMedicationOrder : {},
            showModal : false,
            showContent : 'listOrders'
        }
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        if (this.state.showContent === 'showOffers' && nextState.showContent === 'showOffers') //when clicking on navbar st default behaviour to listOrders
            this.setState({
                showContent : 'listOrders'
            })
        else if (this.state.showContent === 'showCreateOrder' && nextState.showContent === 'showCreateOrder') //when clicking on navbar st default behaviour to listOrders
            this.setState({
                showContent : 'listOrders'
            })
        return true;
    }

    componentDidMount() {
        let medicationOrders = [
            {
                pharmacyAdmin : {
                    firstName : 'Mirko',
                    lastName : 'Mirkovic'
                },
                deadLine : '21.3.2021.',
                medicationQuantity: {

                },
                status : 'pending',
                medicationOffers : []
            },
            {
                pharmacyAdmin : {
                    firstName : 'Jelena',
                    lastName : 'Rozga'
                },
                deadLine : '13.5.2021.',
                medicationQuantity: {

                },
                status : 'processed',
                medicationOffers : []
            }
        ];

        this.setState({
            medicationOrders : medicationOrders,
            showContent : this.props.showContent
        })
    }

    render() {
        return (
            <div>
                <br/><br/>
                {this.handleContent()}
            </div>

        );
    }

    handleContent = () => {
        if (this.state.showContent === 'listOrders')
            return (
                <MedicationOrdersList showOffers={this.changeContent} updateClickedMedicationOrder = {this.updateClickedMedicationOrder}/>
            );
        else if (this.state.showContent === 'showOffers')
            return (
              <MedicationOffers order = {this.state.clickedMedicationOrder}  showListOrders={this.changeContent}/>
            );
        else if (this.state.showContent === 'showCreateOrder')
            return (
              <CreateOrder showListOrders={this.changeContent} />
            );
    }

    changeContent = (content) => {
        this.setState({
            showContent : content
        })
    }

    updateClickedMedicationOrder = (medicationOrder) => {
        this.setState({
            clickedMedicationOrder : medicationOrder
        });
    }
}
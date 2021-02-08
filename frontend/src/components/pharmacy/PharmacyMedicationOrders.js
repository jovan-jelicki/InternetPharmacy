import React from 'react';
import {Button, Modal, FormControl, Navbar} from "react-bootstrap";
import MedicationOrdersList from "./MedicationOrdersList";
import MedicationOffers from "./MedicationOffers";
import CreateOrder from "./CreateOrder";
import EditOrder from "./EditOrder";

export default class PharmacyMedicationOrders extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
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
                <MedicationOrdersList showOffers={this.changeContent} pharmacy={this.props.pharmacy} updateClickedMedicationOrder = {this.updateClickedMedicationOrder}/>
            );
        else if (this.state.showContent === 'showOffers')
            return (
              <MedicationOffers order = {this.state.clickedMedicationOrder}  pharmacy={this.props.pharmacy} showListOrders={this.changeContent}/>
            );
        else if (this.state.showContent === 'showCreateOrder')
            return (
              <CreateOrder showListOrders={this.changeContent} pharmacy={this.props.pharmacy}/>
            );
        else if (this.state.showContent === 'editOrder')
            return (
                <EditOrder showListOrders={this.changeContent} pharmacy={this.props.pharmacy} order = {this.state.clickedMedicationOrder}/>
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
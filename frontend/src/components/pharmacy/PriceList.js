import React from 'react';

import PriceHistory from "./PriceHistory";
import CurrentPriceLists from "./CurrentPriceLists";


export default class PriceList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            priceLists : [],
            mode : "showCurrentPriceLists",
            priceListingHistory : {},
            pharmacyId : this.props.pharmacy.id
        }
    }

    componentDidMount() {

    }

    render() {
        return (
            <div>
                {this.handleContent()}
            </div>
        );
    }

    handleContent = () => {
        if (this.state.mode === 'showCurrentPriceLists')
            return (
                <CurrentPriceLists pharmacyId={this.state.pharmacyId} showHistory={this.changeContent} showHistoryClick = {this.showHistoryClick} />
            );
        else if (this.state.mode === 'showPriceHistory')
            return (
                <PriceHistory pharmacyId={this.state.pharmacyId}  showCurrentPriceLists={this.changeContent} priceListingHistory={this.state.priceListingHistory} />
            );
    }

    changeContent = (content) => {
        this.setState({
            mode : content
        })
    }

    showHistoryClick = (priceListing) => {
        this.setState({
            priceListingHistory : priceListing
        })
    }
}
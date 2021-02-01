import React from 'react';

import PriceHistory from "./PriceHistory";
import CurrentPriceLists from "./CurrentPriceLists";


export default class PriceList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userType : 'pharmacyAdmin',
            priceLists : [],
            mode : "showCurrentPriceLists",
            priceListingHistory : {}
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
                <CurrentPriceLists showHistory={this.changeContent} showHistoryClick = {this.showHistoryClick} />
            );
        else if (this.state.mode === 'showPriceHistory')
            return (
                <PriceHistory  showCurrentPriceLists={this.changeContent} priceListingHistory={this.state.priceListingHistory} />
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
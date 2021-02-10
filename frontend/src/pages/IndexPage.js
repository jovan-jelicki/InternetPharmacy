import React from 'react';
import {Button} from "react-bootstrap";
import UnregisteredLayout from "../layout/UnregisteredLayout"
import PharmacyListing from "../components/pharmacy/PharmacyListing"
import MedicationListing from "../components/MedicationListing"
import Registration from "./Registration";

export default class IndexPage extends React.Component{
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="App">
                <UnregisteredLayout>
                    <PharmacyListing/>
                    <MedicationListing/>
                    <Registration/>
                </UnregisteredLayout>
            </div>
        );
    }
}
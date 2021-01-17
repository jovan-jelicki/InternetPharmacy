import React from 'react';
import {Button} from "react-bootstrap";
import UnregisteredLayout from "../layout/UnregisteredLayout"
import PharmacyListing from "../components/pharmacy/PharmacyListing"
import MedicationListing from "../components/MedicationListing"

export default class IndexPage extends React.Component{
    constructor() {
        super();
        this.state = {
            pharmacies : [],
            medications : []
        }
    }

    componentDidMount() {
        this.setState({
            pharmacies : [
                {
                    name : 'Jankovic',
                    address : 'Narodnog Fronta 5, Novi Sad, Serbia',
                    description : 'Fabulozna apoteka za svaciji ukus i svaku priliku :*'
                },
                {
                    name : 'Biljana i Luka',
                    address : 'Bulevar Oslobodjenja 5, Novi Sad, Serbia',
                    description : 'Mi smo biljana i luka ;;;))))'
                }
            ],
            medications : [
                {
                    name: "Xanax",
                    type: "antihistamine",
                    dose: 2,
                    loyaltyPoints: 3,
                    medicationShape: "pill",
                    manufacturer: "ABC",
                    medicationIssue: "withPrescription",
                    note: "take when hungry",
                    quantity : 10,
                    price : 400.00,
                    grade : 4,
                    ingredient: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
    
                    ],
                    sideEffect: [
                        {
                            name: "nausea"
                        },
                        {
                            name: "blindness"
                        }
                    ],
                    alternatives: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
                    ]
                },
                {
                    name: "Linex",
                    type: "antihistamine",
                    dose: 2,
                    grade : 4,
                    loyaltyPoints: 3,
                    medicationShape: "pill",
                    manufacturer: "ABC",
                    quantity : 10,
                    price : 1300,
                    medicationIssue: "withPrescription",
                    note: "take when hungry",
                    ingredient: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
    
                    ],
                    sideEffect: [
                        {
                            name: "nausea"
                        },
                        {
                            name: "blindness"
                        }
                    ],
                    alternatives: [
                        {
                            name: "brufen"
                        },
                        {
                            name: "linex"
                        }
                    ]
                }
            ]
        })
    }

    render() {
        return (
            <div className="App">
                <UnregisteredLayout>
                    <PharmacyListing pharmacies={this.state.pharmacies}/>
                    <MedicationListing medications={this.state.medications}/>
                </UnregisteredLayout>
            </div>
        );
    }
}
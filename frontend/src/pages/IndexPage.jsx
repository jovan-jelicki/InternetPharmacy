import React from 'react';
import {Button} from "react-bootstrap";
import Login from "../components/Login";

export default class IndexPage extends React.Component{
    constructor() {
        super();
    }

    render() {
        return (
            <div className="App">
                <Login/>
            </div>
        );
    }
}
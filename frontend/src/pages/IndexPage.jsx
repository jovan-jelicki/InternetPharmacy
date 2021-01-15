import React from 'react';
import {Button} from "react-bootstrap";

export default class IndexPage extends React.Component{
    constructor() {
        super();
    }

    render() {
        return (
            <div className="App">
                <Button type="button" className="btn btn-secondary">Secondary</Button>
            </div>
        );
    }
}
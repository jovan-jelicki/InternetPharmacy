import React from 'react';
import {Button, Col, Row} from "react-bootstrap";

export default class OrderQuantityListing extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
        }
    }

    componentDidMount() {
    }

    render() {

        const quantities = this.props.quantities.map((quantity, index) => {
            return (
                <Col xs={10}>
                    <div className="m-2 bg-primary p-2" style={{ height: '3rem' }} key={index}>
                        <Button variant="primary" className="mr-3 p-0" style={{width: '1rem'}} onClick={ () => this.removeQuantity(quantity.medication)}>
                            X
                        </Button>
                        <label className='text-light'>{quantity.medication.name + "  |  " + quantity.quantity}</label>
                    </div>
                </Col>
            )
        })

        return (
            <Row>
                {quantities}
            </Row>
        )
    }

    removeQuantity(quantity) {
        this.props.removeQuantity(quantity)
    }
}
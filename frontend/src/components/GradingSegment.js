import React from 'react'
import { ListGroup, Col, Row, Button } from 'react-bootstrap'
import GradingItem from './GradingItem'

class GradingSegment extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        const rows = this.props.data.map(x => {
            return (
                <GradingItem item={x} grade={this.props.grade}/>
            )
        })

        return (
            <Col xs={12}>
            <ListGroup variant="flush">
                {rows}
            </ListGroup>
            </Col>
        )
    }
}

export default GradingSegment
import React from 'react'
import { ListGroup, Col, Row, Button } from 'react-bootstrap'
import StarRatings from 'react-star-ratings'

class GradingSegment extends React.Component {
    constructor(props) {
        super(props)
        this.changeRating = this.changeRating.bind(this)
        this.grade = this.grade.bind(this)
        this.state = {
            rating : 0
        }
    }

    changeRating(newRating) {
        console.log(newRating)
        this.setState({
            rating : newRating
        })
    }

    grade(entity) {
        entity['grade'] = this.state.rating
        this.props.grade(entity)
    }

    render() {
        console.log(this.props.data)
        const rows = this.props.data.map(x => {
            let name = ''
            if(x.type == 'dermatologist' || x.type == 'pharmacist')
                name = x.firstName + ' ' + x.lastName
            else
                name = x.name
            return (
                <ListGroup.Item>
                    <Row>
                        <Col xs={8}>{name}</Col>
                        <Col xs={3}>
                        <StarRatings
                            starDimension={'40px'}
                            rating={x.grade}
                            starRatedColor='yellow'
                            numberOfStars={5}
                            changeRating={this.changeRating}
                        />
                        </Col>
                        <Col xs={1}>
                            <Button variant="outline-dark" onClick = {() => this.grade(x)}>Grade</Button>
                        </Col>
                    </Row>
                </ListGroup.Item>
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
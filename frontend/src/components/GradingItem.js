import React from 'react'
import { ListGroup, Col, Row, Button } from 'react-bootstrap'
import StarRatings from 'react-star-ratings'

class GradingItem extends React.Component {
    constructor(props) {
        super(props)
        this.changeRating = this.changeRating.bind(this)
        this.grade = this.grade.bind(this)
        this.state = {
            rating : 0
        }
    }

    componentDidMount() {
        this.setState({
            rating : this.props.item.grade
        })
    }

    changeRating(newRating) {
        this.setState({
            rating : newRating
        })
    }

    grade() {
        let item = this.props.item
        item['grade'] = this.state.rating
        this.props.grade(item)
    }

    render() {
        const item = this.props.item
        let name = ''
        if(item.type == 'dermatologist' || item.type == 'pharmacist')
            name = item.firstName + ' ' + item.lastName
        else
            name = item.name

        return (
            <ListGroup.Item>
                    <Row>
                        <Col xs={8}>{name}</Col>
                        <Col xs={3}>
                        <StarRatings
                            starDimension={'40px'}
                            rating={this.state.rating}
                            starRatedColor='yellow'
                            numberOfStars={5}
                            changeRating={this.changeRating}
                        />
                        </Col>
                        <Col xs={1}>
                            <Button variant="outline-dark" onClick = {this.grade}>Grade</Button>
                        </Col>
                    </Row>
                </ListGroup.Item>
        )
    }
}

export default GradingItem
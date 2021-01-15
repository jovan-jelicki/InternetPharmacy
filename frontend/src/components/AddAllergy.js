import React from "react";
import {Button, FormControl} from "react-bootstrap";

class AddAllergy extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'allergy' : '',
            'allergies' : []
        }

        this.handleChange = this.handleChange.bind(this);
        this.addAllergy = this.addAllergy.bind(this);
    }

    componentDidMount() {
        this.setState({
            'allergies' : ['a10', 'a11', 'a12', 'a13']
        })
    }

    handleChange(event) {
        this.setState({allergy: event.target.value});
    }

    addAllergy() {
        this.props.addAllergy(this.state.allergy)
    }

    render() {
        const allergies = this.state.allergies.map((allergy, index) => {
            return <option value={allergy} key={index}>{allergy}</option>
        })

        return (
            <div>
                <select value={this.state.allergy} style={{width: '15rem'}} className="mr-2"
                        onChange={this.handleChange}>
                    {allergies}
                </select>
                <Button variant="success" className="mb-3" onClick={this.addAllergy}>Add</Button>
            </div>
        )
    }

}

export default AddAllergy;
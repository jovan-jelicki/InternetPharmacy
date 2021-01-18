import React from "react";
import {Button, FormControl} from "react-bootstrap";
import axios from 'axios';

class AddAllergy extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            'allergy' : 'Add allergy',
            'allergies' : []
        }

        this.handleChange = this.handleChange.bind(this);
        this.addAllergy = this.addAllergy.bind(this);
    }

    async componentDidMount() {
        await axios
            .get('http://localhost:8080/api/ingredients')
            .then(res => {
                this.setState({
                    'allergies' : res.data
                })
            });
    }

    handleChange(event) {
        this.setState({allergy: event.target.value});
    }

    addAllergy() {
        this.props.addAllergy(this.state.allergies.filter(a => a.name === this.state.allergy)[0])
    }

    render() {
        const allergies = this.state.allergies.map((allergy, index) => {
            return <option value={allergy.name} key={index}>{allergy.name}</option>
        })

        return (
            <div>
                <select value={this.state.allergy} style={{width: '15rem'}} className="mr-2"
                        onChange={this.handleChange}>
                    <option disabled>Add allergy</option>
                    {allergies}
                </select>
                <Button variant="success" className="mb-3" onClick={this.addAllergy}>Add</Button>
            </div>
        )
    }

}

export default AddAllergy;
import React from 'react'
import {Modal, Table} from "react-bootstrap";


class MedicationSpecification  extends React.Component {
    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        console.log(this.props.medication)

        const Ingredients = this.props.medication.ingredient.map((ingredient, key) =>
            <tr>{ingredient.name}</tr>
        )
        const SideEffect = this.props.medication.sideEffect.map((effect, key) =>
            <tr>{effect.name}</tr>
        )
        const Alternatives = this.props.medication.alternatives.map((alt, key) =>
            <tr>{alt.name}</tr>
        )
        return (
            
            <div>
                <Table  hover variant="dark">
                    <tbody>
                        <tr>
                            <td> Medication Id</td>
                            <td>{this.props.medication.id}</td>
                        </tr>
                        <tr>
                            <td> Name</td>
                            <td>{this.props.medication.name}</td>
                        </tr>
                        <tr>
                            <td> Manufacturer</td>
                            <td>{this.props.medication.manufacturer}</td>
                        </tr>
                        <tr>
                            <td> Medication issue</td>
                            <td>{this.props.medication.medicationIssue}</td>
                        </tr>
                        <tr>
                            <td>  Type</td>
                            <td>{this.props.medication.type}</td>
                        </tr>
                        <tr>
                            <td> Shape</td>
                            <td>{this.props.medication.medicationShape}</td>
                        </tr>
                        <tr>
                            <td> Ingredients</td>
                            {this.props.medication.ingredient.length != 0 ?
                                <td>
                                    {Ingredients}
                                </td>
                                :
                                <td> / </td>}
                        </tr>
                        <tr>
                            <td> Side effects</td>
                            {this.props.medication.sideEffect.length != 0?
                                <td>
                                    {SideEffect}
                                </td>
                                :
                                <td> / </td>}
                        </tr>
                        <tr>
                            <td> Alternatives</td>
                            {this.props.medication.alternatives.length != 0 ?
                                <td>
                                    {Alternatives}
                                </td>
                                :
                                <td> / </td>}
                        </tr>
                        <tr>
                            <td>  Dose</td>
                            <td>{this.props.medication.dose}</td>
                        </tr>
                        <tr>
                            <td>  Note</td>
                            <td>{this.props.medication.note}</td>
                        </tr>


                    </tbody>
                </Table>
            </div>
        );
    }
}

export default MedicationSpecification
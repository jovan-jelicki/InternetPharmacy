import React from "react";
import { Container, Button} from "react-bootstrap";
import ReviewedClients from "../ReviewedClients";
import VacationRequest from "../VacationRequest";
import PharmacistProfilePage from "./PharmacistProfilePage";
import PharmacistWorkingHours from "./PharmacistWorkingHours";
import PharmacistConsultationStart from "./PharmacistConsultationStart";
import PharmacistGiveMedicine from "./PharmacistGiveMedicine";

export default class PharmacistHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role: this.props.role,
            Id: this.props.Id,
            navbar : "reviewedClients"
        }
    }

    render() {
        return (
            <div>

                <Container fluid style={{'background-color' : '#AEB6BF'}}>
                    <br/>
                    <ul className="nav justify-content-center">
                        <h3> Welcome {this.props.role}! </h3>
                        <li className="nav-item">
                            <a className="nav-link active" style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' onClick={this.handleChange} name="reviewedClients"> Reviewed clients </a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link " style={{'color' : '#000000' , 'font-weight' : 'bold'}} href='#' name="startAppointment" onClick={this.handleChange}>Start appointment</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link " style={{'color' : '#000000' , 'font-weight' : 'bold'}} href='#' name="workHours" onClick={this.handleChange}>Work hours</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="vacationRequest" onClick={this.handleChange}>Vacation request</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="profile" onClick={this.handleChange}>Account</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link " style={{'color' : '#000000', 'font-weight' : 'bold'}} href='#' name="medicine" onClick={this.handleChange}>Give medication</a>
                        </li>
                    </ul>
                </Container>
                {this.renderNavbar()}
            </div>
        );
    }

    handleChange = (event) => {
        const target = event.target;
        const name = target.name;

        this.setState({
            navbar : name
        });
    }

    renderNavbar = () => {
        if (this.state.navbar === "reviewedClients")
            return (
                <ReviewedClients Id = {this.state.id} role = {this.state.role}/>
            );
        else if (this.state.navbar === "vacationRequest")
            return (
                <VacationRequest Id = {this.state.id} role = {this.state.role} />
            );
        else if (this.state.navbar === "profile")
            return (
                <PharmacistProfilePage Id = {this.state.id} role = {this.state.role} />
            );
        else if (this.state.navbar === "workHours")
            return (
                <PharmacistWorkingHours Id = {this.state.id} role = {this.state.role} />
            );
        else if (this.state.navbar === "startAppointment")
            return (
                <PharmacistConsultationStart Id = {this.state.id} role = {this.state.role} />
            );
        if (this.state.navbar === "medicine")
            return (
                <PharmacistGiveMedicine Id = {this.state.id} role = {this.state.role} />
            );
        else
            return (
                <div><p>Proba</p></div>
            );
    }
}
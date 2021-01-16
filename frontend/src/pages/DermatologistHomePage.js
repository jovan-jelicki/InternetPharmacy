import React from "react";
import { Container, Button} from "react-bootstrap";
import ReviewedClients from "./ReviewedClients";
import VacationRequest from "./VacationRequest";
import DermatologistsProfilePage from "./DermatologistsProfilePage";
import DermatologistWorkingHours from "./DermatologistWorkingHours";

export default class DermatologistHomePage extends React.Component {
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
                <Container> <h3> Welcome {this.props.role}! </h3> </Container>
                <ul className="nav justify-content-center">
                    <li className="nav-item">
                        <Button className="nav-link active" onClick={this.handleChange} name="reviewedClients">Reviewed clients</Button>
                    </li>
                    <li className="nav-item">
                        <Button className="nav-link" name="startAppointment" onClick={this.handleChange}>Start appointment</Button>
                    </li>
                    <li className="nav-item">
                        <Button className="nav-link" name="workHours" onClick={this.handleChange}>Work hours</Button>
                    </li>
                    <li className="nav-item">
                        <Button className="nav-link" name="vacationRequest" onClick={this.handleChange}>Vacation request</Button>
                    </li>
                    <li className="nav-item">
                        <Button className="nav-link" name="profile" onClick={this.handleChange}>Account</Button>
                    </li>
                    <li className="nav-item">
                        <Button className="nav-link" name="scheduleAppointment" onClick={this.handleChange}>Schedule appointment</Button>
                    </li>
                </ul>
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
                <DermatologistsProfilePage Id = {this.state.id} role = {this.state.role} />
            );
        else if (this.state.navbar === "workHours")
            return (
                <DermatologistWorkingHours Id = {this.state.id} role = {this.state.role} />
            );
        else
            return (
              <div><p>Proba</p></div>
            );
    }
}
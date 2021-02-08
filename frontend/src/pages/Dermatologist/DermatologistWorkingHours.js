import React from "react";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import WorkingHoursCalendar from "../../components/WorkingHoursCalendar";
import axios from "axios";

export default class DermatologistWorkingHours extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myEvents : [],
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    }

    componentDidMount() {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/getEvents', {
                'id' : this.state.user.id, //this.props.id
                'type' : this.state.user.type //this.props.role
            } , {  headers: {
                    'Content-Type': 'application/json',
                    Authorization : 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    myEvents : res.data
                })
            })
            .catch(res => alert("Wrong!"))
    }

    render() {

        return (
            <WorkingHoursCalendar events={this.state.myEvents} />
        );
    }
}
import React from "react";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import WorkingHoursCalendar from "../../components/WorkingHoursCalendar";
import axios from "axios";


export default class PharmacistWorkingHours extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myEvents : []
        }
    }
    componentDidMount() {
        axios
            .post(process.env.REACT_APP_BACKEND_ADDRESS ?? 'http://localhost:8080/api/appointment/getEvents', {
                'id' : 1, //this.props.id
                'type' : 1 //this.props.role
            } )
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
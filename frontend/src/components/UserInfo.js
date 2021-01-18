import React from "react";
import {Container, Row, Col, Table, FormControl} from "react-bootstrap";

class UserInfo extends React.Component {
    constructor(props) {
        super(props);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        this.props.onChange(event)
    }

    render() {
        let {user, edit} = this.props;

        return (
            <Table striped hover variant={'dark'}>
                <tbody>
                <tr>
                    <td>First Name</td>
                    {edit
                        ? <FormControl name="firstName" className="mt-2 mb-2" value={user.firstName}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.firstName}</td>
                    }
                </tr>
                <tr>
                    <td>Last Name</td>
                    {edit
                        ? <FormControl name="lastName" className="mt-2 mb-2" value={user.lastName}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.lastName}</td>
                    }
                </tr>
                <tr>
                    <td>Email</td>
                    <td>{user.email}</td>
                </tr>
                <tr>
                    <td>Address</td>
                    {edit
                        ? <FormControl name="address" className="mt-2 mb-2" value={user.address}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.address}</td>
                    }
                </tr>
                <tr>
                    <td>Town</td>
                    {edit
                        ? <FormControl name="town" className="mt-2 mb-2" value={user.town}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.town}</td>
                    }
                </tr>
                <tr>
                    <td>Country</td>
                    {edit
                        ? <FormControl name="country" className="mt-2 mb-2" value={user.country}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.country}</td>
                    }
                </tr>
                <tr>
                    <td>Phone Number</td>
                    {edit
                        ? <FormControl name="phoneNumber" className="mt-2 mb-2" value={user.phoneNumber}
                                       onChange={this.handleInputChange}/>
                        : <td>{user.phoneNumber}</td>
                    }
                </tr>
                </tbody>
            </Table>
        )
    }
}

export default UserInfo;
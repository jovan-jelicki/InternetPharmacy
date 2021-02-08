import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import AuthentificationService from "./AuthentificationService";

const ProtectedRoute = ({ component: Component, role, ...rest }) => {
    alert(role);
    return (
        <Route {...rest} render={
            props => {
                if (AuthentificationService.isLoggedIn() && role !== 'ROLE_pharmacyAdmin') {
                    return <Component {...rest} {...props} />
                }
                else {
                    return <Redirect to={
                        {
                            pathname: '/unauthorized',
                            state: {
                                from: props.location
                            }
                        }
                    } />
                }
            }
        } />
    )
}

export default ProtectedRoute;
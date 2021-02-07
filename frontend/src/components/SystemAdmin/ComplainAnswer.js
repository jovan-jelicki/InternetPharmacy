import React from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import axios from "axios";

export default class ComplainAnswer extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            submitted:false,
            content:null,
            error:'Please write your message',
            boolEnde:false,
        }

    }
    handleInputChange = (event) => {
        const { name, value } = event.target;
        this.setState({ content:value });
    }

    submitForm = async (event) => {
        this.setState({submitted: true});
        event.preventDefault();
        console.log(this.state.content)
        if(this.state.content!=null){
            let error = this.state.error;
            error='';
            this.setState({error});
            this.sendMail();
        }
    }

    async sendMail() {
        console.log(this.props.complaint.patientEmail)
        axios
            .put('http://localhost:8080/api/email/send', {
                'to': 't.kovacevic98@gmail.com',    //this.props.complaint.patientEmail
                'subject':"Response to complaint",
                'body':this.state.content,
            })
            .then(res => {
                this.setState({boolEnde:true});
            });
    }

    render() {
        return (
            <div>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-12">
                            <div className="card">
                                <h6 className="card-header"> Complaint </h6>
                                <div className="card-body">
                                    <p className="card-text">
                                        Created by : {this.props.complaint.patientFullName }
                                        <br/>
                                        Create to : {this.props.complaint.name}
                                        <br/>
                                        Complaint :{this.props.complaint.content}
                                        <br/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



                <p className="card-text" style={{marginTop:10}}>
                    <h6 className="card-header"> Create answer </h6>
                    <Form.Control as="textarea" name="content" rows={6} onChange={(e) => {this.handleInputChange(e)}} />
                    {this.state.submitted && this.state.error.length > 0 && <span className="text-danger">{this.state.error}</span>}
                </p>
                {this.state.boolEnde ?
                    <p className="card-footer" style={{color:"green"}}>Successfully! </p>
                        :
                    <p className="card-footer">
                        <Button style={{marginLeft: 150}} variant="primary" onClick={this.submitForm}> Send
                            answer</Button>
                    </p>
                }
            </div>
        );
    }
}
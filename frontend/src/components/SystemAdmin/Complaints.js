import React from "react";
import axios from "axios";
import {Button, Form, Modal, Row, Table} from "react-bootstrap";
import CreateNewOffer from "../Supplier/CreateNewOffer";
import ComplainAnswer from "./ComplainAnswer";

export default class Complaints extends React.Component {
    constructor() {
        super();
        this.state = {
            complaints:[],
            showModal:false,

        }
    }

    componentDidMount() {
        console.log("BLA")
        this.fetchComplaints()

    }

    async fetchComplaints(){
        await axios
            .get('http://localhost:8080/api/complaints')
            .then((res) => {
                this.setState({
                    complaints: res.data
                })
                console.log("pokupi")
                console.log(this.state.complaints);
            }).catch(
                console.log("greska")
            )
        this.complaintsBackup = [...this.state.complaints]
    }

    handleModal = (complaint) => {
        this.setState({
            showModal: !this.state.showModal,
            modalComplaint:complaint,
        });
        this.state.modalComplaint=complaint;

    }
    closeModal=()=>{
        this.setState({
            showModal : !this.state.showModal
        });
        this.fetchComplaints()

    }

    cancel() {
        this.setState({
            complaints : this.complaintsBackup
        })

    }

    onTypeChange=(event) => {
        console.log(event)
        var option = event.target.id

        this.state.selectedOption=option;

        if(this.state.selectedOption=="all"){
            this.cancel()
        }else if(this.state.selectedOption=="nonActive"){
            console.log(this.complaintsBackup)
            this.state.complaints=this.complaintsBackup;
            console.log(this.state.complaints)
            let filteredData = this.state.complaints.filter(column => {
                return column.active ===false ;
            });
            this.setState({
                complaints: filteredData
            });
        }else {
            this.state.complaints=this.complaintsBackup;
            console.log(this.complaintsBackup)
            let filteredData = this.state.complaints.filter(column => {
                return column.active ===true ;
            });
            this.setState({
                complaints: filteredData
            });
        }
    }



    render() {
        return (
            <div className="container-fluid" >
                <div className="container">
                    <h3 style={({ textAlignVertical: "center", textAlign: "center", marginTop:20})}>Complaints </h3>
                </div>
                <fieldset>
                    <Form>
                        <Form.Group as={Row}>
                            <label style={{'marginLeft':'2rem'}}> Complaint status:</label>
                            <Row sm={10} style={{'marginLeft':'1rem'}}>
                                <Form.Check defaultChecked={true} style={{'marginLeft':'1rem'}} type="radio" label="all" name="formHorizontalRadios"id="all" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="active" name="formHorizontalRadios" id="active" onChange={this.onTypeChange} />
                                <Form.Check style={{'marginLeft':'1rem'}} type="radio" label="nonActive" name="formHorizontalRadios" id="nonActive" onChange={this.onTypeChange} />
                            </Row>
                        </Form.Group>
                    </Form>
                </fieldset>
                <div style={({ marginLeft:90, marginRight:90, marginTop:20})}>
                <Table striped bordered hover variant="dark"  style={{marginTop:60}}>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col" >Created from</th>
                        <th scope="col" colSpan={2}>Created for</th>
                        <th scope="col">Content</th>
                        <th scope="col">Answer</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.complaints.map((complaint, key) =>
                     <tr>
                         <td>{key+1}</td>
                         <td>{complaint.patientFullName}</td>
                         <td>{complaint.type}</td>
                         <td>{complaint.name}</td>
                        <td>{complaint.content} +{complaint.active}</td>
                         {complaint.active &&
                         <td><Button variant="danger" onClick={() => this.handleModal(complaint)}>Answer</Button>
                         </td>
                         }
                     </tr>
                            )}

                    </tbody>
                </Table>
                </div>


                <Modal show={this.state.showModal} onHide={this.closeModal}  style={{'height':850}} >
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Answer</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                      <ComplainAnswer complaint={this.state.modalComplaint}/>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }

}

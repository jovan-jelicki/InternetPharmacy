import React from "react";
import axios from "axios";
import {Button, Form, Modal, Table} from "react-bootstrap";
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

    async componentDidMount() {
        console.log("BLA")
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
    }





    render() {
        return (
            <div className="container-fluid" >
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
                        <td>{complaint.content}</td>
                         <td>   <Button variant="danger" onClick={() => this.handleModal(complaint)}>
                             Answer
                         </Button></td>
                     </tr>
                            )}

                    </tbody>
                </Table>



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

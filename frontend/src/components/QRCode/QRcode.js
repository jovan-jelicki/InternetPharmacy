import React from 'react';
import {Alert, Button, Card, Col, Container, FormGroup, Modal, Row, Table} from "react-bootstrap";
import PatientLayout from "../../layout/PatientLayout";
import jsQR from "jsqr";
import png from "png.js";
import axios from "axios";
import StarRatings from "react-star-ratings";
import PharmacySearch from "../pharmacy/PharmacySearch";
import PharmacyFilter from "../pharmacy/PharmacyFilter";
import HelperService from "../../helpers/HelperService";


export default class QRcode extends React.Component{
    constructor(props) {
        super(props);

        this.state = {
            image: null,
            ePrescription:{ data:[]},
            pharmacies:[],
            boolImage:true,
            showModal:false,
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

        };
        this.onImageChange = this.onImageChange.bind(this);
        this.gradeFilter = this.gradeFilter.bind(this)
        this.search = this.search.bind(this)
        this.cancel = this.cancel.bind(this)
    }

    async componentDidMount() {
        if (this.state.user.type == undefined || this.state.user.type != "ROLE_patient")
            this.props.history.push({
                pathname: "/unauthorized"
            });
    }

    onImageChange = (event)=> {
        var fileReader=new FileReader();
        var fileInput = document.getElementById("file");
        fileReader.readAsArrayBuffer(fileInput.files[0]);
        let img = event.target.files[0];
        this.setState({
            image: URL.createObjectURL(img)
        });


       fileReader.onload= function(event){
            const pngReader = new png(event.target.result);

            pngReader.parse(function(err, pngData) {
                if (err) throw err;
                const pixelArray = new Uint8ClampedArray(pngData.width * pngData.height * 4);
                for (let y = 0; y < pngData.height; y++) {
                    for (let x = 0; x < pngData.width; x++) {
                        const pixelData = pngData.getPixel(x, y);
                        pixelArray[(y * pngData.width + x) * 4 + 0] = pixelData[0];
                        pixelArray[(y * pngData.width + x) * 4 + 1] = pixelData[1];
                        pixelArray[(y * pngData.width + x) * 4 + 2] = pixelData[2];
                        pixelArray[(y * pngData.width + x) * 4 + 3] = pixelData[3];
                    }
                }
                let result=jsQR(pixelArray, pngData.width, pngData.height)
                if(result!=null) {
                    localStorage.setItem("text", JSON.stringify(result.data));
                }
            });
        };

       this.handleQrCode()
    };

    handleQrCode=()=> {
        if (!!localStorage.getItem("text") ) {
            let meds=JSON.parse(localStorage.getItem("text"))
            if(meds!=null) {
                this.state.ePrescription=meds;
                console.log(this.state.ePrescription);
                this.setState({
                    boolImage:false
                })
                this.sendData();
            }
            else alert("Please upload QR code")
        }
    }

    async sendData() {
        //const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/eprescriptions/getPharmacyForQR/"
          //  : 'http://localhost:8080/api/eprescriptions/getPharmacyForQR/';
        axios
            .get(HelperService.getPath('/api/eprescriptions/getPharmacyForQR/'+this.state.ePrescription),{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then(res => {
                this.setState({
                    pharmacies:res.data
                    }
                )
                this.pharmaciesBackup = [...this.state.pharmacies]
            });
    }

    cancel() {
        this.setState({
            pharmacies : this.pharmaciesBackup
        })

    }

    search({name, location}) {
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacy/search"
            : 'http://localhost:8080/api/pharmacy/search';
        axios
            .post(HelperService.getPath('/api/pharmacy/search'), {
                'name' : name,
                'street' : location.street,
                'town' : location.town,
                'country': location.country
            },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.setState({
                    pharmacies : res.data
                })
                console.log(this.state.pharmacies)

            })
    }

    gradeFilter(grade) {

        this.setState({
            pharmacies : [...this.pharmaciesBackup.filter(p => p.pharmacyGrade >= grade)]
        })
    }

    sortByGrade = () => {
        if(this.state.sortType === "desc")
            this.setState({
                pharmacies: this.state.pharmacies.sort((a, b) => (a.pharmacyGrade > b.pharmacyGrade) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                pharmacies: this.state.pharmacies.sort((a, b) => (a.pharmacyGrade > b.pharmacyGrade) ? 1 : -1),
                sortType : "desc"
            })
    }

    sortByPrice = () => {
        if(this.state.sortType === "desc")
            this.setState({
                pharmacies: this.state.pharmacies.sort((a, b) => (a.medicationPrice > b.medicationPrice) ? -1 : 1),
                sortType : "asc"
            })
        else
            this.setState({
                pharmacies: this.state.pharmacies.sort((a, b) => (a.medicationPrice > b.medicationPrice) ? 1 : -1),
                sortType : "desc"
            })
    }

    async buyMedication(pharmacy){
        const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/eprescriptions/buyMedication"
            : 'http://localhost:8080/api/eprescriptions/buyMedication';
        await axios
            .post(HelperService.getPath('/api/eprescriptions/buyMedication'),{
                'pharmacyId':0,
                'prescriptionId':1
        },{
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.state.user.jwtToken
                }
            })
            .then((res) => {
                this.handleModal();
            })

        }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }

render() {
        const pharmacies = this.state.pharmacies.map((pharmacy, index) => {
            const medicationName=pharmacy.medicationName;
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country
            return (
                <Col xs={4} >
                    <Card bg={'dark'} key={index} text={'white'} style={{ width: '25rem', height: '20rem' }} className="mb-2">
                        <Card.Body>
                            <Card.Title>
                                <label className=" col-form-label">{pharmacy.name}</label>
                                <Button style={{ marginLeft:170}} variant="outline-light" onClick={() => this.buyMedication(pharmacy.pharmacyId)}>Buy</Button>
                            </Card.Title>
                            <Card.Subtitle className="mb-5 mt-2 text-muted">{address}</Card.Subtitle>
                            <Card.Text>{pharmacy.medicationName} </Card.Text>
                            <table className="table table-dark">
                                <tr>
                                    <td>Medication price</td>
                                    <td>{pharmacy.medicationPrice}</td>
                                </tr>
                            </table>
                        </Card.Body>
                        <Card.Footer>
                            <StarRatings starDimension={'25px'} rating={pharmacy.pharmacyGrade} starRatedColor='yellow' numberOfStars={5}/>
                        </Card.Footer>
                    </Card>
                </Col>
            )
        })


        return (
            <PatientLayout>
                <Container fluid>
                    <h3 style={{marginLeft:'1rem', marginTop:'2rem'}}>QR code</h3>
                    {this.state.boolImage &&
                    <div>
                        <div className="row" style={{marginTop: '1rem'}}>
                            <h5 style={{marginLeft: '1rem',marginRight: '1rem'}}>Select Image</h5>
                            <input id="file" accept="image/png" type="file" onChange={this.onImageChange}/>
                        </div>

                    </div>
                    }
                    {!this.state.boolImage &&
                        <div>
                            <img style={{height: 300, width: 300, marginLeft: '1rem', marginBottom: 10}}
                                 src={this.state.image}/>
                            {this.state.pharmacies.length != 0 &&
                             <div>
                               < PharmacySearch search={this.search} cancel={this.cancel}/>
                                <PharmacyFilter gradeFilter={this.gradeFilter}/>
                                <Button onClick={this.sortByGrade} style={{height : 40, marginRight:10}}  type="button" className="btn btn-secondary"> Sort by grade</Button>
                                <Button onClick={this.sortByPrice} style={{height : 40}}  type="button" className="btn btn-secondary"> Sort by price</Button>
                                </div>
                            }</div>
                    }
                    {this.state.pharmacies.length != 0 ?
                            <Row className={'mt-4'}>
                                {pharmacies}
                            </Row>
                        :
                        <Alert variant='dark'  show={true}  style={({textAlignVertical: "center", textAlign: "center", marginTop:'10rem',marginLeft:'5rem',marginRight:'5rem', backgroundColor:'darkgray'})}>
                            No records found. Try again.
                        </Alert>
                    }
                </Container>
                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Successfully!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        <div className="row" >
                            <div className="col-md-12">
                                <div className="card">
                                    <div className="card-body" style={{padding : '1rem'}}>
                                        <p className="card-text">
                                           Successfully
                                        </p>
                                    </div>
                                </div>
                                <br/><br/>
                            </div>
                        </div>
                    </Modal.Body>
                </Modal>
            </PatientLayout>

        );
    }
}




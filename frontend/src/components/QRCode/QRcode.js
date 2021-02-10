import React from 'react';
import {Alert, Button, Card, Col, Container, Row, Table} from "react-bootstrap";
import PatientLayout from "../../layout/PatientLayout";
import jsQR from "jsqr";
import png from "png.js";
import axios from "axios";
import StarRatings from "react-star-ratings";


export default class QRcode extends React.Component{
    constructor(props) {
        super(props);

        this.state = {
            image: null,
            ePrescription:{ data:[]},
            pharmacies:[],
            boolBLa:false,
            parameters:[{
                'medicationId':''
            }]
        };
        this.onImageChange = this.onImageChange.bind(this);
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
                localStorage.setItem("text", JSON.stringify(result.data));
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
                this.sendData();
            }
            else alert("Please upload QR code")
        }
    }

    async sendData() {
        console.log(this.state.ePrescription)
        axios
            .get('http://localhost:8080/api/eprescriptions/getPharmacyForQR/'+this.state.ePrescription)
            .then(res => {
                this.setState({
                    pharmacies:res.data
                    }
                )
                console.log(this.state.pharmacies)
            });
    }

render() {
        const pharmacies = this.state.pharmacies.map((pharmacy, index) => {
            const address = pharmacy.address.street + ', ' + pharmacy.address.town + ', ' + pharmacy.address.country
            return (
                <Col xs={4} >
                    <Card bg={'dark'} key={index} text={'white'} style={{ width: '25rem', height: '15rem' }} className="mb-2">
                        <Card.Body>
                            <Card.Title>
                                <label className=" col-form-label">{pharmacy.name}</label>
                                <Button style={{ marginLeft:170}} variant="outline-light">Buy</Button>
                            </Card.Title>
                            <Card.Subtitle className="mb-5 mt-2 text-muted">{address}</Card.Subtitle>
                            <Card.Text>{pharmacy.description} </Card.Text>
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
                    <div className="row"style={{marginTop: '1rem'}}>
                        <h>Select Image</h>
                        <input id="file" accept="image/png" type="file" onChange={this.onImageChange} />
                    </div>
                    <img style={{height:300, width:300,marginLeft:'1rem'}} src={this.state.image} />

                    {this.state.pharmacies.length != 0 ?
                        <Row className={'mt-4'}>
                            {pharmacies}
                        </Row>
                        :
                        <Alert variant='dark'  show={true}  style={({textAlignVertical: "center", textAlign: "center", marginLeft:'5rem',marginRight:'5rem', backgroundColor:'darkgray'})}>
                            No records found. Try again.
                        </Alert>
                    }

                </Container>
            </PatientLayout>

        );
    }
}




import React from 'react';
import {Button, Container} from "react-bootstrap";
import PatientLayout from "../../layout/PatientLayout";
import jsQR from "jsqr";
import png from "png.js";


export default class QRcode extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            image: null,
            loaded:false,
            fileReader : new FileReader()
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
                console.log(jsQR(pixelArray, pngData.width, pngData.height));
            });
        };
    };

    render() {
        return (
            <PatientLayout>
                <Container fluid>
                    <h3 style={{marginLeft:'1rem', marginTop:'2rem'}}>QR code</h3>
                    <div className="row"style={{marginTop: '1rem'}}>
                        <h>Select Image</h>
                        <input id="file" accept="image/png" type="file" onChange={this.onImageChange} />
                    </div>
                    <img style={{height:300, width:300,marginLeft:'1rem'}} src={this.state.image} />
                    <script src="index.js"></script>
                </Container>
            </PatientLayout>

        );
    }
}




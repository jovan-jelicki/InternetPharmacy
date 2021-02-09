import React from 'react';
import {Button, Container} from "react-bootstrap";
import PatientLayout from "../layout/PatientLayout";
import jsQR from "jsqr";
import png from "png.js";

const fileReader = new FileReader();
const fileInput = document.getElementById("file");


fileReader.onload= function(event){
    const pngReader = new png(event.target.result);
    pngReader.parse(function(err, pngData) {
        if (err) throw err;
        const pixelArray = convertPNGtoByteArray(pngData);
        console.log(jsQR(pixelArray, pngData.width, pngData.height));
    });
};

fileInput.onchange = function () {
    fileReader.readAsArrayBuffer(fileInput.files[0]);
};

function convertPNGtoByteArray(pngData) {
    console.log(pngData)
    const data = new Uint8ClampedArray(pngData.width * pngData.height * 4);
    for (let y = 0; y < pngData.height; y++) {
        for (let x = 0; x < pngData.width; x++) {
            const pixelData = pngData.getPixel(x, y);

            data[(y * pngData.width + x) * 4 + 0] = pixelData[0];
            data[(y * pngData.width + x) * 4 + 1] = pixelData[1];
            data[(y * pngData.width + x) * 4 + 2] = pixelData[2];
            data[(y * pngData.width + x) * 4 + 3] = pixelData[3];
        }
    }
    console.log(data)
    return data;
}








class QRcode extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            image: null,
            loaded:false
        };

        this.onImageChange = this.onImageChange.bind(this);
    }
/*
    onImageChange = event => {
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            this.setState({
                image: URL.createObjectURL(img)
            });
        }
    };
  */
    onImageChange = (event)=> {
       /* let img = event.target.files[0];
        fileReader.readAsArrayBuffer(img);
        this.setState({
            image: URL.createObjectURL(img)
        });
        */

        let img = event.target.files[0];
        fileReader.readAsArrayBuffer(img);
        this.setState({
            image: URL.createObjectURL(img)
        });


    };

    /*
    onLoad=event=>{
        console.log(event.target)

        if (!this.state.loaded) {
            console.log('image loaded');
            this.setState({ loaded: true });
        }
        console.log("BLAA")
        console.log(event.target)
        const pngReader = new png(event.target);
        pngReader.parse(function(err, pngData) {
            if (err) throw err;
            const pixelArray = this.convertPNGtoByteArray(pngData);
            console.log(jsQR(pixelArray, pngData.width, pngData.height));
        });
    };

*/

    render() {
        return (
            <PatientLayout>
                <Container fluid>
                    <h3 style={{marginLeft:'1rem', marginTop:'2rem'}}>QR code</h3>

                    <div className="row"style={{marginTop: '1rem'}}>
                        <h>Select Image</h>
                        <input accept="image/png" style={{marginLeft:'1rem'}} type="file" id="file" name="myImage" onChange={this.onImageChange} />
                    </div>
                    <img style={{height:300, width:300,marginLeft:'1rem'}} src={this.state.image}  />

                </Container>
            </PatientLayout>

        );
    }
}export default QRcode;
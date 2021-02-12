import React from 'react';
import {Button, Modal} from "react-bootstrap";
import axios from "axios";
import moment from "moment";
import PharmacyAdminService from "../../helpers/PharmacyAdminService";
import HelperService from "../../helpers/HelperService";

export default class Promotions extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            promotions : [],
            showModal: false,
            showModalAlert:false,
            isSubscribed:false,
            subPromotion:{
                id:'',
                pharmacyId:'',
                content:'',
                periodStart: '',
                periodEnd:'',
            },
            user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            pharmacyId : this.props.pharmacy.id
        }
    }

    componentDidMount() {
        // let temp = await PharmacyAdminService.fetchPharmacyId();
        // this.setState({
        //     pharmacyId : temp
        // })
        this.fetchPromotions();
    }

    handlePromotion=(promotion)=>{
        console.log(promotion)
        this.subscribeToPromotion(promotion)
        //this.checkIfSubscribed(promotion)
    }

    chooseModal=()=>{
        console.log(this.state.isSubscribed)
        let boolState=this.state.isSubscribed
        if(boolState==false){
            console.log("DOSAOFALSE")
           this.handleModalAlert()
        }else {
            console.log("DOSAO")

            this.handleModal();
        }
    }

    handleModal = () => {
        this.setState({
            showModal : !this.state.showModal
        });
    }
    handleModalAlert = () => {
        this.setState({
            showModalAlert : !this.state.showModalAlert
        });
    }

    render() {
        return (
            <div className="container-fluid" style={({ marginLeft: '1rem' })}>
                <div className="row">
                    <div style={{marginLeft : '1rem'}}>
                        <br/><br/>
                        <h1>Actions & promotions</h1>

                        <br/>
                        <div className="row">
                        {this.state.promotions.map((promotion, index) => (
                            <div className="row" key={index}>
                                <div className="col-md-8">
                                    <div className="card">
                                        <div className="card-block" style={{padding : '1rem'}}>
                                            <h5 className="card-title">
                                                {promotion.content} {promotion.id}
                                            </h5>
                                            <p className="card-text" >
                                                {'Valid from ' + moment(promotion.period.periodStart).format("DD.MM.YYYY.").toString() + ' to ' + moment(promotion.period.periodEnd).format('DD.MM.YYYY').toString()}
                                            </p>
                                            <p>
                                                <Button variant={"success"} style={this.state.user.type !== 'ROLE_pharmacyAdmin' ? {display : 'inline-block'} : {display : 'none'}} onClick={() => this.handlePromotion(promotion)} >Subscribe</Button>
                                            </p>
                                        </div>
                                    </div>
                                    <br/><br/>
                                </div>
                            </div>
                        ))}
                        </div>
                    </div>
                </div>

                <Modal show={this.state.showModal} onHide={this.handleModal}>
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Successfully subscribed</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        <div className="row" >
                            <div className="col-md-12">
                                <div className="card">
                                    <div className="card-body" style={{padding : '1rem'}}>
                                        <p className="card-text">
                                            You are successfully subscribed!
                                            Please check your profile.
                                        </p>
                                    </div>
                                </div>
                                <br/><br/>
                            </div>
                        </div>
                    </Modal.Body>
                </Modal>

                <Modal show={this.state.showModalAlert} onHide={this.handleModalAlert}>
                    <Modal.Header closeButton style={{'background':'silver'}}>
                        <Modal.Title>Subscribe info</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{'background':'silver'}}>
                        <div className="row" >
                            <div className="col-md-12">
                                <div className="card">
                                    <div className="card-body" style={{padding : '1rem'}}>
                                        <p className="card-text">
                                            You are already subscriberd.
                                            Please check your profile.
                                        </p>
                                    </div>
                                </div>
                                <br/><br/>
                            </div>
                        </div>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }

    fetchPromotions = () => {
        axios.get(HelperService.getPath("/api/promotion/getCurrentPromotionsByPharmacy/" + this.state.pharmacyId), {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + this.state.user.jwtToken
            }
        })
            .then((res) => {
                this.setState({
                    promotions : res.data
                })
            })
    }

    async checkIfSubscribed (promotion){
        console.log(promotion.id)
        await axios
            .get(HelperService.getPath('/api/promotion/checkPatientSubscribedToPromotion/' + promotion.pharmacyId + "/" + 0 + "/" + promotion.id))
            .then(res => {
                console.log(this.state.isSubscribed)

                console.log(res.data)
                this.setState({isSubscribed : res.data})
                console.log(this.state.isSubscribed)
                //alert("Successfully registered!"+this.state.isSubscribed);

            }).catch(() => {
           // alert("Pharmacy was not registered successfully!")
        })
    }

    subscribeToPromotion =  (promotion)=>{
        axios
            .put('http://localhost:8080/api/promotion/subscribeToPromotion/'+0+"/"+promotion.id)
            .then(res => {
                console.log(res.data)
                this.setState({isSubscribed : res.data})
                this.chooseModal();

            }).catch(() => {
            // alert("Pharmacy was not registered successfully!")
        })
    }
}

import axios from "axios";

async function fetchPharmacyId() {
    let user = !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {};
    var pharmacyId = -1;


    const path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + "/api/pharmacyAdmin/getPharmacyAdminPharmacy/" +
        user.id
        : 'http://localhost:8080/api/pharmacyAdmin/getPharmacyAdminPharmacy/' + user.id;

    await axios.get(path,
        {
            headers: {
                'Content-Type': 'application/json',
                Authorization : 'Bearer ' + user.jwtToken
            }
        })
        .then((res) => {
            pharmacyId = res.data
        });
    return pharmacyId;
}

export default {
    fetchPharmacyId,
}
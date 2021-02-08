import axios from "axios";

async function fetchPharmacyId() {
    let user = !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {};
    var pharmacyId = -1;
    await axios.get("http://localhost:8080/api/pharmacyAdmin/getPharmacyAdminPharmacy/" + user.id,
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
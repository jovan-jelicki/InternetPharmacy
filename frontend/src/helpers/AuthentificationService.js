function isLoggedIn() {
    const jwtLocal = JSON.parse(localStorage.getItem('user'));
    return jwtLocal != null;
}

function logout() {
    localStorage.removeItem('user');
}

function validateRoles (roles) {
    let temp = false;

}

export default {
    logout,
    isLoggedIn
}
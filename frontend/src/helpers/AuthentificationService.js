function isLoggedIn() {
    const jwtLocal = JSON.parse(localStorage.getItem('user'));
    return jwtLocal != null;
}

function logout() {
    localStorage.removeItem('user');
}


export default {
    logout,
    isLoggedIn
}
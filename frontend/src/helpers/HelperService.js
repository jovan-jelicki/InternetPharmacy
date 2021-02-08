function getPath (apiPath) {
    let path = process.env.REACT_APP_BACKEND_ADDRESS ? process.env.REACT_APP_BACKEND_ADDRESS + apiPath
        : 'http://localhost:8080' + apiPath;
    return path;
}

export default {
    getPath
}
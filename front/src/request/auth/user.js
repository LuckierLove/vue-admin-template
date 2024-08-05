import axios from "axios";

const login = function (params) {
    return axios.post('/api/auth/login', params)
}

const logout = function (params) {
    return axios.post('/api/auth/logout', params)
}

export {
    login
}
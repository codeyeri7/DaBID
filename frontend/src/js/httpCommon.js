import axios from "axios";


export default {
    axios: axios.create({
        baseURL: "https://i5a506.p.ssafy.io/api",
        // baseURL: "http://localhost:8080/",
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
            'Access-Control-Allow-Headers': 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range',
            'Access-Control-Expose-Headers': 'Content-Length,Content-Range',

        }
    })
}
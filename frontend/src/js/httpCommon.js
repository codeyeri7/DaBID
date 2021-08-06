import axios from "axios";


export default {
    axios: axios.create({
        baseURL: "https://i5a506.p.ssafy.io:8080/",
        headers: {
            "Content-Type": "application/json",
        }
    })
}
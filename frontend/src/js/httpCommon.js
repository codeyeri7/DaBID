import axios from "axios";


export default {
    axios: axios.create({
        baseURL: "https://localhost:8080/",
        headers: {
            "Content-Type": "application/json",
        }
    })
}
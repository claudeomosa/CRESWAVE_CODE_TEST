import { Injectable } from '@angular/core';
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.baseURL = "http://localhost:8080"
    axios.defaults.headers.post["content-type"] = "application/json"
  }

  request(method: string, url: string, data: any) {
    return axios({
      method,
      url,
      data
    })
  }
}

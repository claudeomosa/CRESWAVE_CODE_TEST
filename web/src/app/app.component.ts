import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FormsModule} from "@angular/forms";
import {AxiosService} from "./axios.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    FormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Creswave Blog';
  constructor(
    private axiosService: AxiosService,
    private router: Router) {
  }


  logoutUser() {
    this.axiosService.request(
      "POST",
      "/logout",
    {}
    ).then((response) => {
      if (response.status === 200){
        this.router.navigate([""]).then()
      }
    })
  }
}

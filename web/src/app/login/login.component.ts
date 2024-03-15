import {Component, EventEmitter, Output} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from "@angular/forms";
import {AxiosService} from "../axios.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {


  constructor(
    private axiosService: AxiosService,
    private router: Router) {}
  loginFormData = {
    "username": "",
    "password": ""
  }
  errorMessage: string = "";
  onSubmitLogin(): void {
    this.axiosService.request(
      "POST",
      "/login",
      {
        "username": this.loginFormData.username,
        "password": this.loginFormData.password
      }
    ).then((response) => {
      if (response.status === 200) {
        this.router.navigate(["create-post"])
          .then(
          )
      } else  {
       this.errorMessage = "Invalid username or password"
      }
    }).catch((error) => {
      this.errorMessage = "Invalid username or password"
    })
  }

}

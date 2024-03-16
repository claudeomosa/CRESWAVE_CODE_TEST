import {Component} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AxiosService} from "../axios.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  errorMessage: string = "";
  registerFormData = {
    "fullname": "",
    "email": "",
    "password": "",
    "confirmPassword": ""
  }

  constructor(
    private axiosService: AxiosService,
    private router: Router
  ) {}

  private generateUsername(fullname: String){
    return fullname.replace(/\s/g, '').toLowerCase();
  }
  onSubmitRegister(): void {
    if (this.registerFormData.password !== this.registerFormData.confirmPassword) {
      this.errorMessage = "Error registering user. Passwords do not match";
      return;
    }
    this.axiosService.request(
      "POST",
      "/register",
      {
        "fullname": this.registerFormData.fullname,
        "email": this.registerFormData.email,
        "password": this.registerFormData.password,
        "role": "USER"
      },
      false
    ).then((response) => {
      if (response.status === 200 && response.data.token !== undefined && response.data.token !== null && response.data.token !== "") {
        const token = response.data.token;
        localStorage.setItem("authToken", token);
        this.router.navigate(["create-post"])
          .then(
          )
      } else  {
        this.errorMessage = "Error registering user." + response.data.message
      }
    }).catch((error) => {
      console.log(error)
      this.errorMessage = "Error registering user."
    })
  }


}

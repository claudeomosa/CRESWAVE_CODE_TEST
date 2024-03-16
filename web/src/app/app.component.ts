import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {FormsModule} from "@angular/forms";
import {AxiosService} from "./axios.service";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthInterceptor} from "./auth.inceptors";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    FormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
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
    {},
      true
    ).then((response) => {
      if (response.status === 200){
        localStorage.removeItem("authToken");
        this.router.navigate([""]).then()
      }
    })
  }
}

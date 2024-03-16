import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AxiosService} from "../axios.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-post',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})
export class CreatePostComponent {

constructor(private axiosService: AxiosService, private router: Router) { }
  blogPostData = {
    "title": "",
    "content": "",
    "creationDate": "",
  }

  onSubmitBlogPost() {
    this.axiosService.request(
      "POST",
      "/api/posts",
      {
        "title": this.blogPostData.title,
        "content": this.blogPostData.content,
        "creationDate": new Date().toISOString(),
      },
      true
    ).then((response) => {
      if (response.status === 200) {
        this.router.navigate(["/"])
          .then(
          )
      }
    })

  }
}

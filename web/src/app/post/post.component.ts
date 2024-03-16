import { Component } from '@angular/core';
import {BlogPost} from "../shared/blogPost.interface";
import {AxiosService} from "../axios.service";
import {ActivatedRoute, Params} from "@angular/router";
import {NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

interface Comment {
  id: number;
  text: string;
  user: {
    fullname: string;
    username: string;
  }
}
@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent {
  currentPost: BlogPost = {} as BlogPost;
  currentBlogPostComments: any[] = [];
  currentPostId: string = "";
  constructor(private axiosService: AxiosService, private activatedRoute: ActivatedRoute) { }
  ngOnInit() {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.currentPostId = params['id'];
    });

    this.axiosService.request(
      "GET",
      `/api/posts/${this.currentPostId}`,
      {}
    ).then((response) => {
      this.currentPost = response.data;
      console.log(this.currentPost, "currentPost")
    }).catch((error) => {
      console.error(error);
    });

    this.axiosService.request(
      "GET",
      `/api/posts/${this.currentPostId}/comments`,
      {}
    ).then((response) => {
      this.currentBlogPostComments = response.data;
      console.log(this.currentBlogPostComments, "currentBlogPostComments")
    }).catch((error) => {
      console.error(error);
    });
  }
  onSubmitComment() {
    this.axiosService.request(
      "POST",
      `/api/posts/${this.currentPostId}/comments`,
      {
        "text": "this is a comment",
        "user_id": "1"
      }
    )
  }

}

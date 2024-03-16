import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';
import {DatePipe, NgFor} from '@angular/common';
import {RouterLink} from "@angular/router";
import {BlogPost} from "../shared/blogPost.interface";

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [
    NgFor,
    DatePipe,
    RouterLink
  ],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {
  blogPosts: BlogPost[] = [];

  constructor(private axiosService: AxiosService) {

  }

  ngOnInit() {
    this.axiosService.request(
      "GET",
      "api/posts",
      {}
    ).then((response) => {
      this.blogPosts = response.data;
    }).catch((error) => {
      console.error(error);
    });
  }

}

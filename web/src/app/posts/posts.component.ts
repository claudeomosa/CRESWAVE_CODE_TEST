import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [
    NgFor
  ],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {
  blogPosts: Object[] = [];

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
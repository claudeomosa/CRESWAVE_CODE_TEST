import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [],
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
      "/posts",
      {}
    ).then((response) => {
      this.blogPosts = response.data;
    }).catch((error) => {
      console.error(error);
    });
  }

}
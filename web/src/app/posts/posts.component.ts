import { Component } from '@angular/core';

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.css'
})
export class PostsComponent {

}
// this class will be used to fedctch the data from the server, all blog posts it will maybe use a service to fetch the data
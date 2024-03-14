import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { PostsComponent } from './posts/posts.component';
import { PostComponent } from './post/post.component';
import { CreatePostComponent } from './create-post/create-post.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: '', component: PostsComponent},
    {path: "posts/:id", component: PostComponent},
    {path: "create-post", component: CreatePostComponent},
];

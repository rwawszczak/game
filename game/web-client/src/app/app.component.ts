import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'game';
  navLinks = [
    {label: 'List Users', path: 'users'},
    {label: 'Add User', path: 'add'}];
}

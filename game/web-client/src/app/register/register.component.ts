import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {User} from '../_models';
import {AuthenticationService} from '../_services';
import {FormControl, Validators} from '@angular/forms';

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  error = '';
  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  usernameFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(15),
  ]);
  passwordFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(20),
  ]);

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }


  ngOnInit() {
    // reset login status
    this.authenticationService.logout();
  }

  createUser(): void {
    this.authenticationService.register(this.user)
      .subscribe(
        data => {
          this.router.navigate(['login']);
        },
        error => {
          this.error = error;
        });

  }

}

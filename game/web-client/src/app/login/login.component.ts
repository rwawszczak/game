import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AuthenticationService} from '../_services';
import {Login} from '../_models';
import {MatSnackBar} from '@angular/material';

@Component({
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login: Login = new Login();

  usernameOrEmailFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);
  passwordFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);

  loginForm: FormGroup;
  returnUrl: string;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar,
    private authenticationService: AuthenticationService) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // reset login status
    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  loginUser() {
    if (this.isFormInvalid()) {
      return;
    }
    this.authenticationService.login(this.login)
      .pipe(first())
      .subscribe(
        () => {
          this.snackBar.dismiss();
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.error = error;
          this.snackBar.open('Username, email and/or password is invalid.', null, {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
        });
  }

  isFormInvalid() {
    return this.usernameOrEmailFormControl.invalid || this.passwordFormControl.invalid;
  }
}

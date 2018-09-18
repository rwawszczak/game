import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {User} from '../_models';
import {AuthenticationService} from '../_services';
import {AbstractControl, FormControl, ValidatorFn, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material';

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
  passwordConfirmFormControl: FormControl = new FormControl('', [
    Validators.required,
    this.matchingPasswordValidator()
  ]);
  passwordConfirm: String;

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private snackBar: MatSnackBar) {
  }


  ngOnInit() {
    this.authenticationService.logout();
  }

  registerUser(): void {
    if (this.isFormInvalid()) {
      return;
    }
    this.authenticationService.register(this.user)
      .subscribe(
        () => {
          this.snackBar.dismiss();
          this.router.navigate(['login']);
        },
        error => {
          this.error = error;
          this.snackBar.open(error, null, {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
        });

  }
  matchingPasswordValidator(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const notMatchingPassword = control.value !== this.user.password;
      return notMatchingPassword ? {'notMatchingPassword': {value: control.value}} : null;
    };
  }

  isFormInvalid() {
    return this.emailFormControl.invalid || this.usernameFormControl.invalid ||
      this.passwordFormControl.invalid || this.passwordConfirmFormControl.invalid;
  }

}

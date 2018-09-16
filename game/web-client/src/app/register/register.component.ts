import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {User} from '../_models';
import {AuthenticationService} from '../_services';
import {FormControl, Validators} from '@angular/forms';

@Component({
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  error = '';
  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor(
    private router: Router,
    private route: ActivatedRoute,
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

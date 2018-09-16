import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {User} from '../_models';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private userUrl = '/api';

  constructor(private http: HttpClient) {
  }

  login(usernameOrEmail: string, password: string) {
    return this.http.post<any>(this.userUrl + `/auth/signin`, {usernameOrEmail, password})
      .pipe(map(user => {
        // login successful if there's a jwt token in the response
        if (user && user.accessToken) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }

        return user;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  public register(user) {
    return this.http.post<User>(this.userUrl + '/auth/signup', user);
  }

}

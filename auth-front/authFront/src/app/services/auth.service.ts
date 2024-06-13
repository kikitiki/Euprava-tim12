import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';
import { LoginRequestPayload } from '../components/login/login-request.payload';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

  private acces_token = '';

  headers = new HttpHeaders({
    Accept: 'application/json',
    'Content-Type': 'application/json',
  });

  constructor(private http: HttpClient, private route: Router) {}

  getDecodeAccessToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (Error) {
      return null;
    }
  }

  login(user: LoginRequestPayload): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/auth/login',
      JSON.stringify(user),
      { headers: this.headers, responseType: 'text' }
    );
  }

  getJwtToken() {
    return localStorage.getItem('jwt');
  }

  getRole(): string {
    return localStorage.getItem('role')!;
  }

  getUserName() {
    return localStorage.getItem('username');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null && this.getJwtToken() != undefined;
  }
}

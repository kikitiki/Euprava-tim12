import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import { SecurityService } from './service/security.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'dom-front';
  isStudent: boolean = false;
  isUpravnik: boolean = false;

  constructor(private securityService: SecurityService, private router: Router) {}

  ngOnInit() {
    // Proveri da li je korisnik prijavljen
    if (this.securityService.isLoggedIn()) {
      // Uzmi JWT token iz localStorage
      const token = this.securityService.getJwtToken();

      if (token) {
        // Odredi ulogu korisnika
        const role = this.securityService.getRole(token);
        this.isStudent = (role === 'student');
        this.isUpravnik = (role === 'upravnik');

        // Preusmeri korisnika na odgovarajuću početnu stranicu
        if (this.isStudent) {
          this.router.navigate(['/student-home']);
        } else if (this.isUpravnik) {
          this.router.navigate(['/upravnik-home']);
        }
      }
    } else {
      // Ako korisnik nije prijavljen, preusmeri na stranicu za prijavu ili neku početnu stranicu
      // this.router.navigate(['/login']);
    }
  }
}

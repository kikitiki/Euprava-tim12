import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  role: string | undefined;

  constructor(
    private authService: AuthService,
    private activateRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getRole();

    console.log('ROLE : ' + this.role);
  }

  isRole(): any {
    if (this.authService.getRole() == 'ROLE_DOKTOR') {
      return 'doktor';
    } else if (this.authService.getRole() == 'ROLE_STUDENT') {
      return 'student';
    } else {
      return 'gradjanin';
    }
  }
}

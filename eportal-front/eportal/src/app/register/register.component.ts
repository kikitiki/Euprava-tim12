import { Component } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(registerForm: any) {
    this.authService.register(registerForm.value)
      .subscribe({
        next: () => this.router.navigate(['/']),
        error: (err) => alert('Greška pri registraciji: ' + err.error)
      });
  }
}

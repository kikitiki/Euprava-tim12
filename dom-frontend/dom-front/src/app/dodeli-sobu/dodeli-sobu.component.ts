import { Component } from '@angular/core';
import { KonkursService } from "../service/konkurs.service";
import { Router, ActivatedRoute } from "@angular/router";
import { SecurityService } from "../service/security.service";
import {HttpErrorResponse} from "@angular/common/http";  // Dodao sam import za SecurityService

@Component({
  selector: 'app-dodeli-sobu',
  templateUrl: './dodeli-sobu.component.html',
  styleUrls: ['./dodeli-sobu.component.css']
})
export class DodeliSobuComponent {
  username: string = '';
  sobaId: number = 0;
  message: string | null = null;

  constructor(
    private konkursService: KonkursService,
    private router: Router,
    private securityService: SecurityService
  ) {}

  submitForm() {
    this.konkursService.dodeliSobu(this.username, this.sobaId).subscribe(
      (response: any) => {
        this.message = response; // Prikazuje poruku iz backend-a
        const currentToken = this.securityService.getJwtToken();
        if (currentToken) {
          const targetUrl = `/upravnik/${currentToken}`;
          this.router.navigateByUrl(targetUrl);
        } else {
          console.error('Nedostaje parametar token u trenutnoj ruti.');
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Greška prilikom dodeljivanja sobe:', error); // Dodajte ovo za dodatne informacije o grešci
        if (error.status === 400) {
          this.message = error.error; // Prikazuje poruku greške iz backend-a
        } else if (error.status === 404) {
          this.message = 'Student ili soba nisu pronađeni.';
        } else if (error.status === 500) {
          this.message = error.error; // Prikazuje poruku greške iz backend-a
        } else {
          this.message = 'Neprepoznata greška.';
        }
      }
    );
  }}

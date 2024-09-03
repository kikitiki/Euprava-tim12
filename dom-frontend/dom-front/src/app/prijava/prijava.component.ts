import { Component } from '@angular/core';
import { KonkursService } from '../service/konkurs.service';
import { StudentDTO } from '../model/StudentDTO';
import { SecurityService } from '../service/security.service';
import {Router} from "@angular/router";


@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.css']
})
export class PrijavaComponent {
  jmbg: string = '';
  student?: StudentDTO;
  konkursId: number | undefined;
  successMessage?: string;
  errorMessage?: string;

  constructor(private konkursService: KonkursService, private router: Router, private securityService: SecurityService) { }

  searchStudent() {
    this.konkursService.getStudentByJmbg(this.jmbg).subscribe(
      data => {
        console.log('Podaci o studentu:', data);
        this.student = data;
      },
      error => {
        console.error('Greška prilikom pretrage studenta:', error);
        this.errorMessage = 'Došlo je do greške prilikom pretrage studenta.';
      }
    );
  }

  applyForCompetition() {
    if (this.jmbg && this.konkursId) {
      const studentDTO: StudentDTO = {
        jmbg: this.jmbg,
        konkursId: this.konkursId
      };
      console.log('StudentDTO za prijavu:', studentDTO);

      this.konkursService.prijaviStudentaNaKonkurs(studentDTO).subscribe(
        (response: any) => {
          this.successMessage = response;  // Pretpostavljamo da response sadrži poruku
          this.errorMessage = undefined;  // Očisti poruku o grešci

          // Dobijanje trenutnog tokena iz securityService
          const currentToken = this.securityService.getJwtToken();
          if (currentToken) {
            const targetUrl = `/student/${currentToken}`;
            this.router.navigateByUrl(targetUrl);
          } else {
            console.error('Nedostaje parametar token u trenutnoj ruti.');
          }
        },
        error => {
          this.errorMessage = error.error || 'Došlo je do greške prilikom prijave.';
          this.successMessage = undefined; // Očisti poruku o uspehu
        }
      );
    } else {
      this.errorMessage = 'Molimo Vas da unesete sve potrebne informacije.';
    }
  }}

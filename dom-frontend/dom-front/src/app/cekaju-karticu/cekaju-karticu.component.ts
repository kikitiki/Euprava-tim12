import {Component, OnInit} from '@angular/core';
import {Student} from "../model/Student";
import {KonkursService} from "../service/konkurs.service";
import {SecurityService} from "../service/security.service";
import {StudentDTO} from "../model/StudentDTO";
import {Router} from "@angular/router";


@Component({
  selector: 'app-cekaju-karticu',
  templateUrl: './cekaju-karticu.component.html',
  styleUrls: ['./cekaju-karticu.component.css']
})
export class CekajuKarticuComponent implements OnInit {
  studentiKojiCekajuKarticu: StudentDTO[] = [];
  isUpravnik: boolean = false;
  message: string = ''; // Dodaj ovu varijablu


  constructor(
    private konkursService: KonkursService,
    private securityService: SecurityService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const token = this.securityService.getJwtToken();
    if (token) {
      const role = this.securityService.getRole(token);
      console.log('ROLE:', role);
      this.isUpravnik = role === 'ROLE_UPRAVNIK';
    }

    this.konkursService.getStudentiKojiCekajuKarticu().subscribe(data => {
      this.studentiKojiCekajuKarticu = data;
      console.log('Studenti koji čekaju karticu:', this.studentiKojiCekajuKarticu);
    });
  }

  azurirajKarticu(jmbg: string | undefined): void {
    if (jmbg) {
      this.konkursService.azurirajKarticu(jmbg).subscribe(
        response => {
          console.log('Odgovor sa servera:', response);
          this.message = response; // Prikazuje poruku iz backend-a
          const currentToken = this.securityService.getJwtToken();
          if (currentToken) {
            const targetUrl = `/upravnik/${currentToken}`;
            this.router.navigateByUrl(targetUrl);
          } else {
            console.error('Nedostaje parametar token u trenutnoj ruti.');
          }
        },
        error => {
          console.error('Greška prilikom ažuriranja kartice:', error);
          alert('Došlo je do greške prilikom ažuriranja kartice.');
        }
      );
    } else {
      console.error('JMBG nije definisan.');
    }
  }


  goBack(){
    this.router.navigate(['upravnik',localStorage.getItem('jwt')]);
  }
}

import { Component } from '@angular/core';
import { KonkursService } from '../service/konkurs.service';
import { StudentDTO } from '../model/StudentDTO';
import { SecurityService } from '../service/security.service';
import {Router} from "@angular/router";

// @Component({
//   selector: 'app-prijava',
//   templateUrl: './prijava.component.html',
//   styleUrls: ['./prijava.component.css'],
// })
// export class PrijavaComponent {
// //   studentDTO: StudentDTO = {};
// //
// //   constructor(private konkursService: KonkursService, private router: Router) {}
// //
// //   onSubmit() {
// //     this.konkursService.prijaviStudentaNaKonkurs(this.studentDTO)
// //       .pipe(
// //         catchError((error) => {
// //           console.error('Greška prilikom prijavljivanja na konkurs:', error);
// //           // Obradite grešku, prikažite poruku o grešci, itd.
// //           // Vratite observable sa podacima o grešci kako bi se moglo nastaviti praćenje
// //           return of(null);
// //         })
// //       )
// //       .subscribe((result) => {
// //         if (result !== null) {
// //           console.log('Uspešno ste se prijavili na konkurs!');
// //           // Dodajte bilo koju dodatnu logiku ili obaveštenja ovde
// //
// //           // Nakon uspešne prijave, preusmerite korisnika na željenu stranicu
// //           this.router.navigate(['/rang-lista']);
// //         }
// //       });
// //   }
// // }
//
//   jmbg: string = '';
//   student?: StudentDTO;
//   konkursId: number | undefined;
//   successMessage?: string;
//   errorMessage?: string;
//
//   constructor(private konkursService: KonkursService, private router: Router) { }
//
//   searchStudent() {
//     this.konkursService.getStudentByJmbg(this.jmbg).subscribe(
//       data => this.student = data,
//       error => this.errorMessage = error
//     );
//   }
//
//   applyForCompetition() {
//     if (this.jmbg && this.konkursId) {
//       // Napravi DTO koristeći jmbg i konkursId
//       const studentDTO: StudentDTO = {
//         jmbg: this.jmbg,
//         konkursId: this.konkursId
//       };
//       console.log('StudentDTO za prijavu:', studentDTO); // Debug linija da proveriš formirani objekat
//       this.konkursService.prijaviStudentaNaKonkurs(studentDTO).subscribe(
//         message => {
//           this.successMessage = message;
//           this.router.navigate(['/rang-lista']);
//         },
//         error => {
//           if (error.status === 400) {
//             this.errorMessage = error.error; // Prikaz greške koju vraća backend
//           } else {
//             this.errorMessage = 'Greška prilikom prijavljivanja na konkurs.';
//           }
//         }
//       );
//     } else {
//       this.errorMessage = 'Nema podataka o JMBG ili Konkurs ID. Unesite JMBG i odaberite konkurs.';
//     }
//   }
// }

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

  constructor(private konkursService: KonkursService, private router: Router) { }

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
        message => {
          this.successMessage = message;
          this.errorMessage = undefined; // Očisti poruku o grešci
          this.router.navigate(['/rang-lista']);
        },
        error => {
          this.errorMessage = error; // Prikaži grešku
          this.successMessage = undefined; // Očisti poruku o uspehu
        }
      );
    } else {
      this.errorMessage = 'Molimo Vas da unesete sve potrebne informacije.';
    }
  }
}

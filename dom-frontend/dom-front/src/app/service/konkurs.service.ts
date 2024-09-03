import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {  StudentDTO } from '../model/StudentDTO'; // Importujte vaše DTO modele
import {  KonkursDTO } from '../model/KonkursDTO';
import {Student} from "../model/Student"; // Importujte vaše DTO modele

@Injectable({
  providedIn: 'root',
})
export class KonkursService {
  private baseUrl = 'http://localhost:9000/api/konkursi';

  constructor(private http: HttpClient) {}

  getAllKonkursi(): Observable<KonkursDTO[]> {
    return this.http.get<KonkursDTO[]>(this.baseUrl);
  }


  prijaviStudentaNaKonkurs(studentDTO: StudentDTO): Observable<string> {
    return this.http.post(`${this.baseUrl}/prijavi-se-na-konkurs`, studentDTO, { responseType: 'text' })
      .pipe(
        catchError(error => {
          console.error('Greška prilikom prijavljivanja:', error);
          return throwError('Greška prilikom prijavljivanja na konkurs');
        })
      );
  }

  getStudentByJmbg(jmbg: string): Observable<StudentDTO> {
    return this.http.get<StudentDTO>(`http://localhost:9000/api/konkursi/by-jmbg/${jmbg}`);
  }

  getRangLista(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/rang-lista`);
  }

  getRangListaSoba(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/rang-lista-soba`);
  }

  getStudentiKojiCekajuKarticu(): Observable<StudentDTO[]> {
    return this.http.get<StudentDTO[]>(`${this.baseUrl}/cekaju-karticu`)
      .pipe(
        catchError(error => {
          console.error('Greška prilikom dohvatanja studenata koji čekaju karticu:', error);
          return throwError('Došlo je do greške prilikom dohvatanja studenata.');
        })
      );
  }


  azurirajKarticu(jmbg: string): Observable<string> {
    const body = { jmbg };

    return this.http.post<string>(`${this.baseUrl}/azuriraj-karticu`, body, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text' as 'json'
    }).pipe(
      catchError(error => {
        console.error('Greška prilikom ažuriranja kartice:', error);
        return throwError('Došlo je do greške prilikom ažuriranja kartice.');
      })
    );
  }



  dodeliSobu(username: string, sobaId: number): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/dodeli-sobu?username=${username}&sobaId=${sobaId}`, null, {
      responseType: 'text' as 'json'
    }).pipe(
      catchError(error => {
        console.error('Greška prilikom dodeljivanja sobe:', error);
        return throwError('Došlo je do greške prilikom dodeljivanja sobe.');
      })
    );
  }
  getSobaInfoByUsername(username: string): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${username}/soba-info`);
  }
}

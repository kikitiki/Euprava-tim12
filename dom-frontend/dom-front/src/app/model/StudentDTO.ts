export class StudentDTO {
  id?: number;
  username?: string;
  password?: string;
  ime?: string;
  prezime?: string;
  jmbg?: string;
  godinaStudiranja?: number;
  osvojeniBodovi?: number;
  prosek?: number;
  kartica?: string; // Ako je kartica enum, trebaš koristiti odgovarajući tip
  bodovi?: number;
  konkursId?: number; // Ako backend koristi Long, možeš ostaviti kao number ako frontend ne koristi Long
  sobaId?: number; // Ako backend koristi Long, možeš ostaviti kao number ako frontend ne koristi Long
}

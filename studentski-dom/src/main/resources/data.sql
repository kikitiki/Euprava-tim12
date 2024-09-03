INSERT INTO dom (id, naziv) VALUES
(1, 'Dom A'),
(2, 'Dom B'),
(3, 'Dom C');

INSERT INTO konkurs (id, grad, skolska_godina, opis, datum_pocetka, datum_zavrsetka) VALUES
(3, 'Beograd', '2024/2025', 'Opis konkursa za Beograd', '2024-09-01', '2024-09-30'),
(4, 'Novi Sad', '2024/2025', 'Opis konkursa za Novi Sad', '2024-09-01', '2024-09-30');


INSERT INTO soba (id, broj_sobe, dom_id) VALUES
(1, '101', 1),
(2, '201', 2),
(3, '301', 3);


INSERT INTO student (username, password, ime, prezime, jmbg, godina_studiranja, osvojeni_bodovi, prosek, bodovi, kartica, soba_id, konkurs_id) VALUES
('maja123', 'maja123', 'Marko', 'Marković', '1234567890123', 2, 120, 8.5, 150.0, 'BUDZET', 1, 1),
('student2', 'password2', 'Ana', 'Anić', '9876543210123', 3, 90, 7.0, 100.0, 'BUDZET', 2, NULL);

INSERT INTO upravnik (id, username, password, ime, prezime, jmbg) VALUES
(1, 'admin', 'admin123', 'Admin', 'Adminović', '1111222233334');


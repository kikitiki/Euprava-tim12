package com.ftn.euprava.dom.controller;

import com.ftn.euprava.dom.dto.KonkursDTO;
import com.ftn.euprava.dom.dto.StudentDTO;
import com.ftn.euprava.dom.model.Kartica;
import com.ftn.euprava.dom.model.Soba;
import com.ftn.euprava.dom.model.Student;
import com.ftn.euprava.dom.repository.SobaRepository;
import com.ftn.euprava.dom.repository.StudentRepository;
import com.ftn.euprava.dom.service.KonkursService;
import com.ftn.euprava.dom.service.SobeService;
import com.ftn.euprava.dom.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/konkursi")
public class KonkursController {

    @Autowired
    private KonkursService konkursService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SobeService sobeService;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private SobaRepository sobaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String DOKTOR_SERVICE_URL = "http://localhost:9008/api/doktor";


    @GetMapping
//    @PreAuthorize("hasRole('STUDENT')")

    public ResponseEntity<List<KonkursDTO>> getAllKonkursi() {
        List<KonkursDTO> konkursi = konkursService.getAllKonkursi();

        if (konkursi != null && !konkursi.isEmpty()) {
            return ResponseEntity.ok(konkursi);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/by-jmbg/{jmbg}")
    public ResponseEntity<StudentDTO> getStudentByJmbg(@PathVariable String jmbg) {
        return studentService.getStudentByJmbg(jmbg)
                .map(studentDTO -> ResponseEntity.ok(studentDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/prijavi-se-na-konkurs")
    public ResponseEntity<String> prijaviStudentaNaKonkurs(@RequestBody StudentDTO studentDTO) {
        try {
            studentService.prijaviStudentaNaKonkurs(studentDTO);
            return ResponseEntity.ok("Student je uspešno prijavljen na konkurs.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//    @PostMapping("/prijavi-se-na-konkurs")
//    public ResponseEntity<String> prijaviSeNaKonkurs(@RequestBody StudentDTO studentDTO) {
//        try {
//            studentService.prijaviStudentaNaKonkurs(studentDTO);
//            return new ResponseEntity<>("Student je uspešno prijavljen na konkurs.", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }


    @GetMapping("/rang-lista")
    public List<Object> getRangLista() {
        List<Student> rangLista = studentRepository.findByBodoviGreaterThanOrderByBodoviDesc(0);

        // Kreiranje liste koja može da sadrži različite tipove objekata (Student i String za crtu)
        List<Object> result = new ArrayList<>();

        for (int i = 0; i < rangLista.size(); i++) {
            result.add(rangLista.get(i));
            if (i == 2) { // Posle trećeg studenta dodaj crtu
                result.add("----");
            }
        }

        return result;
    }


    @GetMapping("/rang-lista-soba")
    public ResponseEntity<List<Student>> getRangListaSobaNull() {
        List<Student> rangLista = studentRepository.findByBodoviGreaterThanAndSobaIsNull(0);

        if (rangLista != null && !rangLista.isEmpty()) {
            return ResponseEntity.ok(rangLista);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/cekaju-karticu")
    public ResponseEntity<List<Student>> getStudentiKojiCekajuKarticu() {
        try {
            List<Student> studenti = studentRepository.findAll().stream()
                    .filter(student -> !Kartica.BUDZET.equals(student.getKartica()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(studenti);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/azuriraj-karticu")
    public ResponseEntity<String> azurirajKarticu(@RequestBody Map<String, String> body) {
        String jmbg = body.get("jmbg");
        try {
            Optional<Student> studentOptional = studentRepository.findByJmbg(jmbg);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                student.setKartica(Kartica.BUDZET);
                studentRepository.save(student);
                return ResponseEntity.ok("Kartica uspešno ažurirana.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student sa JMBG-om " + jmbg + " nije pronađen.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška: " + e.getMessage());
        }
    }

//    @PostMapping("/dodeli-sobu")
//    public ResponseEntity<Void> dodeliSobu(@RequestParam String username, @RequestParam Long sobaId) {
//        try {
//            Optional<Student> studentOptional = studentRepository.findByUsername(username);
//            Optional<Soba> sobaOptional = sobaRepository.findById(sobaId);
//
//            if (studentOptional.isPresent() && sobaOptional.isPresent()) {
//                Student student = studentOptional.get();
//
//                // Proverite da li student ima "BUDZET" karticu
//                if (!Kartica.BUDZET.equals(student.getKartica())) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Status 400 za nedostatak kartice
//                }
//
//                Soba soba = sobaOptional.get();
//                student.setSoba(soba);
//
//                studentRepository.save(student);
//
//                return ResponseEntity.ok().build(); // Status 200 za uspešno dodeljivanje sobe
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 za student ili soba nisu pronađeni
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Status 500 za grešku na serveru
//        }
//    }

    @GetMapping("/{username}/soba-info")
    public ResponseEntity<Student> getSobaInfoByUsername(@PathVariable String username) {
        try {
            Optional<Student> studentOptional = studentRepository.findByUsername(username);

            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                Soba soba = student.getSoba();

                if (soba != null) {
                    return ResponseEntity.ok(student);
                }
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/dodeli-sobu")
    public ResponseEntity<String> dodeliSobu(@RequestParam String username, @RequestParam Long sobaId) {
        try {
            Optional<Student> studentOptional = studentRepository.findByUsername(username);
            Optional<Soba> sobaOptional = sobaRepository.findById(sobaId);

            if (!studentOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Student sa korisničkim imenom " + username + " nije pronađen.");
            }

            if (!sobaOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Soba sa ID-jem " + sobaId + " nije pronađena.");
            }

            Student student = studentOptional.get();
            Soba soba = sobaOptional.get();

            // Proveri lekarski pregled prema JMBG
            Boolean imaLekarski = proveriLekarski(student.getJmbg());

            if (imaLekarski == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Greška u komunikaciji sa spoljnim servisom za lekarski pregled.");
            }

            if (!imaLekarski) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Student nema validan lekarski pregled.");
            }

            // Proveri da li student ima "BUDZET" karticu
            if (!Kartica.BUDZET.equals(student.getKartica())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Student nema 'BUDZET' karticu.");
            }

            student.setSoba(soba);
            studentRepository.save(student);

            return ResponseEntity.ok("Soba je uspešno dodeljena studentu.");
        } catch (Exception e) {
            // Log grešku za internu svrhu
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Greška na serveru: " + e.getMessage());
        }
    }

    private Boolean proveriLekarski(String jmbg) {
        String url = DOKTOR_SERVICE_URL + "/lekarski?jmbg=" + jmbg;
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
            return response.getBody();
        } catch (Exception e) {
            // Logovanje greške
            System.err.println("Greška u komunikaciji sa spoljnim servisom za lekarski pregled: " + e.getMessage());
            throw new RuntimeException("Greška pri proveri lekarskog pregleda", e);
        }
    }

    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody StudentDTO studentDTO) {
        try {
            studentService.registerStudent(studentDTO);
            return ResponseEntity.ok("Student uspešno registrovan!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


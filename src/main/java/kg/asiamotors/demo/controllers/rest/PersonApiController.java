package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.services.api.PersonApiService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonApiController {

    private final PersonApiService personApiService;

    public PersonApiController(PersonApiService personApiService) {
        this.personApiService = personApiService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personApiService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable int id) {
        PersonDTO personDTO = personApiService.getPersonById(id);
        if (personDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personApiService.createPerson(personDTO);
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable int id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personApiService.updatePerson(id, personDTO);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        if (!personApiService.deletePerson(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<PersonDTO>> searchPersonsByName(@RequestParam String name) {
        List<PersonDTO> persons = personApiService.searchPersonsByName(name);
        return ResponseEntity.ok(persons);
    }
    @GetMapping("page")
    public ResponseEntity<Page<PersonDTO>> getAllPersonDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(personApiService.getAllPersonDto(offset, limit));
    }
}

package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "Person")
public class PersonApiController {

    private final PersonService personService;

    public PersonApiController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Получение списка персонажа")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение персонажа по id")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable int id) {
        PersonDTO personDTO = personService.getPersonById(id);
        if (personDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping
    @Operation(summary = "Создание персонажа")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.createPerson(personDTO);
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить персонажа")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable int id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить персонажа")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        if (!personService.deletePerson(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    @Operation(summary = "Поиск персонажа по имени")
    public ResponseEntity<List<PersonDTO>> searchPersonsByName(@RequestParam String name) {
        List<PersonDTO> persons = personService.searchPersonsByName(name);
        return ResponseEntity.ok(persons);
    }
    @GetMapping("page")
    @Operation(summary = "Получение списка персонажей с пагинацией")
    public ResponseEntity<Page<PersonDTO>> getAllPersonDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(personService.getAllPersonDto(offset, limit));
    }
}

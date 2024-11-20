package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonApiController {
    private final PersonService personService;

    public PersonApiController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.findAll()
                .stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable int id) {
        Person person = personService.findOne(id);
        if (person != null) {
            return ResponseEntity.ok(new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        Person person = new Person(personDTO.getName(), personDTO.getAge(), personDTO.getEmail());
        personService.save(person);
        return ResponseEntity.ok(new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable int id, @RequestBody PersonDTO personDTO) {
        Person existingPerson = personService.findOne(id);
        if (existingPerson != null) {
            existingPerson.setName(personDTO.getName());
            existingPerson.setAge(personDTO.getAge());
            existingPerson.setEmail(personDTO.getEmail());
            personService.save(existingPerson);
            return ResponseEntity.ok(new PersonDTO(existingPerson.getId(), existingPerson.getName(), existingPerson.getAge(), existingPerson.getEmail()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        Person existingPerson = personService.findOne(id);
        if (existingPerson != null) {
            personService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

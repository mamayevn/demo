package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.repository.PersonRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "persons", key = "#id")
    public PersonDTO getPersonById(int id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Человек с id " + id + " не найден"));
        return new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail());
    }

    @CachePut(value = "persons", key = "#result.id")
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());
        Person savedPerson = personRepository.save(person);
        return new PersonDTO(savedPerson.getId(), savedPerson.getName(), savedPerson.getAge(), savedPerson.getEmail());
    }

    @CachePut(value = "persons", key = "#id")
    public PersonDTO updatePerson(int id, PersonDTO personDTO) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Человек с id " + id + " не найден"));

        existingPerson.setName(personDTO.getName());
        existingPerson.setAge(personDTO.getAge());
        existingPerson.setEmail(personDTO.getEmail());
        Person updatedPerson = personRepository.save(existingPerson);
        return new PersonDTO(updatedPerson.getId(), updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail());
    }

    @CacheEvict(value = "persons", key = "#id")
    public boolean deletePerson(int id) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Человек с id " + id + " не найден"));
        personRepository.deleteById(id);
        return true;
    }

    public List<PersonDTO> searchPersonsByName(String name) {
        List<Person> persons = personRepository.findByNameContainingIgnoreCase(name);
        return persons.stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()))
                .collect(Collectors.toList());
    }

    public Page<PersonDTO> getAllPersonDto(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return personRepository.findAllPersonDtos(pageable);
    }
}


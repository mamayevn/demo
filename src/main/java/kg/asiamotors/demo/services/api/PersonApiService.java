package kg.asiamotors.demo.services.api;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.repasitories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonApiService {

    private final PersonRepository personRepository;

    public PersonApiService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()))
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(int id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) {
            return null;
        }
        return new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail());
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());
        Person savedPerson = personRepository.save(person);
        return new PersonDTO(savedPerson.getId(), savedPerson.getName(), savedPerson.getAge(), savedPerson.getEmail());
    }

    public PersonDTO updatePerson(int id, PersonDTO personDTO) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson == null) {
            return null;
        }
        existingPerson.setName(personDTO.getName());
        existingPerson.setAge(personDTO.getAge());
        existingPerson.setEmail(personDTO.getEmail());
        Person updatedPerson = personRepository.save(existingPerson);
        return new PersonDTO(updatedPerson.getId(), updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail());
    }

    public boolean deletePerson(int id) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson == null) {
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }
    public List<PersonDTO> searchPersonsByName(String name) {
        List<Person> persons = personRepository.findByNameContainingIgnoreCase(name);
        return persons.stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getAge(), person.getEmail()))
                .collect(Collectors.toList());
    }
    public Page<PersonDTO> getAllPersonDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return personRepository.findAllPersonDtos(pageable);
    }
}

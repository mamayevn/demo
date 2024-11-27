package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        if (personRepository.existsById(id)) {
            updatedPerson.setId(id);
            personRepository.save(updatedPerson);
        }
    }
    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }
    public List<Person> findByName(String name) {
        return personRepository.findByNameContainingIgnoreCase(name);
    }
}
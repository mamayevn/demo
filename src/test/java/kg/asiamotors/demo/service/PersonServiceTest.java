package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository);
    }

    @Test
    void testGetAllPersons() {
        Person person = new Person(1, "Нурс", 30, "mamaev@mail.com");
        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<PersonDTO> persons = personService.getAllPersons();

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("Нурс", persons.get(0).getName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetPersonById_Found() {
        Person person = new Person(1, "Нурс Мамаев", 29, "mamayevn@mail.ru");
        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        PersonDTO personDTO = personService.getPersonById(1);

        assertNotNull(personDTO);
        assertEquals("Нурс Мамаев", personDTO.getName());
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    void testGetPersonById_NotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personService.getPersonById(1));
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO(0, "Нурс1", 40, "nurs@gmail.com");
        Person person = new Person(1, "Нурс1", 40, "nurs@gmail.com");

        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDTO createdPerson = personService.createPerson(personDTO);

        assertNotNull(createdPerson);
        assertEquals("Нурс1", createdPerson.getName());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdatePerson() {
        Person existingPerson = new Person(1, "Нурс2", 30, "nurs@mail.com");
        PersonDTO personDTO = new PersonDTO(0, "Нурс3", 35, "nurs@mail.com");

        when(personRepository.findById(1)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        PersonDTO updatedPerson = personService.updatePerson(1, personDTO);

        assertNotNull(updatedPerson);
        assertEquals("Нурс3", updatedPerson.getName());
        assertEquals(35, updatedPerson.getAge());
        verify(personRepository, times(1)).findById(1);
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    void testDeletePerson_Found() {
        Person person = new Person(1, "Нурс", 30, "nurs@gmail.com");

        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        boolean result = personService.deletePerson(1);

        assertTrue(result);
        verify(personRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePerson_NotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personService.deletePerson(1));
        verify(personRepository, times(1)).findById(1);
        verify(personRepository, never()).deleteById(anyInt());
    }

    @Test
    void testSearchPersonsByName() {
        Person person = new Person(1, "Nurs Johnson", 28, "nurs.johnson@example.com");
        when(personRepository.findByNameContainingIgnoreCase("Nurs")).thenReturn(Collections.singletonList(person));

        List<PersonDTO> persons = personService.searchPersonsByName("Nurs");

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("Nurs Johnson", persons.get(0).getName());
        verify(personRepository, times(1)).findByNameContainingIgnoreCase("Nurs");
    }
}

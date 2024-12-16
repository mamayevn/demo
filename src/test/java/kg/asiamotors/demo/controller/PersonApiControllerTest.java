package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonApiControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonApiController personApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersons_ShouldReturnListOfPersons() {
        List<PersonDTO> persons = Arrays.asList(new PersonDTO(1, "Nurs", 30, "nurs@mail.com"));
        when(personService.getAllPersons()).thenReturn(persons);

        ResponseEntity<List<PersonDTO>> response = personApiController.getAllPersons();

        assertEquals(1, response.getBody().size());
        assertEquals("Nurs", response.getBody().get(0).getName());
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void createPerson_ShouldReturnCreatedPerson() {
        PersonDTO personDTO = new PersonDTO(0, "Nurs", 30, "nurs@mail.com");
        PersonDTO createdPerson = new PersonDTO(1, "Nurs", 30, "nurs@mail.com");
        when(personService.createPerson(personDTO)).thenReturn(createdPerson);

        ResponseEntity<PersonDTO> response = personApiController.createPerson(personDTO);

        assertEquals(1, response.getBody().getId());
        assertEquals("Nurs", response.getBody().getName());
        verify(personService, times(1)).createPerson(personDTO);
    }

    @Test
    void deletePerson_ShouldReturnNoContent() {
        when(personService.deletePerson(1)).thenReturn(true);

        ResponseEntity<Void> response = personApiController.deletePerson(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(personService, times(1)).deletePerson(1);
    }
}

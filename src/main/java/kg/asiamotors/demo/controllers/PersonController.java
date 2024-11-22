package kg.asiamotors.demo.controllers;

import jakarta.validation.Valid;
import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.services.CarService;
import kg.asiamotors.demo.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class PersonController {

    private final PersonService personService;
    private final CarService carService;

    public PersonController(PersonService personService, CarService carService) {
        this.personService = personService;
        this.carService = carService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("persons", personService.findAll());
        return "main";
    }

    @GetMapping("/{id}/persons_cars")
    public String showCars(@PathVariable("id") int id, Model model) {
        Person person = personService.findOne(id);
        if (person != null) {
            List<Car> cars = carService.findByPersonId(id);
            model.addAttribute("person", person);
            model.addAttribute("cars", cars);
        }
        return "persons_cars";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/main";
        }
        personService.save(person);
        return "redirect:/add_car";
    }

    @GetMapping("/list_all")
    public String list_all(Model model) {
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "list_all";
    }

    @GetMapping("/{id}/edit")
    public String updatePerson(Model model, @PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        person.setId(id);
        personService.update(id, person);
        return "redirect:/main";
    }

    @PostMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }
        person.setId(id);
        personService.update(id, person);
        return "redirect:/main";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/main";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/search")
    public String searchPerson(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value  = "id", required = false) Integer id,
                               Model model) {
        List<Person> foundPersons;

        if (name != null && !name.isEmpty()) {
            foundPersons = personService.findByName(name);
        } else if (id != null) {
            Person person = personService.findOne(id);
            foundPersons = person != null ? List.of(person) : Collections.emptyList();
        } else {
            foundPersons = Collections.emptyList();
        }

        model.addAttribute("persons", foundPersons);
        return "search_results";
    }
}
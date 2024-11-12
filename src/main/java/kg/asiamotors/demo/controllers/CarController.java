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

import java.util.List;

@Controller
public class CarController {
    private final CarService carService;
    private final PersonService personService;

    public CarController(CarService carService, PersonService personService) {
        this.carService = carService;
        this.personService = personService;
    }

    @GetMapping("/add_car")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Car());
        List<Person> persons = personService.findAll();
        if (persons.isEmpty()) {
            model.addAttribute("error", "Нет доступных владельцев!");
        }
        model.addAttribute("persons", persons);
        return "add_car";
    }

    @PostMapping("/add_car")
    public String saveCar(Car car, @RequestParam("personId") int personId) {
        Person person = personService.findOne(personId);
        if (person != null) {
            car.setPerson(person);
            carService.save(car);
        }
        return "redirect:/main";
    }


    @GetMapping("/list_all_car")
    public String findAll(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "list_all";
    }

    @GetMapping("/{id}/edit_car")
    public String update(Model model, @PathVariable("id") int id) {
        Car car = carService.findOne(id);
        if (car == null) {
            return "redirect:/main";
        }
        model.addAttribute("cars", car);
        return "main/edit_car";
    }

    @PostMapping("/{id}/edit_car")
    public String updateCar(@PathVariable("id") int id, @ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main/edit_car";
        }
        car.setId(id);
        carService.update(id, car);
        return "redirect:/list_all_car";
    }

    @PostMapping("/{id}/delete_car")
    public String deleteCar(@PathVariable("id") int id) {
        carService.delete(id);
        return "redirect:/main";
    }

    @GetMapping("/search_cars")
    public String searchCars(@RequestParam("brand") String brand, Model model) {
        List<Car> cars = carService.findByBrand(brand);
        model.addAttribute("cars", cars);
        return "list";
    }
}

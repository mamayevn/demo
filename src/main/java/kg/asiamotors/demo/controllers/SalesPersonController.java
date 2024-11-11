package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.services.SalesPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salespersons")
public class SalesPersonController {
    @Autowired
    private SalesPersonService salesPersonService;

    @GetMapping("/add")
    public String showAddSalespersonForm(Model model) {
        model.addAttribute("salesperson", new SalesPerson());
        return "add_salesperson";
    }

    @PostMapping("/add")
    public String addSalesperson(@ModelAttribute SalesPerson salesPerson) {
        salesPersonService.saveSalesperson(salesPerson);
        return "redirect:/salespersons/list";
    }

    @GetMapping("/list")
    public String listSalespersons(Model model) {
        model.addAttribute("salespersons", salesPersonService.findAllSalespersons());
        return "list_salespersons";
    }
}

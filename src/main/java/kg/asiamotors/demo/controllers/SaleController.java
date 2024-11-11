package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesPersonService salesPersonService;

    @Autowired
    private CarModelService carModelService;

    @GetMapping("/add")
    public String showAddSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("customers", customerService.findAllCustomers());
        model.addAttribute("salesPersons", salesPersonService.findAllSalespersons());
        model.addAttribute("carModels", carModelService.findAll());
        return "add_sale";
    }

    @PostMapping("/add")
    public String addSale(@ModelAttribute Sale sale, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_sale";
        }
        sale.setSaleDate(LocalDate.now());
        saleService.saveSale(sale);
        return "redirect:/sales/list";
    }

    @GetMapping("/list")
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.findAllSales());
        return "list_sales";
    }

}



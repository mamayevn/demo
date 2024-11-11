package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("sales", saleService.findAllSales());
        return "main_page";
    }
}

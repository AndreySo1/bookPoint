package by.tms.bookpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String index(Model model) {

        String environment = System.getenv("ENV_MODE");
        String baseUrl = "https://bookpoint-dev.up.railway.app";

        if (environment == "prod") {
            baseUrl = "https://bookpoint-production.up.railway.app";
        }

        model.addAttribute("baseUrl", baseUrl);
        return "index";
    }
}

package by.tms.bookpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private Environment environment;
    @GetMapping
    public String index(Model model) {

//        String environment = System.getenv("ENV_MODE");
        String env = environment.getProperty("spring.profiles.active");
        String baseUrl = "https://bookpoint-dev.up.railway.app";

        if (env == "prod") {
            baseUrl = "https://bookpoint-production.up.railway.app";
        }

        model.addAttribute("baseUrl", baseUrl);
        return "index";
    }
}

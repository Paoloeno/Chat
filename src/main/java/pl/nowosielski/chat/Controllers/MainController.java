package pl.nowosielski.chat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {




    @GetMapping("/")
    public String index(){


        if(true)
        /*
        Zamiast 'true':
        SPRAWDZENIE SESJI - jesli nie istnieje to wracaj do login
                          - jeśli istnieje to otwórz czat
        */
            return "redirect:/login";
        return "index";
    }
}

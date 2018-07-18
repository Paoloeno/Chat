package pl.nowosielski.chat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        if(LoginController.tmpUser == null)
            return "redirect:/login";
        return "index";
    }
}

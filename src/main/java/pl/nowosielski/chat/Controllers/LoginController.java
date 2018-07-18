package pl.nowosielski.chat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.nowosielski.chat.models.UserModel;

@Controller
public class LoginController {

    public static UserModel tmpUser;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("loginArea") String login, Model model){

        if(login.length() > 16 || login.length() < 3) {
            model.addAttribute("loginInfo", "Login musi mieć od 3 do 16 znaków! Spróbuj innego :)");
            return "login"; }

        tmpUser = new UserModel(login);
        return"redirect:/";
    }
}

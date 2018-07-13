package pl.nowosielski.chat.Controllers;

import org.springframework.stereotype.Controller;
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
    public String login(@RequestParam("loginArea") String login){
        tmpUser = new UserModel(login);
        return"redirect:/";
    }
}

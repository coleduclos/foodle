package user; 

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UserController {

    @RequestMapping("/user")
    public String index() {
        return "Inside UserController!!!";
    }

}
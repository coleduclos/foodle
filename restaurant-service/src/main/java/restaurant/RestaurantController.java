package restaurant;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RestaurantController {

    @RequestMapping("/restaurant")
    public String index() {
        return "Inside RestaurantController!!!";
    }

}
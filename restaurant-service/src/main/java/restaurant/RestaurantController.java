package restaurant;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String addRestaurant(@RequestBody Restaurant restaurant) {
        return "Adding restaurant: " + restaurant.getName() + ", " + restaurant.getUuid();
    }

}
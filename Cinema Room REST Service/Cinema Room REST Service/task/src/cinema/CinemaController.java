package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    @GetMapping("/seats")
    public Cinema getSeats() {
        Cinema cinema = new Cinema(9, 9);
        return cinema;
    }
}

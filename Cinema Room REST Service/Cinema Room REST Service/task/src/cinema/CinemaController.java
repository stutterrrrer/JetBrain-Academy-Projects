package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {
    private final Cinema cinema = new Cinema();

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody Seat requestedSeat) {
        int row = requestedSeat.getRow(), col = requestedSeat.getColumn();
        if (row <= 0 || row > cinema.getTotalRows() || col <= 0 || col > cinema.getTotalCols()) {
            return new ResponseEntity<>(
                    Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        Seat seat = cinema.getSeat(row, col);
        if (seat.isAvailable()) {
            String token = cinema.purchaseTicketReturnToken(row, col);
            return new ResponseEntity<>(
                    Map.of(
                            "token", token,
                            "ticket", seat
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> refundTicket(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        if (!cinema.isValidToken(token)) {
            return new ResponseEntity<>(Map.of(
                    "error", "Wrong token!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                Map.of("returned_ticket", cinema.refundTicketReturnSeat(token)),
                HttpStatus.OK
        );
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> getStats(
            @RequestParam(value = "password", required = false)
            String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(
                    Map.of("error", "The password is wrong!"),
                    HttpStatus.valueOf(401)
            );
        }
        return new ResponseEntity<>(cinema.getStatistics(), HttpStatus.OK);
    }
}

package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    private final Cinema cinema = new Cinema();
    private final Map<String, Seat> ticketTokens = new HashMap<>();

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Seat requestedSeat) {
        int row = requestedSeat.getRow(), col = requestedSeat.getColumn();
        if (row <= 0 || row > cinema.getTotalRows() || col <= 0 || col > cinema.getTotalCols()) {
            return new ResponseEntity<>(
                    Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        Seat seat = cinema.getSeat(row, col);
        if (seat.isAvailable()) {
            return bookTicket(seat);
        } else {
            return new ResponseEntity<>(
                    Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<Map<String, Object>> bookTicket(Seat seat) {
        seat.setAvailable(false);
        String token = UUID.randomUUID().toString();
        ticketTokens.put(token, seat);
        return new ResponseEntity<>(
                Map.of("token", token,
                        "ticket", seat),
                HttpStatus.OK
        );
    }

    @PostMapping("/return")
    public ResponseEntity refundTicket(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        if (!ticketTokens.containsKey(token)) {
            return new ResponseEntity<>(Map.of(
                    "error", "Wrong token!"),
                    HttpStatus.BAD_REQUEST);
        }
        Seat seat = ticketTokens.remove(token);
        seat.setAvailable(true);
        return new ResponseEntity<>(
                Map.of("returned_ticket", seat),
                HttpStatus.OK
        );
    }
}

package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class CinemaController {
    private Cinema cinema = new Cinema();

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Map<String, Integer> rowAndColumn) {
        int row = rowAndColumn.get("row"), col = rowAndColumn.get("column");
        if (row <= 0 || row > cinema.getTotalRows() || col <= 0 || col > cinema.getTotalCols()) {
            return new ResponseEntity<>(
                    Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (cinema.seatIsAvailable(row, col)) {
            cinema.bookSeat(row, col);
            return new ResponseEntity<>(cinema.getSeat(row, col), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}

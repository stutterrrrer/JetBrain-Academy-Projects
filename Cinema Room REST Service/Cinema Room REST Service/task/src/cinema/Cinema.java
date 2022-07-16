package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cinema {
    @JsonProperty("total_rows")
    private Integer totalRows = 9;
    @JsonProperty("total_columns")
    private Integer totalCols = 9;
    @JsonIgnore
    private final List<Seat> allSeats;
    @JsonProperty("available_seats")
    private List<Seat> availableSeats;

    public Cinema() {
        allSeats = new ArrayList<>();
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                allSeats.add(new Seat(row + 1, col + 1));
            }
        }
    }

    public Seat getSeat(int row, int col) {
        return allSeats.get(row * totalCols + col);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public List<Seat> getAvailableSeats() {
        return allSeats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }
}

package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    @JsonProperty("total_rows")
    private Integer totalRows = 9;
    @JsonProperty("total_columns")
    private Integer totalCols = 9;
    @JsonProperty("available_seats")
    private final List<Seat> allSeats;

    public Cinema(int rows, int cols) {
        allSeats = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                allSeats.add(new Seat(row + 1, col + 1));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public List<Seat> getAllSeats() {
        return allSeats;
    }
}

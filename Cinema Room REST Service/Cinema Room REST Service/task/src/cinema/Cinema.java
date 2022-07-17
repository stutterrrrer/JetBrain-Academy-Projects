package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class Cinema {
    @JsonProperty("total_rows")
    private Integer totalRows = 9;
    @JsonProperty("total_columns")
    private Integer totalCols = 9;
    private final List<Seat> allSeats; // no getter - won't be in JSON output
    @JsonProperty("available_seats")
    private List<Seat> availableSeats;
    private final TicketTokens ticketTokens; // no getter - won't be in JSON output
    private final Statistics statistics; // @JsonIgnore getter - won't be in JSON output

    public Cinema() {
        allSeats = new ArrayList<>();
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                allSeats.add(new Seat(row + 1, col + 1));
            }
        }
        ticketTokens = new TicketTokens();
        statistics = new Statistics(allSeats.size());
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public List<Seat> getAvailableSeats() {
        return allSeats.stream()
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
    }

    public Seat getSeat(int row, int col) {
        return allSeats.get((row - 1) * totalCols + (col - 1));
    }

    public String purchaseTicketReturnToken(int row, int col) { // expects valid seat only
        Seat seat = getSeat(row, col);
        seat.setAvailable(false);
        statistics.purchaseTicket(seat);
        return ticketTokens.generateAndReturnTokenForSeat(seat);
    }

    public boolean isValidToken(String token) {
        return ticketTokens.hasToken(token);
    }

    public Seat refundTicketReturnSeat(String token) { // expects valid token only
        Seat seat = ticketTokens.freeSeat(token);
        seat.setAvailable(true);
        statistics.refundTicket(seat);
        return seat;
    }

    @JsonIgnore
    public Statistics getStatistics() {
        return statistics;
    }
}

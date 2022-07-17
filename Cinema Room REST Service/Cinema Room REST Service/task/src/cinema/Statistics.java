package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {
    @JsonProperty("current_income")
    private int income;
    @JsonProperty("number_of_available_seats")
    private int availableSeatCount;
    @JsonProperty("number_of_purchased_tickets")
    private int purchasedSeatCount;

    public Statistics(int totalSeat) {
        income = 0;
        availableSeatCount = totalSeat;
        purchasedSeatCount = 0;
    }

    public void purchaseTicket(Seat seat) {
        income += seat.getPrice();
        availableSeatCount--;
        purchasedSeatCount++;
    }

    public void refundTicket(Seat seat) {
        income -= seat.getPrice();
        availableSeatCount++;
        purchasedSeatCount--;
    }

    public int getIncome() {
        return income;
    }

    public int getAvailableSeatCount() {
        return availableSeatCount;
    }

    public int getPurchasedSeatCount() {
        return purchasedSeatCount;
    }

}

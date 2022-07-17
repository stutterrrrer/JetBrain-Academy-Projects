package cinema;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TicketTokens {
    private final Map<String, Seat> tokensMap; // no getter - won't be in JSON output

    public TicketTokens() {
        tokensMap = new HashMap<>();
    }

    public String generateAndReturnTokenForSeat(Seat seat) {
        String token = UUID.randomUUID().toString();
        tokensMap.put(token, seat);
        return token;
    }

    public boolean hasToken(String token) {
        return tokensMap.containsKey(token);
    }

    public Seat freeSeat(String token) {
        return tokensMap.remove(token);
    }
}

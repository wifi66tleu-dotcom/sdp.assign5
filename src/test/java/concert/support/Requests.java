package concert.support;

import concert.model.CustomerType;
import concert.model.TicketRequest;
import java.time.LocalDate;

public final class Requests {

    public static final LocalDate TODAY = LocalDate.of(2026, 1, 1);

    private Requests() {
    }

    public static TicketRequest of(int quantity, double basePrice, int daysUntilEvent) {
        return new TicketRequest("rock-fest", quantity, basePrice,
                TODAY, TODAY.plusDays(daysUntilEvent), CustomerType.REGULAR);
    }
}

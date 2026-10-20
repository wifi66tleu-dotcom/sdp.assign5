package concert.model;

import java.time.LocalDate;
import java.util.Objects;

public record TicketRequest(
        String eventId,
        int quantity,
        double basePrice,
        LocalDate purchaseDate,
        LocalDate eventDate,
        CustomerType customerType) {

    public TicketRequest {
        Objects.requireNonNull(eventId, "eventId");
        Objects.requireNonNull(purchaseDate, "purchaseDate");
        Objects.requireNonNull(eventDate, "eventDate");
        Objects.requireNonNull(customerType, "customerType");
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("basePrice must not be negative");
        }
    }
}

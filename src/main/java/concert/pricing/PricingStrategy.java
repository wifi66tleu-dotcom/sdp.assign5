package concert.pricing;
import concert.model.TicketRequest;

public interface PricingStrategy {

    double calculateTotal(TicketRequest request);

    String name();
}

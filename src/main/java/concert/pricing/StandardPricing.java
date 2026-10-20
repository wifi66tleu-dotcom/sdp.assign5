package concert.pricing;
import concert.model.TicketRequest;
public class StandardPricing implements PricingStrategy {

    @Override
    public double calculateTotal(TicketRequest request) {

        return request.basePrice() * request.quantity();
    }

    @Override
    public String name() {
        return "Standard";
    }
}

package concert.pricing;
import concert.model.TicketRequest;

public class GroupBundlePricing implements PricingStrategy {

    static final int BUNDLE_SIZE = 4;

    @Override
    public double calculateTotal(TicketRequest request) {
        int freeTickets = request.quantity() / BUNDLE_SIZE;
        return request.basePrice() * (request.quantity() - freeTickets);
    }

    @Override
    public String name() {
        return "Group bundle";
    }
}

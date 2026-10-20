package concert.pricing;

import concert.data.SalesDataSource;
import concert.model.TicketRequest;

import java.util.Objects;

public class DemandBasedPricing implements PricingStrategy {

    private final SalesDataSource salesData;

    public DemandBasedPricing(SalesDataSource salesData) {
        this.salesData = Objects.requireNonNull(salesData, "salesData");
    }

    @Override
    public double calculateTotal(TicketRequest request) {
        double ratio = salesData.soldRatio(request.eventId());
        return request.basePrice() * request.quantity() * (1 + ratio * ratio);
    }

    @Override
    public String name() {
        return "Demand based";
    }
}

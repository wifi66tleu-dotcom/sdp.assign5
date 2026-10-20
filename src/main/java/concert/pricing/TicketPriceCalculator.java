package concert.pricing;

import concert.data.SalesDataSource;
import concert.model.TicketRequest;
import java.util.EnumMap;
import java.util.Map;

public class TicketPriceCalculator {

    private final Map<PricingType, PricingStrategy> catalog = new EnumMap<>(PricingType.class);

    public TicketPriceCalculator(SalesDataSource salesData) {
        catalog.put(PricingType.STANDARD, new StandardPricing());
        catalog.put(PricingType.EARLY_BIRD, new EarlyBirdPricing());
        catalog.put(PricingType.DEMAND_BASED, new DemandBasedPricing(salesData));
        catalog.put(PricingType.GROUP_BUNDLE, new GroupBundlePricing());
    }

    public double calculate(PricingType type, TicketRequest request) {
        return new PricingContext(catalog.get(type)).calculatePrice(request);
    }
}

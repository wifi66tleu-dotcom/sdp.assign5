package concert.pricing;
import concert.data.SalesDataSource;
import concert.model.TicketRequest;
import java.time.temporal.ChronoUnit;

public class PricingSelector {

    static final int GROUP_MIN_QUANTITY = 4;
    static final double HIGH_DEMAND_RATIO = 0.8;
    static final long EARLY_BIRD_MIN_DAYS = 30;

    private final SalesDataSource salesData;
    private final PricingStrategy standard = new StandardPricing();
    private final PricingStrategy earlyBird = new EarlyBirdPricing();
    private final PricingStrategy groupBundle = new GroupBundlePricing();
    private final PricingStrategy demandBased;

    public PricingSelector(SalesDataSource salesData) {
        this.salesData = salesData;
        this.demandBased = new DemandBasedPricing(salesData);
    }

    public PricingStrategy select(TicketRequest request) {
        if (request.quantity() >= GROUP_MIN_QUANTITY) {
            return groupBundle;
        }
        if (salesData.soldRatio(request.eventId()) >= HIGH_DEMAND_RATIO) {
            return demandBased;
        }
        long daysLeft = ChronoUnit.DAYS.between(request.purchaseDate(), request.eventDate());
        if (daysLeft >= EARLY_BIRD_MIN_DAYS) {
            return earlyBird;
        }
        return standard;
    }
}

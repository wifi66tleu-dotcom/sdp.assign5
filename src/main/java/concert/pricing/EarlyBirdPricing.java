package concert.pricing;
import concert.model.TicketRequest;
import java.time.temporal.ChronoUnit;

public class EarlyBirdPricing implements PricingStrategy {

    static final long FAR_TIER_DAYS = 60;
    static final long NEAR_TIER_DAYS = 30;
    static final double FAR_TIER_FACTOR = 0.75;
    static final double NEAR_TIER_FACTOR = 0.85;

    @Override
    public double calculateTotal(TicketRequest request) {
        long daysLeft = ChronoUnit.DAYS.between(request.purchaseDate(), request.eventDate());
        return request.basePrice() * request.quantity() * factorFor(daysLeft);
    }

    private double factorFor(long daysLeft) {
        if (daysLeft >= FAR_TIER_DAYS) {
            return FAR_TIER_FACTOR;
        }
        if (daysLeft >= NEAR_TIER_DAYS) {
            return NEAR_TIER_FACTOR;
        }
        return 1.0;
    }

    @Override
    public String name() {
        return "Early bird";
    }
}

package concert.pricing;
import concert.model.TicketRequest;
import java.util.Objects;

public class PricingContext {

    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {

        setStrategy(strategy);
    }

    public void setStrategy(PricingStrategy strategy) {

        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public double calculatePrice(TicketRequest request) {

        return strategy.calculateTotal(request);
    }

    public String strategyName() {

        return strategy.name();
    }
}

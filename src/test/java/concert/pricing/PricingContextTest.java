package concert.pricing;

import concert.model.TicketRequest;
import concert.support.Requests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PricingContextTest {
    private static PricingStrategy constant(double value, String name) {
        return new PricingStrategy() {
            @Override
            public double calculateTotal(TicketRequest request) {

                return value;
            }

            @Override
            public String name() {

                return name;
            }
        };
    }

    @Test
    void delegatesToTheStrategyFromTheConstructor() {
        PricingContext context = new PricingContext(constant(42.0, "stub"));
        assertEquals(42.0, context.calculatePrice(Requests.of(1, 100, 10)), 1e-6);
    }

    @Test
    void setStrategy_replacesTheAlgorithmAtRuntime() {
        PricingContext context = new PricingContext(constant(1.0, "first"));
        context.setStrategy(constant(2.0, "second"));
        assertEquals(2.0, context.calculatePrice(Requests.of(1, 100, 10)), 1e-6);
        assertEquals("second", context.strategyName());
    }

    @Test
    void rejectsNullStrategy() {
        assertThrows(NullPointerException.class, () -> new PricingContext(null));
        PricingContext context = new PricingContext(constant(1.0, "x"));
        assertThrows(NullPointerException.class, () -> context.setStrategy(null));
    }
}

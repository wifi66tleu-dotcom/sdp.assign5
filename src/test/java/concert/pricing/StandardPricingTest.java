package concert.pricing;

import concert.support.Requests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardPricingTest {

    private final StandardPricing pricing = new StandardPricing();

    @Test
    void total_isBasePriceTimesQuantity() {

        assertEquals(500.0, pricing.calculateTotal(Requests.of(5, 100, 3)), 1e-6);
    }

    @Test
    void total_ignoresDatesAndDemand() {
        assertEquals(pricing.calculateTotal(Requests.of(2, 80, 1)),
                pricing.calculateTotal(Requests.of(2, 80, 200)), 1e-6);
    }
}

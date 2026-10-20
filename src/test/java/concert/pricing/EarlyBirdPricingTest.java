package concert.pricing;

import concert.support.Requests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EarlyBirdPricingTest {

    private final EarlyBirdPricing pricing = new EarlyBirdPricing();

    @ParameterizedTest(name = "{0} days before the concert -> total {1}")
    @CsvSource({
            "100, 150.0",
            "60,  150.0",
            "59,  170.0",
            "30,  170.0",
            "29,  200.0",
            "0,   200.0"
    })
    void total_dependsOnDaysLeft(int daysLeft, double expectedTotal) {
        assertEquals(expectedTotal, pricing.calculateTotal(Requests.of(2, 100, daysLeft)), 1e-6);
    }
}

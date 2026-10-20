package concert.pricing;

import concert.support.Requests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupBundlePricingTest {

    private final GroupBundlePricing pricing = new GroupBundlePricing();

    @ParameterizedTest(name = "{0} tickets -> {1} paid")
    @CsvSource({
            "1, 1",
            "3, 3",
            "4, 3",
            "7, 6",
            "8, 6",
            "12, 9"
    })
    void everyFourthTicketIsFree(int quantity, int paidTickets) {
        assertEquals(paidTickets * 100.0, pricing.calculateTotal(Requests.of(quantity, 100, 10)), 1e-6);
    }
}

package concert.data;
import java.util.HashMap;
import java.util.Map;

public class SimulatedSalesDataSource implements SalesDataSource {

    private final Map<String, Double> soldRatios = new HashMap<>();
    private final double defaultRatio;

    public SimulatedSalesDataSource(double defaultRatio) {

        this.defaultRatio = defaultRatio;
    }

    public SimulatedSalesDataSource withEvent(String eventId, double ratio) {
        soldRatios.put(eventId, ratio);
        return this;
    }

    @Override
    public double soldRatio(String eventId) {

        return soldRatios.getOrDefault(eventId, defaultRatio);
    }
}

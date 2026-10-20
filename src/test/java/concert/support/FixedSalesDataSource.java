package concert.support;
import concert.data.SalesDataSource;

public class FixedSalesDataSource implements SalesDataSource {

    private final double ratio;
    private String lastEventId;

    public FixedSalesDataSource(double ratio) {

        this.ratio = ratio;
    }

    @Override
    public double soldRatio(String eventId) {
        this.lastEventId = eventId;
        return ratio;
    }

    public String lastEventId() {

        return lastEventId;
    }
}

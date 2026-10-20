package concert.model;

public record Seat(int row, int number, double price) {

    public Seat {
        if (row < 1 || number < 1) {
            throw new IllegalArgumentException("row and number start at 1");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
    }
}

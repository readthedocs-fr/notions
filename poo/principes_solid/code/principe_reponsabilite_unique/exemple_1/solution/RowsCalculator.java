public class RowsCalculator {

    private static final int SEAT_PER_ROW = 10;

    public int calculRows(ConcertHall concertHall) {
        return (concertHall.getClassicSeats() + concertHall.getHandicapSeats() + concertHall.getClassicSeats()) / SEAT_PER_ROW;
    }

}
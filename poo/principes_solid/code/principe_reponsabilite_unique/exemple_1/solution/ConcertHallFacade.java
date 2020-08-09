public class ConcertHallFacade {

    private final ConcertHall concertHall;

    public ConcertHallFacade(ConcertHall concertHall) {
        this.concertHall = concertHall;
    }

    public int getTotalPrice() {
        return new PriceCalculator().calculTotalPrice(concertHall);
    }

    public int getHalRows() {
        return new RowsCalculator().calculRows(concertHall);
    }

    public void saveConcert(String day) {
        new ConcertSaver().saveConcert(concertHall, day);
    }

}

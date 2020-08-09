public class PriceCalculator {

    private static final int CLASSIC_SEAT_PRICE = 15;
    private static final int VIP_SEAT_PRICE = 30;

    public int calculTotalPrice(ConcertHall concertHall) {
        return (concertHall.getClassicSeats() + concertHall.getHandicapSeats()) * CLASSIC_SEAT_PRICE + concertHall.getVipSeats() * VIP_SEAT_PRICE;
    }

}
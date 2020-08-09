public class ConcertHall {

    private static final int SEAT_PRICE = 15;
    private static final int SEAT_PER_ROW = 10;
    private static final int VIP_PRICE_FACTOR = 2;

    private final int hallNumber;
    private final int classicSeats;
    private final int handicapSeats;
    private final int vipSeats;

    public ConcertHall(int hallNumber, int classicSeats, int handicapSeats, int vipSeats) {
        this.hallNumber = hallNumber;
        this.classicSeats = classicSeats;
        this.handicapSeats = handicapSeats;
        this.vipSeats = vipSeats;
    }

    public int getTotalPrice() {
        return getTotalSeats() * SEAT_PRICE;
    }

    public int getRowCount() {
        return getTotalSeats() / SEAT_PER_ROW;
    }

    private int getTotalSeats() {
        return classicSeats + handicapSeats + (vipSeats * VIP_PRICE_FACTOR);
    }

    public void registerShow(String day) {
        // Sauvegarde dans un fichier des données du spectacle en rapport avec la salle
    }

}
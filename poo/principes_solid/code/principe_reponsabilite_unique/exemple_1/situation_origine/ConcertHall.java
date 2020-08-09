public class ConcertHall {

    private static final int SEAT_PRICE = 15;
    private static final int SEAT_PER_ROW = 10;

    private final int hallNumber;
    private final int classicSeats;
    private final int handicapSeats;

    public ConcertHall(int hallNumber, int classicSeats, int handicapSeats) {
        this.hallNumber = hallNumber;
        this.classicSeats = classicSeats;
        this.handicapSeats = handicapSeats;
    }

    public int getTotalPrice() {
        return getTotalSeats() * SEAT_PRICE;
    }

    public int getRowCount() {
        return getTotalSeats() / SEAT_PER_ROW;
    }

    private int getTotalSeats() {
        return classicSeats + handicapSeats;
    }

    public void registerShow(String day) {
        // Sauvegarde dans un fichier des données du spectacle en rapport avec la salle
    }

}
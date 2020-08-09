public class ConcertHall {

    private final int classicSeats;
    private final int vipSeats;
    private final int handicapSeats;

    public ConcertHall(int classicSeats, int vipSeats, int handicapSeats) {
        this.classicSeats = classicSeats;
        this.vipSeats = vipSeats;
        this.handicapSeats = handicapSeats;
    }

    public int getClassicSeats() {
        return classicSeats;
    }

    public int getVipSeats() {
        return vipSeats;
    }

    public int getHandicapSeats() {
        return handicapSeats;
    }

}

public class AveragePresenter {

    private final AverageCalculator averageCalculator;

    public AveragePresenter(AverageCalculator averageCalculator) {
        this.averageCalculator = averageCalculator;
    }

    public String toString() {
        return "Student average : " + averageCalculator.calculAverage();
    }

    public String toJSON() {
        return "{ \"average\":" + averageCalculator.calculAverage() + " }";
    }

}
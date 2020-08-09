public class AverageCalculator {

    private final Student student;

    public AverageCalculator(Student student) {
        this.student = student;
    }

    public float calculAverage() {

        List<Integer> results = student.getResults();

        if (results.isEmpty()) {
            return 0;
        }

        return results.stream()
                .reduce((sum, result) -> sum += result)
                .orElse(0) / (float) results.size();
    }
    
}

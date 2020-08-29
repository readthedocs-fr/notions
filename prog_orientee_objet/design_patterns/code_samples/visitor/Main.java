import java.util.*;

class Main {
    
    public static void main(String... args) {
        AnimalFeedingVisitor feeder = getAnyFeeder();
        List<Animal> animals = Arrays.asList(
            new Lion(),
            new Whale(),
            new Duck()
        );
        
        for(Animal animal : animals) {
            animal.accept(feeder);
        }
    }
    
    private static AnimalFeedingVisitor getAnyFeeder() {
        // ici vous pouvez renvoyer l'implémentation que vous souhaitez
        return new ZooClientVisitor();
        // return new ZooEmployeeVisitor();
    }
}
# Abstraction

The **abstraction** idea, is divide a class in parts, to be able to share (via a parameter passed in method for example) **only one** of this part rather the object “in full”. To understand the utility of this principle and how apply, take for example a class Human, since this is a thing that we know better.


```java

class Human {

    int air;
    int satiety;
    float energy;


    void breathe() {
        air += 10;
    }

    void eat() {
        satiety += 5;
    }

    void move() {
        System.out.println("Un humain souhaite se déplacer !");
    }

    void consumeEnergy(float value) {
        this.energie -= value;
    }
}
```

Now, imagine that we have a class Hiking which allows organise a walk enter Humans. A thing of style:

```java

class Hiking {

    List<Human> participants;

    public Hiking() { this.participants = new ArrayList<>(); }

    void addParticipant(Human participant) { this.participants.add(participant); }

    void moveAll() {
        for(Human human : participants) {
            human.move();
            human.consumeEnergy(3);
        }
    }
}
```

So far so good, we have that we want. This code works perfectly, except, in reality, he contain two major problems.

1. **We restraint the hiking of the objects of type Human**, whereas anything can walk and exhausted could take a hiking. It’s not really the kind to forbid to elephants and to tractors to participate :(
2. We ask a human in full but us all we need, this is his legs (hum). This class hiking, however currently access to the human respiratory system (via `breathe()` ) and to this stomach (via `eat()` ) ! **So, we give too much power to this class Hiking, since she is able to do things who shouldn’t be her accessible**. <br>

So, how to contour this two problems? 

The ** interfaces ** come to our rescue (interfaces are great). Let's make a `Walking` interface, which allows to describe something that moves and gets tired:

```java
interface Walking {
    void move();
    void consumeEnergy(float value);
}
```

So, now, our class Human will be able to implement this super interface, and in reality we add `implements Walking` and put a little `@Override` on their methods `move` and `consumeEnergy` and the turn is played, since this is a behaviour that we had already implemented beforehand, just without the interface.

```java
class Human implements Walking {

    int air;
    int satiety;
    float energy;


    void breathe() {
        air += 10;
    }

    void eat() {
        satiety += 5;
    }

    @Override
    void move() {
        System.out.println("Un humain souhaite se déplacer !");
    }

    @Override
    void consumeEnergy(float value) {
        this.energy -= value;
    }
}
```

But, what to change? Now, instead of making a human list in our Hiking, we will do a *Walking list* (it’s more politically correct). And, there is almost nothing to change, it’s a good sign:

```java

class Hiking {

    List<Walking> participants;

    public Hiking() { this.participants = new ArrayList<>(); }

    void addParticipant(Walking participant) { this.participants.add(participant); }

    void moveAll() {
        for(Walking walking: participants) {
            walking.move();
            walking.consumeEnergy(3);
        }
    }
}
```

And, henceforth, not only our Hiking class hasn’t access to the Human methods `breathe` and `eat`, since, nowhere, we said to our work with humans (no, but think, if a hiking can make you swallow peas, it would be creepy, still), but more, she accepts the elephants and the tractors !
Provided that implements `Walking` them too… and, who know how to out of print a tractor.

## Conclusion

Make an abstraction to our **Human** class we allowed to do two very important opposite things:

- **An access restriction**, since `Hiking` hasn’t access to some functions to the `Human` class
- **An ease of access**, since `Hiking` accepts henceforth, every object type implementing `Walking`, instead of being limited to humans.

For more information on this principle [SOLID] (<https://fr.wikipedia.org/wiki/SOLID_(informatique)>) named **Dependency Inversion** (who is mainly based on abstraction), I invite you to read [Wikipedia](https://en.wikipedia.org/wiki/Dependency_inversion_principle) on this subject.

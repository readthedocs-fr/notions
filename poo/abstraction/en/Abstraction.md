# Abstraction

**Abstraction** is the idea of dividing a class into several parts, to be able to share (via a parameter passed in a method for example) **only one** of these parts, rather than the object entirely. To understand the utility of this principle and how to apply it, take for example a class `Human` since this is something that we know well.


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
        System.out.println("A human wants to move!");
    }

    void consumeEnergy(float value) {
        this.energy -= value;
    }
}
```

Now, imagine that we have a class `Hiking` that allows us to organise a walk. Something like:

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

So far so good, we have what we want. This code works perfectly well, except that, in reality, it contains two major problems.

1. **We restraint the hiking of the objects of type Human**, whereas anything that can **walk** and **be exhausted** could take a hike. It’s not really the kind to forbid to elephants and to tractors to participate :(
2. We ask for a whole human but all we need is their legs (hum). This hiking class however currently has access to the human breathing system (via `breathe()` ) and to his stomach (via `eat()` )! **So, we give too much power to this class, since it is able to do things that it shouldn’t be able to do**. <br>

So, how do we get around these two problems? 

**Interfaces** come to our rescue (interfaces are great). Let's make a `Walking` interface, which allows describing something that moves and gets tired:

```java
interface Walking {
    void move();
    void consumeEnergy(float value);
}
```

So now, our class `Human` will be able to **implement** this awesome interface, and all that's to di is to add `implements Walking` and put a little `@Override` on our `move` and `consumeEnergy` methods, and that's it, since this is a behavior that we had already implemented beforehand, only without the interface.

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
        System.out.println("A human wants to move!");
    }

    @Override
    void consumeEnergy(float value) {
        this.energy -= value;
    }
}
```

But, what has changed? Well now, instead of making a Human list in our Hiking class, we will do a **Walking list** (it’s more politically correct). And, there is almost nothing to change, which is a good sign:

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

And, henceforth, not only doesn't our Hiking class have access to the Human methods `breathe` and `eat`, since, we never specified that it's humans we're working with (think about it, if hiking could make you swallow peas, it would be creepy, right?), but also, it accepts the elephants and the tractors!
But that would assuming that they implement `Walking` too… and, who knows how to make a tractor tired.

## Conclusion

By making an abstraction to our **Human** class, we allowed two very important yet opposite things:

- **An access restriction**, since `Hiking` doesn’t have access to the functions that are specific to the `Human` class
- **An ease of access**, since `Hiking` accepts henceforth every object whose type implements `Walking`, instead of being limited to humans.

For more information on the [SOLID] (<https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)>) principle called **Dependency Inversion** (which is mainly based on abstraction), I invite you to read the [Wikipedia](https://en.wikipedia.org/wiki/Dependency_inversion_principle) article related to this topic.

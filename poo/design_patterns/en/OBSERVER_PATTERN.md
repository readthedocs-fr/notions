# Observer Pattern

> :information_source: Examples written in Java

### Prerequisite

- Be comfortable with the notion of inheritance 

---

The **Observer Pattern** is a design pattern which makes use of 2 subjects : an **observable** subject and one or more **observer** subjects.

When the observable's state changes, it will notify each one of its observers which will react accordingly.

You have surely already used this principle without realizing it.

## Let's go

Let's take a look over the heart of the pattern: **its conception** !

---


*As you will notice, I'll sometimes use what I call "easy code". Easy code is, for me, some piece of code which is explicit enough to be easily understood without having knowledge of the internal implementation*

For example :

```java
System.out.println(new Person("John", "Doe", 42).getJob().getName());
```

We don't know the code of the class `Person`. We don't exactly know what `getJob()` or `getName()` returns but we can assume this line will display the job's name of John Doe.

---

Let's set up the situation.

You work for a home automation company.

Your company decided to develop a way to trigger some systems when any alarm occurs within a certain radius.

Your line manager asked you to design a prototype.

Without using the **Observer Pattern**, the code could look like this :

```java
class Alarm {
    
    /*
      Here the way alert() 
      gets called don't matter. 
      It's an example of "easy code"
    */
    public void alert(){
        /*
          And again, some easy code.
          I didn't define the house variable 
          but we can assume what it contains 
        */
        Police.sendTheftAlert(this.house.getAddress());
        this.house.lockAllDoors();
        this.house.getOwner().sendMessage("Someone entered your house !");
        //...
    }
}
```

However, we have 2 issues:

1. The alarm isn't supposed to know the systems that react to it.
2. If we want to add a new auto-activable system, we would have to create it and next add it in the `alert()` method. By doing this, the method could have lines and lines in the long run.

## So, what do we do Einstein ?

The **Observer Pattern** was born for this kind of situation.

This pattern is made of 2 subjects, as said earlier: an **observable** subject and one or more **observer** subjects.

We'll use the abstraction principle to write a reusable code in most or all cases.

```java
interface Observer {

}

interface Observable {

}
```

These interfaces seem very empty... for now.

With this pattern, a so-called **Observer** class must be notifiable at any time of the day and night and an **observable** class must notify each one of the elements which are observing it.

Let's add these 2 methods :

```java
interface Observer {
    void notify();
}

interface Observable {
    void notifyObservers();
}
```

Okay.

Of course we must be able to link an **Observer** to an **Observable** element :

```java
interface Observer {
    void notify();
}

interface Observable {
    void notifyObservers();
    void addObserver(Observer observer);
}
```

Let's try to rewrite our alarm system making use of these 2 interfaces :

```java
class Alarm implements Observable {
    
    private final List<Observer> observers = new ArrayList<>();
    
    public void alert() {
        notifyObservers();
        this.speaker.startAlarm();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void notifyObservers() {
        observers.foreach(Observer::notify);
    }
}

class PoliceCallerComponent implements Observer {
    
    @Override 
    public void notify() {
        System.out.println("Police successfully called");
    }
}

class DoorLockerComponent implements Observer {

    @Override
    public void notify() {
        lockAllDoors();
        System.out.print("The thief is blocked inside");
    }

    private void lockAllDoors() {
        //...
    }

}

//...
```

*I'm not going to write all the systems, you understood the main idea.*

Can't figure out how the `notifyObservers()` method works?
Go read the course about the [functional interfaces](https://github.com/readthedocs-fr/notions/blob/master/java/interfaces_fonctionnelles/en/FUNCTIONAL_INTERFACES.md) as well as the note about the [Method Referencing](https://github.com/readthedocs-fr/notions/blob/master/java/interfaces_fonctionnelles/en/FUNCTIONAL_INTERFACES.md#always-shorter-always-further).

This line will produce the same effect as the following for loop :
       
```java
    for(Observer observer : observers) {
        observer.notify();
    }
```

We now have a code that perfectly suits the **Observer Pattern**. Let's add a control center for the realistic touch and it will surely please our boss.

```java
class ControlCenter {

    private static Alarm alarm;

    public static void main(String[] args) {

        alarm = new Alarm();

        alarm.addObserver(new PoliceCallerComponent());
        alarm.addObserver(new DoorLockerComponent());
        //...

       System.out.println("Alarm successfully initialized");
    }

}
```

Perfect !

Now, as soon as the `alert()` method from the `Alarm` class gets called, each one of its **Observers** will get notified and will react accordingly.

And as a bonus, our code follows a realistic logic, since it's not the alarm that knows its **Observers** but rather our **control center**, which defines what will happen when our alarm occurs.

Moreover, we can see in this code a nice example of the `Open/Closed` principle symbolized with the `O` letter in the [SOLID](https://github.com/readthedocs-fr/notions/tree/master/poo/principes_solid/) principles list. Indeed, our `Alarm` class is open for extension but closed to any modifcation. We can extend it without any limit by adding more and more **Observer** composants without having to modify it. The alarm system is ended, we don't need to change it. So we are sure we would never cause some trouble in this class when adding a new auto-activable component.

While speaking about the [SOLID](https://github.com/readthedocs-fr/notions/tree/master/poo/principes_solid/) principles, we can also notice the `D` letter principle named the `Dependency Inversion principle`. Indeed, by making use of `Observer` and `Observable` interfaces, we have build a code that directly refers to abstractions and not to implementations. Our `addObserver(Observer observer)` method takes an object of type `Observer` as parameter. We can pass it any **Observer** implementation, the class implementing the `Observable` interface will never have to deal with the implementation. It's a significant point in maintenability and readibility of our code because we will never have to care of consequences a new implementation will have on the global code, at least not of the POV of our **Observer Pattern**.

## Conclusion

We have seen the kind of problem that an **Observer Pattern** can fix and its conception step by step.

Overall, we must remember that :

- This pattern allows us to split our code into many parts.
- It helps us store our code with better logic.
- It's based on 2 important interfaces : **Observer** and **Observable**.
- The **Observable** class notifies each one of its **Observers** when its state changes
- It helps us easily enforce 2 of the [**SOLID**](https://github.com/readthedocs-fr/notions/tree/master/poo/principes_solid/) principles.

---

<ins>Notes :</ins>

I presented here the **Observer Pattern** in its purest style. Of course, you can adjust it according to your desires. For example, the **Observer** `notify()` method can take a parameter to pass it to each **Observer**. In my case, I could have passed the house address if I had had this need. The method's signature would have looked like this: `void notify(Address address);`. The `notifyObservers()` method can also get fitted out according to your needs and you can also include some other methods that look useful for you in your case. The idea stays the same. 

The reactive paradigm uses and greatly amplifies this principle, I let you inquire on the official website of the [Reactor.io](https://projectreactor.io) project which is one of the best, if not the best known amongst the Java libraries for reactive programming.

The **Observer Pattern** is very suitable with an Entity Component System (abbreviated ECS) for those who have already heard of it. An article will maybe someday be written about it.

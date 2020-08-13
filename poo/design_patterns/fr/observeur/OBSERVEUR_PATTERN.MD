# Observer Pattern

> :information_source: Exemples écrits en Java

### Prérequis

- Comprendre la notion d'héritage 

---

L'**Observer Pattern** *(ou observateur en français)* est un patron de conception prenant en compte au moins 2 sujets : un sujet **observable** et un ou plusieurs sujets **observateurs**.

Lorsque le sujet observable change d'état, il va notifier chacun de ses observateurs qui réagiront en conséquence.

Vous avez sûrement déjà utilisé ce principe sans même vous en rendre compte


## Let's go

Rentrons dans le cœur du patron : **sa conception** !

---


*Comme vous aller le constater, j'utilise parfois du pseudo-code, vous en avez peut-être déjà entendu parler et vous en entendrez encore sûrement parler. Le pseudo-code est du code suffisamment explicite pour être compréhensible sans devoir connaître l'exacte nature du code derrière*

Par exemple :

```java
System.out.println(new Person("Jean", "Dupont", 42).getJob().getName());
```

On ne connaît pas le code de la classe `Person`, on ne sait pas exactement ce que renvoie `getJob()` ni `getName()` mais on se doute que cette ligne affichera le nom du métier de Jean Dupont.


---

Posons la situation.

Vous travaillez dans une entreprise de domotique. 

Votre société décide de développer une manière de déclencher différents dispositifs lorsqu'une alarme quelconque s'active.

Votre supérieur vous demande donc de concevoir un prototype.

Sans utiliser l'Observer Pattern, le code pourrait ressembler à ça :

```java
class Alarm {
    
    /*
      Ici la manière dont alert() 
      est appelée ne nous intéresse pas. 
      Il s'agit de pseudo-code
    */
    public alert(){
        /*
          Encore une fois du pseudo-code, 
          je n'ai pas défini la variable house
          mais on comprend son principe 
        */
        Police.sendTheftAlert(this.house.getAddress())
        this.house.lockAllDoors();
        this.house.getOwner().sendMessage("Someone entered in your house !");
        //...
    }
}
```

Ici, nous avons 2 problèmes :

1. L'alarme n'est pas censé connaître les composants qui lui réagissent 
2. Si nous voulons ajouter un nouveau dispositif auto-déclenchant, il nous faudrait d'abord le créer puis le rajouter dans la méthode `alert()`. À force cette méthode pourrait faire des dizaines et des dizaines de lignes.

## Qu'est ce qu'on fait alors Einstein ?

C'est pour ce genre de situation que l'Observer Pattern est né.

Ce pattern se compose de 2 sujets comme dit plus haut : un sujet **observable** et un ou des sujets **observateurs**.

Nous allons nous aider du principe d'abstraction afin d'écrire un code réutilisable dans la grande majorité voire l'entièreté des cas :

```java
interface Observer {

}

interface Observable {

}
```

Ces interfaces semblent bien vides.

Avec ce pattern, une classe dite **Observer** se doit d'être notifiable à toute heure du jour et de la nuit et une classe **observable** doit notifier les éléments qui l'observent.

Ajoutons donc ces 2 méthodes :

```java
interface Observer {
    void notify();
}

interface Observable {
    void notifyObservers();
}
```

Bon il faut évidemment pouvoir lier un **Observer** à un élément observable :

```java
interface Observer {
    void notify();
}

interface Observable {
    void notifyObservers();
    void addObserver(Observer observer);
}
```

Essayons de récrire notre système d'alarme en utilisant ces 2 interfaces :

```java
class Alarm implements Observable {
    
    private final List<Observer> observers = new ArrayList<>();
    
    public void alert() {
        notifyObservers();
        this.speaker.startAlarm();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        observers.foreach(Observer::notify);

       /*
        Cette méthode ne vous dis rien ?
        Allez lire le cours sur les interfaces fonctionnelles 
        ainsi que celui sur le Method Referencing ou référencement de méthode.
        
        Cette ligne fait le même travail que la boucle ci dessous :
       
        for(Observer observer : observers) {
            observer.notify();
        }

        */
    }
}

class PoliceCallerComponent implements Observer {
    
    public void notify() {
        System.out.println("Police successfully called");
    }
}

class DoorLockerComponent implements Observer {

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

*Je ne vais pas écrire tous les composants, vous avez compris l'idée.*

- [Interfaces Fonctionnelles](https://github.com/readthedocs-fr/notions/blob/master/java/interfaces_fonctionnelles/fr/INTERFACES_FONCTIONNELLES.md)
- [Method Referencing](https://github.com/readthedocs-fr/notions/blob/master/java/referencement_methode/fr/REFERENCEMENT_METHODE.md)

Nous avons à présent un code qui suit l'**Observer Pattern.** Ajoutons un centre de contrôle pour la touche de réalisme et ça plaira sûrement au patron.

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

Impeccable !

À présent, dès que la méthode `alert()` de la classe `Alarm` est appelée, chacun de ses **Observers** se verra notifié et agira en conséquence.

Et en prime, notre code suit une logique réaliste, ce n'est pas l'alarme qui connaît ses **Observers** mais bien le **Centre de contrôle** qui définit ce qui va se produire lors de l'alerte.

## Conclusion

On a vu le type de problème que l'**Observer Pattern** peut résoudre ainsi que sa construction pas à pas.

Globalement il faut retenir que 

- Ce pattern permet de fractionner le code.
- Il permet de le ranger de manière plus logique.
- Il se fonde sur 2 interfaces importante : **Observer** et **Observable**.
- La classe **Observable** notifie chacun de ses **Observers** lors d'un changement d'état.

---

<ins>Notes :</ins>

J'ai présenté ici l'**Observer Pattern** dans son style le plus épuré. Bien évidemment, vous pouvez l'adapter au gré de vos envies. Par exemple, la méthode `notify()` de l'**Observer** peut prendre un paramètre afin de le passer à chaque **Observer**. Dans mon cas, j'aurais pu passer l'adresse de la maison si j'avais eu besoin. La signature de la méthode aurait ressemblé à `void notify(Address address);`. La méthode `notifyObservers()` peut également être aménagée en fonction des besoins et vous pouvez inclure d'autres méthodes qui peuvent vous sembler utiles dans votre cas. Le principe reste le même 

La programmation réactive utilise et amplifie grandement ce principe, je vous laisse vous renseigner sur le site officiel du projet [Reactor.io](https://projectreactor.io) qui est une, si pas la plus connue, des librairies Java pour la programmation réactive.

L'**Observer Pattern** se lie très bien avec un Entity Component System (abrégé ECS) pour ceux qui en ont déjà entendu parler. Un billet sera peut-être écrit dessus un jour.

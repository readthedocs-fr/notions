L'idée de l'**abstraction**, c'est de découper une classe en morceaux, pour pouvoir partager (via un passage de paramètre dans une méthode par exemple) **un seul** de ces morceaux plutôt que l'objet "en entier". Pour comprendre l'utilité de ce principe et comment l'appliquer, prenons comme exemple une classe Human, puisque c'est un truc qu'on connaît plutôt pas trop mal.

```java

class Human {
  
    int air;
    int satiete;
    float energie;


    void respirer() {
        air += 10;
    }

    void manger() {
        satieter += 5;
    }

    void deplacer() {
        System.out.println("Un humain souhaite se déplacer !");
    }

    void epuiser(float value) {
        this.energie -= value;
    }
}
```

Imaginons maintenant que nous avons une classe `Randonnee` qui nous permet d'organiser une marche entre humains. Un truc du style:

```java

class Randonnee {
  
    List<Human> participants;

    public Randonnee() { this.participants = new ArrayList<>(); }

    void addParticipant(Human participant) { this.participants.add(participant); }

    void faireAvancer() {
        for(Human human : participants) {
            human.deplacer();
            human.epuiser(3);
        }
    }   
}
```

Jusque là tout va bien, on a ce qu'on veut. Ce code fonctionne parfaitement, sauf qu'en réalité, il contient deux problèmes majeurs.

1) **On restreint la randonnée aux objets de type Human**, alors que n'importe quoi qui peut **marcher** et **s'épuiser** pourrait faire une randonnée. C'est vraiment pas gentil d'interdire aux éléphants et aux tracteurs de participer :(
2) On demande un humain ENTIER alors que tout ce dont on a besoin, c'est ses jambes (hum). Cette classe randonnée a pourtant actuellement accès au système respiratoire de l'humain (via `respirer()`) et à son estomac (via `manger()`) ! **On donne donc bien trop de pouvoir à cette classe Randonnée, puisqu'elle est capable de faire des choses qui ne devraient pas lui être accessibles**. <br>

Comment fait-on alors pour contourner ces deux problèmes ?

Les **interfaces** viennent à notre secours (les interfaces, c'est chouette). Faisons une interface `Walking`, qui permet de décrire quelque chose qui se déplace et se fatigue:

```java
interface Walking {
    void deplacer();
    void epuiser(float value);
}
```

Donc maintenant, notre classe Human va pouvoir **implémenter** cette super interface, et en fait ben y a qu'à rajouter `implements Walking` et mettre des petits `@Override` sur les méthodes `deplacer` et `epuiser` et le tour est joué, vu que c'est un comportement qu'on avait déjà implémenté au préalable, juste sans l'interface.

```java
class Human implements Walking {
  
    int air;
    int satiete;
    float energie;


    void respirer() {
        air += 10;
    }

    void manger() {
        satieter += 5;
    }

    @Override
    void deplacer() {
        System.out.println("Un humain souhaite se déplacer !");
    }
  
    @Override
    void epuiser(float value) {
        this.energie -= value;
    }
}
``` 
***

Mais du coup, qu'est ce qui a changé ? Ben maintenant au lieu de faire une liste d'humains dans notre Randonnée, on va faire **une liste de Walking** (c'est quand même plus politiquement correct). Et y a presque rien à changer, c'est plutôt bon signe:

```java

class Randonnee {
  
    List<Walking> participants;

    public Randonnee() { this.participants = new ArrayList<>(); }

    void addParticipant(Walking participant) { this.participants.add(participant); }

    void faireAvancer() {
        for(Walking walking: participants) {
            walking.deplacer();
            walking.epuiser(3);
        }
    }
}
```

Et désormais, non seulement notre classe Randonnee n'a plus accès aux méthodes `respirer` et `manger` de Human, puisque l'on dit nulle part que l'on travaille avec des humains (non mais imaginez si une randonnée pouvait vous faire avaler des petit pois, ça serait terrifiant quand même), mais en plus elle accepte les éléphants et les tracteurs ! 
Enfin à condition qu'ils implémentent `Walking` eux aussi ... et qui sait comment on épuise un tracteur.

## Conclusion

Faire une abstraction de notre classe **Human** nous a en fait permis de faire deux choses opposées très importantes:

- **Une restriction d'accès**, puisque `Randonnee` n'a plus accès à certaines fonctions de la classe `Human`
- **Une facilisation d'accès**, puisque `Randonnee` accepte désormais tous les types d'objet implémentant **Walking**, au lieu de se restreindre aux humains.

Pour plus d'information sur le principe [SOLID](https://fr.wikipedia.org/wiki/SOLID_(informatique)) nommé **Dependency Inversion** (qui se base principalement sur l'abstraction), je vous invite à lire l'article [Wikipedia](https://en.wikipedia.org/wiki/Dependency_inversion_principle) anglais sur le sujet. 

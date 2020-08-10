# Le pattern Visiteur

> :information_source: Exemples écrits en Java

### Prérequis

- Une bonne connaissance en programmation orientée objet, en particulier sur les interfaces et l'héritage en général
- Savoir ce qu'est la surcharge de méthode
- Etre à l'aise avec la lecture de `pseudo code`


## Une mise en situation pour mieux comprendre

Imaginons la situation suivante. Vous devez coder une simulation d'un Zoo qui contient **trois** animaux différents: des **lions**, une **baleine** et des **canards** (c'est pauvre, mais restriction de budget oblige). Etant donné que l'on ne souhaite pas spécialement déterminer de particularités pour les animaux (pour l'instant), on va se contenter de créer nos classes vides.

```java
interface Animal {}

class Lion implements Animal {}
class Whale implements Animal {}
class Duck implements Animal {}
```

Le tout avec une classe principale:

```java
import java.util.*;

class Main {

    public static void main(String... args) {
    
        List<Animal> animals = Arrays.asList(
            new Lion(), new Lion()
            new Whale(),
            new Duck(), new Duck(), new Duck()
        );
    }
}
```

So far so good. Mais une fois ceci créé, on apprend qu'il faut mettre en place **un système pour nourrir les animaux**. On aura besoin **d'employés**, qui s'occuperont de **fournir la nourriture nécessaire aux bêtes**. Pour l'instant, ne nous prenons pas la tête. Implémentons simplement une méthode `feed()` dans l'interface Animal qui déterminera comment l'animal doit être nourri.

Avant de montrer le code, il est important de préciser que tous les morceaux de code jusqu'à la fin de cet article contiendront une bonne dose de `pseudo code`, du code **dont
l'implémentation n'est volontairement pas montrée** pour éviter de compliquer les choses. Il faudra s'y habituer, en lisant simplement le code sans chercher à comprendre ce qu'il se passe derrière.

```java
interface Animal {
    void feed();
}


class Lion implements Animal {

    @Override
    public void feed() {
    
        // Voilà à quoi ressemble du pseudo code, on comprend ce qu'il fait sans savoir ce que `me` ou `openBag()` représente réellement
        Food food = me.openBag().takeFoodForLions();
        me.feedCarefully(this, food);
    }
}

class Whale implements Animal {

    @Override
    public void feed() {
    
        int quantityToFeed = Zoo.maximalHungerOf(whale) - Zoo.foodAmountSoldForWhaleToday();
        
        if(quantityToFeed < 0) {
            ZooManager.report("The clients have fed the whale too much today :angryface:");
            return;
        }
        
        me.feed(this, new WhaleFood(quantityToFeed));
    }
}

class Duck implements Animal {

    @Override
    public void feed() {
        System.out.println("Employees don't need to feed the ducks. Clients do so more than enough already!");
    }
}
```

Excellent ! En utilisant le **polymorphisme**, on arrive à une super solution avec un code bien organisé. Enfin... vraiment ?
Il y a déjà un léger problème: est-ce réellement à l'animal de déterminer comment il va être nourri ? Pas vraiment en fait, puisque **l'alimentation des animaux devrait être gérée
par le zoo lui même**. Sinon on pourrait se retrouver avec un drôle d'animal comme ceci:

```java
class Ant implements Animal {

    @Override
    public void feed() {
        me.feed(this, new Steak("700kg"));
    }
}
```
et le budget exploserait, ça serait la faillite. Cette perte de contrôle est problématique, mais il y a encore pire.

Si vous avez regardé attentivement le pseudo code, vous verrez que l'alimentation de la baleine dépend de "à quel point" les visiteurs du zoo l'ont déjà nourri pendant la journée,
et les canards ne sont carréments pas nourris par le personnel. Ce qui implique que... les clients aussi devraient pouvoir nourrir les animaux du zoo. Et là on a un gros problème:
**les clients ne nourrissent pas de la même façon les animaux que le personnel**. Utilisons les règles ci-dessous pour la suite de l'exemple.

***
**Client**
- Ne nourrit pas les lions, c'est bien trop dangereux
- Peut nourrir la baleine en achetant de la nourriture pour baleine à l'entrée du zoo
- Peut nourrir les canards s'il possède du pain dans sa poche

**Employé**
- Nourrit les lions en étant prudent
- Nourrit la baleine en fonction de "à quel point" les clients l'ont nourrie pendant la journée
- Ne nourrit pas les canards, les clients jettent beaucoup trop de pain de toutes façons
***

On pourrait éventuellement renommer notre méthode `feed()` en `feedByEmployee()` et ajouter une méthode `feedByClient()`, mais... ça commencerait à devenir atroce, si on cherche à
rajouter d'autres manières de nourrir les animaux, il faudra implémenter une troisième méthode, puis une quatrième... sans compter que les animaux seront toujours ceux qui contrôlent
l'alimentation, ce qui n'est pas logique.

Une autre possibilité serait de ne pas avoir ces méthodes dans l'interface `Animal`, mais quelque part en vrac dans une classe `util`, mais cela serait encore pire (ne faites
jamais ça). Non seulement **l'organisation du code serait encore pire** qu'avec la solution précédente, mais en plus **on perd la beauté du polymorphisme** et on sera forcé d'avoir:
`feedLionByClient`, `feedLionByEmployee`, `feedWhaleByClient`, etc... Non décidément ça ne va pas.

## La solution

Le pattern visiteur est une solution qui permet d'amener de **l'ordre et de la logique** dans le code. On va commencer par créer une classe qui va être utile pour décrire
l'alimentation des animaux par un employé. Cette classe va s'appeler `ZooEmployeeVisitor`, mais ne cherchez pas à comprendre pour l'instant pourquoi `Visitor`.

```java
class ZooEmployeeVisitor {
        
    public void feedLion(Lion lion) {
    
        Food food = me.openBag().takeFoodForLions();
        me.feedCarefully(lion, food);
    }
        
    public void feedWhale(Whale whale) {
    
        int quantityToFeed = Zoo.maximalHungerOf(whale) - Zoo.foodAmountSoldForWhaleToday();
        
        if(quantityToFeed < 0) {
            ZooManager.report("The clients have fed the whale too much today :angryface:");
            return;
        }
        
        me.feed(whale, new WhaleFood(quantityToFeed));
    } 
        
    public void feedDuck(Duck duck) {
        System.out.println("Employees don't need to feed the ducks. Clients do so more than enough already!");
    }
}
```

Pour l'instant, rien de spécial: **on a délégué le comportement "alimentation des animaux par un employé" dans une classe à part**.
Maintenant, ajoutons le comportement "alimentation des animaux par un client". De nouveau, ne cherchons pas à comprendre pourquoi son nom comporte "Visitor".

```java
class ZooClientVisitor {
         
    public void feedLion(Lion lion) {
        System.out.println("Unable to feed the lions. They're too dangerous to be fed by clients.");
    }
        
    public void feedWhale(Whale whale) {
    
        Food food = Market.buyFoodForWhales();
        Zoo.throwFoodInCage(whale, food);
    }
    
    public void feedDuck(Duck duck) {
    
        if(!me.hasBreadInPockets()) {
            System.out.println("You have no bread to feed the ducks :(");
            return;
        }
           
        Bread bread = me.takeOutBreadFromPocket();
        bread.tornAppart();
        me.throwBread(bread);
    }
}
```

Hmmmm, ça sent les méthodes en commun, c'est une bonne chose. Faisons un peu de factorisation en ayant **une interface commune**, qu'on appellera `AnimalFeedingVisitor`.

```java
interface AnimalFeedingVisitor {
    void feedLion(Lion lion);
    void feedWhale(Whale whale);
    void feedDuck(Duck duck);
}
```

Parfait ! La logique du programme est désormais bien meilleure: c'est celui qui nourrit qui définit comment nourrir. Désormais, à partir de n'importe quel "nourrisseur d'animaux", on
peut appeler une méthode de notre choix qui correspond à l'animal à nourrir, et en fournissant à cette méthode un animal, ce dernier sera nourri correctement. Et si l'on veut ajouter
un nouvel animal, on devra ajouter une nouvelle méthode `feed`. 
Faisons encore un peu mieux en rendant possible l'inverse, c'est à dire nourrir un animal à partir d'un nourrisseur (au lieu d'utiliser un nourisseur pour nourrir un animal). Ajoutons une méthode dans
notre interface `Animal`:

```java
interface Animal {
    void receiveFoodBy(AnimalFeedingVisitor visitor);
}
```

L'implémentation de cette méthode par les différents animaux sera toujours la même, c'est à dire:

```java
@Override
public void receiveFoodBy(AnimalFeedingVisitor visitor) {
    visitor.feedX(this);
}
```

Mais alors, était-ce vraiment nécessaire ? `a.f(b)` au lieu de `b.f(a)` ? Eh bien ça peut l'être. Imaginons une maman canard, qui serait également considéré comme "nourrissable".
On pourrait alors décider dans cette classe de nourrir chacun des bébés canards de la maman individuellement quand la nourriture est jetée. Par exemple:

```java
class DuckMom extends Duck {

    private List<Duck> children;
    
    public DuckCage(List<Duck> children) {
        this.children = children;
    }
    
    @Override
    public void accept(AnimalFeedingVisitor visitor) {
    
        visitor.feedDuck(this);
        
        for(Duck child : children) {
            visitor.feedDuck(child);
        }
    }
}
```

Et finalement c'est logique ! Une fois la nourriture jetée, l'animal a bien le pouvoir de décider comment la gérer. De manière générale, on préfère simplement `myAnimal.feed(feeder)`
plutôt que `feeder.feed(myAnimal)` parce que c'est plus intuitif, et que ça donne un peu de pouvoir à l'animal à propos des étapes de l'alimentation, tout en laissant le contrôle au
nourrisseur de la "procédure" d'alimentation même.

Finalement, le code principal ressemblera à quelque chose comme cela:

```java
import java.util.*;

class Main {
    
    public static void main(String... args) {
    
        AnimalFeedingVisitor feeder = getAnyFeeder();
        
        List<Animal> animals = Arrays.asList(
            new Lion(), new Lion()
            new Whale(),
            new Duck(), new Duck(), new Duck()
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
```

## Conventions

Vous ne l'avez peut-être pas réalisé, mais vous venez d'utiliser le pattern visiteur pour résoudre ce problème. En revanche, le code ci-dessus n'est pas encore totalement un pattern
visiteur pur, car des conventions de nommage n'ont pas été respectées. Elles sont utiles car cela permet de rapidement reconnaître le pattern.

- les méthodes de l'interface `Visitor` (dans notre exemple `AnimalFeedingVisitor`) devraient toutes avoir le même nom, à savoir (ici) `feed` ou plus généralement `visit`.
- La méthode `receiveFoodBy` qui a comme but d'accepter un nourrisseur devrait s'appeler `accept`.
- Le nom de la classe / interface qui contient cette méthode `accept` devrait finir en `Element`

## Limitations

Bien que très pratique, ce pattern pose quand même un petit problème: **il entrave la maintenabilité du code**. En effet, à chaque ajout d'un nouvel animal, non seulement il faudra
implémenter ce dernier (dans notre exemple, les animaux ne font rien, mais si on complexifiait la situation cela serait différent), **mais il faudra en plus ajouter manuellement une
méthode `feed` dans l'interface visiteur**, et que toutes ses sous-classes l'implémentent.

## Conclusion

La pattern visiteur est un pattern très pratique qui favorise grandement l'organisation et la logique du code, tout en étant très facile à implémenter. En revanche, il est important
de ne pas en abuser afin d'équilibrer la logique du code avec la maintenabilité, facteur tout aussi important.

En résumé, la recette de ce pattern est la suivante:

- Vous avez une classe mère `Element`
- Vous voulez définir plusieurs comportements qui interagissent avec des `Element`, et chaque comportement agit différemment en fonction de à quelle sous-classe de `Element` il a affaire
- Créez une interface `Visitor`, qui contient autant de méthode `visit` qu'il n'y a d'implémentations de `Element`. Chacune de ces méthodes prend en paramètre un objet d'un type qui est une sous-classe de `Element`.
- Créez une méthode `accept` dans `Element`, qui demande un `Visitor` en paramètre. Les implémentations de cette méthode sont presque tout le temps `visitor.visit(this)`, mais pour les "groupes d'éléments", une boucle peut être effectuée
- Chaque implémentation de `Visitor` définit un comportement précis pour chaque sous-classe de `Element`

## Sources

[Article Wikipedia](https://en.wikipedia.org/wiki/Visitor_pattern)

[Visitor Design Pattern in Java - Baeldung](https://www.baeldung.com/java-visitor-pattern)

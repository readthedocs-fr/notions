# Objets Calisthéniques

(Traduction de l'article : https://williamdurand.fr/2013/06/03/object-calisthenics/)

## Rapide présentation 

Avant tout, il est important de rappeler que les règles des objets calisthéniques ne sont qu'un **guide pour avoir un code plus propre**, il n'est en aucun cas *obligatoire* de respecter ces règles, bien que *conseillé*.

D'autre part, ces règles sont là pour changer votre façon de programmer. Essayez d'en suivre un maximum au départ, puis abandonnez celles que vous désirez. Cependant, les règles **6** et **9** sont particulièrement importantes : vous ne devriez pas les abandonner. La règle **9** reste néanmoins la plus importante.

Pour qu'un objet soit déclaré comme étant calisthénique il doit respecter un ensemble de **9 règles** au total. Nous allons les étudier ensemble avec des exemples et en détaillant leur utilité.

## Règle 1 : Un seul niveau d'indentation par méthode

Dans l'optique d'optimiser la **lisibilité** de votre code, vous devez vous restreindre à un seul niveau d'indentation dans une méthode. Dans les faits, vous ne pourrez pas toujours imaginer facilement le comportement de votre méthode dans votre tête. Si l'on prend un exemple avec une boucle dans une boucle, ça devient tout de suite plus parlant, vous ne pouvez pas facilement imaginer ce que fait exactement à tout instant votre méthode. Prenons cet exemple : 
```Java
class Board {

    private int[][] data;

    public String board() {
        StringBuilder builder = new StringBuilder();

        // 0
        for (int i = 0; i < 10; i++) {
            // 1
            for (int j = 0; j < 10; j++) {
                // 2
                builder.append(data[i][j]);
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
```
Une manière simple de rendre cette méthode beaucoup plus **lisible** et **compréhensible** est d'appliquer le [*Extract Method Pattern*](http://refactoring.com/catalog/extractMethod.html). Appliquer ce pattern ne réduira pas le nombre de ligne de code (et ce n'est pas ce qu'on veut) mais il rendra la méthode beaucoup plus **intuitive** pour un développeur qui cherche à comprendre ce qu'elle fait. Derrière le nom du pattern que vous pourriez trouver un peu barbare se cache une méthode simple : extraire d'une "méthode mère" le maximum de méthodes pour simplifier et clarifier son code. Ici par exemple, cela donnerait :
```Java
class Board {
    public String board() {
        StringBuilder builder = new StringBuilder();

        collectRows(builder);

        return builder.toString();
    }

    private void collectRows(StringBuilder builder) {
        for (int i = 0; i < 10; i++) {
            collectRow(builder, i);
        }
    }

    private void collectRow(StringBuilder builder, int row) {
        for (int i = 0; i < 10; i++) {
            builder.append(data[row][i]);
        }

        builder.append("\n");
    }
}
```
Revenons sur les changements, il y a l'apparition de deux nouvelles méthodes : `collectRows` et `collectRow`. Elles sont ici pour apporter de la **précision** sur ce que l'on fait, on sait que dans `collectRow` on explore et "collecte" les caractères présents sur une colonne et que logiquement dans `collectRows` on explore et collecte plusieurs colonnes. Rien que le fait d'avoir rajouté ces deux méthodes rend le processus de la méthode `board` plus **compréhensible**.  

## Règle 2 : N'utilisez pas le mot clé else

Le mot clé *else* est bien connu car il est intégré dans la construction *if/else* qui est présente dans presque tous les langages de programmation.
Or, lire des instructions conditionnelles imbriquées n'est pas agréable.
C'est exactement pourquoi il faut l'éviter. 
Il est beaucoup plus facile d'ajouter une nouvelle branche au code existant que de le réfactoriser vers une meilleure solution.
Et à force de faire cela, vous vous retrouvez souvent avec un très mauvais code qui est **illisible et incompréhensible**.

```Java
public void login(String username, String password) {
    if (userRepository.isValid(username, password)) {
        redirect("homepage");
    } else {
        addFlash("error", "Bad credentials");

        redirect("login");
    }
}
```

Cependant, il existe heureusement des solutions pour éviter ça, comme par exemple utliser le **retour anticipé** de la méthode.

```Java
public void login(String username, String password) {
    if (userRepository.isValid(username, password)) {
        redirect("homepage");
        return;
    }

    addFlash("error", "Bad credentials");

    redirect("login");
    return;
}
```

Votre condition peut être **optimiste**, ce qui signifie que vous avez des conditions pour vos cas d'erreur et que le reste de votre méthode suit le scénario par défaut,
ou vous pouvez adopter une approche **défensive**, ce qui signifie que vous mettez le scénario par défaut dans une condition, et s'il n'est pas satisfait,
vous renvoyez un état d'erreur.
C'est mieux car cela évite les problèmes potentiels auxquels vous n'avez pas pensé.

Comme autre alternative, vous pouvez également introduire une variable afin de rendre votre instruction `return` paramétrable. Bien que pratique, ce n'est cependant pas toujours possible.

```Java
public void login(String username, String password) {
    String redirectRoute = "homepage";

    if (!userRepository.isValid(username, password)) {
        addFlash("error", "Bad credentials");
        redirectRoute = "login";
    }

    redirect(redirectRoute);
}
```

En outre, il convient de mentionner que la programmation orientée objet nous offre des fonctionnalités **puissantes**, telles que le **polymorphisme**. 
Enfin, les patterns [**Null Object**](https://en.wikipedia.org/wiki/Null_object_pattern), [**State**](https://en.wikipedia.org/wiki/State_pattern#:~:text=The%20state%20pattern%20is%20a,concept%20of%20finite%2Dstate%20machines.) et [**Strategy**](https://en.wikipedia.org/wiki/Strategy_pattern#:~:text=In%20computer%20programming%2C%20the%20strategy,family%20of%20algorithms%20to%20use.) peuvent également vous aider !

## Règle 3 : Encapsulez tous les primitifs et chaînes de caractères

Suivre cette règle est assez simple, il vous suffit d'encapsuler tous les primitifs dans les objets, afin d'éviter l'anti-pattern [Primitive Obsession](https://refactoring.guru/smells/primitive-obsession).

Si la variable primitive a un comportement, vous DEVEZ l'encapsuler.
Pour cela, vous pouvez utiliser des **Value Objects** comme `Money` ou `Hour` par exemple. (Une fiche sur les value objects arrive bientôt)

## Règle 4 : Une collection par classe

Toute classe qui contient une collection ne doit contenir **aucun autre field**.
Si vous avez un ensemble d'éléments (une collection) et que vous souhaitez les manipuler, **créez une classe dédiée à cet ensemble**.

Chaque collection est **enveloppée dans sa propre classe**, donc maintenant tous les comportements liés à cette collection doivent être implémentés dans la classe contenant la collection (par exemple les méthodes de filtrage).

Pour illustrer cette règle, voici un exemple provenant du code de la notion de value objects, nous avons une classe qui représente un ordre de commande :
```java

public class Order {

    // Not a value object because it has an id
    private final UUID orderId;
    private Products products;
    private Address addressToDeliver;

    public Order(Products products, Address addressToDeliver) {
        this.orderId = UUID.randomUUID();
        this.products = products;
        this.addressToDeliver = addressToDeliver;
    }
}

```

Cette classe est composée d'un `UUID`, d'une `Address` et ce qui va nous intéresser ici d'un objet `Products` qui représente une liste de `Product`.

Jetons un oeil à cette classe :

```java
public class Products {

    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Money getProductsCost() {
        Money money = new Money(0);
        for (Product product : products) {
            Money productCost = product.getCost();
            money = money.addMoney(productCost);
        }
        return money;
    }
}
```

Nous voyons bien que la classe ne comporte qu'un field qui est une collection de `Product`, la règle est donc respectée.
Nous pouvons donc observer que la logique pour gérer plusieurs `Product` est bien présente dans cette classe. 
On pourrait aussi facilement implémenter des méthodes de filtrage qui retourneraient tous les `Product` avec un prix minimum de 10€ par exemple.

Maintenant, regardons comment serait organisé le code s'il n'y avait pas de classe pour gérer cette collection, on aurait sûrement eu quelque chose de ce style : 

```java
public class Order {

    // Not a value object because it has an id
    private final UUID orderId;
    private List<Product> products;
    private Address addressToDeliver;

    public Order(List<Product> products, Address addressToDeliver) {
        this.orderId = UUID.randomUUID();
        this.products = products;
        this.addressToDeliver = addressToDeliver;
    }

    public Money getProductsCost() {
        Money money = new Money(0);
        for (Product product : products) {
            Money productCost = product.getCost();
            money = money.addMoney(productCost);
        }
        return money;
    }
}
```

Nous voyons bien que ça aurait été à l'objet `Order` de gérer cette logique, alors qu'il aurait pu déléguer cela à une classe qui gère une collection de `Product`. 
Pour l'instant ce n'est pas choquant, mais si on rajoute différentes méthodes de filtrage, la classe va vite commencer à s'allonger, ce qui ne respecterait pas la [règle 7](#r%C3%A8gle-7--ne-pas-avoir-dentit%C3%A9s-trop-grosses) et serait très difficile à lire et maintenir.

## Règle 5 : Un point par ligne (One dot per line)

Ce *point* est celui que vous utilisez pour **appeler des méthodes** en Java. Selon le langage, cela peut varier : par exemple, en PHP ce serait une flèche.

Fondamentalement, la règle dit que vous ne devez pas enchaîner les appels de méthodes. 
Cependant, cela ne s'applique pas aux [interfaces Fluent](http://flippinawesome.org/2013/05/20/fluent-apis-and-method-chaining/) et plus généralement à tout ce qui implémente le [modèle de chaînage de méthodes](https://martinfowler.com/dslCatalog/methodChaining.html) (par exemple, un générateur de requêtes ou un builder).

Pour les autres classes, vous devez respecter cette règle. C'est l'utilisation directe de [la loi de Déméter](http://wiki.c2.com/?LawOfDemeter), qui dit de ne parler **qu'à vos amis** et de ne pas parler à des étrangers.

Regardez ces classes :
```Java
class Location {
    public Piece current;
}
```
```Java
class Piece {
    public String representation;
}
```
```Java
class Board {
    public String boardRepresentation() {
        StringBuilder builder = new StringBuilder();

        for (Location loc : squares()) {
            builder.append(loc.current.representation.substring(0, 1));
        }

        return builder.toString();
    }
}
```
Il est normal d'avoir des attributs publics dans `Piece` et `Location`.
En fait, avoir une propriété `publique` ou une propriété `private` avec getter/setter est, selon les principes des objets calisthéniques, la même chose (voir [règle 9](#règle-9--nutilisez-pas-de-getter-et-de-setter-)).

Cependant, la méthode `boardRepresentation()` est horrible, jetez un œil à cette ligne :

`builder.append(loc.current.representation.substring(0, 1));`

Il accède à une `Location`, puis à son field `piece`, puis au field `representation` de la référence de `Piece` sur laquelle il effectue une action.
C'est loin d'être un point par ligne.

Mais, [la loi de Déméter](http://wiki.c2.com/?LawOfDemeter) vous dit de parler uniquement à vos amis, alors faisons-le :
```Java
class Location {
    private Piece current;

    public void addTo(StringBuilder builder) {
        current.addTo(builder);
    }
}
```
Rendre privé le field `piece` garantit que vous n'essaierez pas d'interagir avec et ainsi de déplacer de la logique qui devrait se trouver dans la classe `Location`.
Cependant, comme vous devez effectuer une action sur ce field, vous avez besoin d'une nouvelle méthode `addTo()`. 
Ce n'est pas à l'objet `Location` de déterminer comment le `Piece` sera ajouté, alors changeons ça :

```Java
class Piece {
    private String representation;

    public String character() {
        return representation.substring(0, 1);
    }

    public void addTo(StringBuilder builder) {
        builder.append(character());
    }
}
```

Là encore, vous devez modifier la visibilité de votre attribut. 
Pour rappel, [le principe *ouvert/fermé*](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle) dit que les entités logicielles (classes, modules, fonctions, etc.) doivent être ouvertes à l'extension, mais fermées à la modification.

De plus, extraire le code pour obtenir le premier caractère de `representation` dans une nouvelle méthode semble être une bonne idée car il peut être réutilisé à un moment donné. 
Enfin, voici la classe `Board` refactorisée :

```Java
class Board {
    public String boardRepresentation() {
        StringBuilder builder = new StringBuilder();

        for (Location location : squares()) {
            location.addTo(builder);
        }

        return builder.toString();
    }
}
```

Et voilà qui respecte cette règle et qui est en plus, plus lisible !

## Règle 6 : N'abrégez pas !

La bonne question étant : « **Pourquoi diable voulez-vous abréger ?** »

Vous pouvez répondre que c'est parce que vous écrivez le même nom encore et encore ?
Et je répondrai que cette méthode est réutilisée plusieurs fois, et qu'elle ressemble à une **duplication de code**.

Vous direz donc ensuite que le nom de la méthode est de toute façon trop long. 
Et je vous dirai que votre classe a peut-être de **multiples responsabilités**, ce qui est mauvais car cela viole le principe de [responsabilité unique](https://en.wikipedia.org/wiki/Single-responsibility_principle).

Je dis souvent que si vous ne trouvez pas un nom décent pour une classe ou une méthode, il y a probablement un problème. 
C'est une règle que j'utilise pour concevoir un logiciel en nommant des méthodes ou des attributs.

De plus si vous abrégez, la compréhension de votre code peut devenir ambiguë, ce qui n'est pas une bonne chose, même pour vous quand vous allez vous relire plusieurs semaines après avoir écrit ce code. 

N'abrégez pas, point final. 

## Règle 7 : Ne pas avoir d'entités trop grosses

Tout d'abord, par entité j'entends les packages, les classes, les méthodes, bref, tout ce qui peut être extensible.

Pas de classe sur 50 lignes et pas de package sur 10 fichiers.
Cela dépend de vous, mais je pense que vous pouvez changer le nombre de lignes de 50 à 150.

L'idée derrière cette règle est que les fichiers longs sont plus difficiles à lire, plus difficiles à comprendre et plus difficiles à gérer.
Donc plus vous avez des entités petites, plus elles sont faciles à comprendre et donc à maintenir, et donc plus vous gagnez de temps !

## Règle 8 : Pas de classe avec plus de deux attributs d'instance

Cette règle est probablement la plus difficile, mais elle favorise une cohésion élevée et une meilleure encapsulation dans votre classe.

Il faut noter que cette règle est liée à la [règle 3](#règle-3--encapsulez-tous-les-primitifs-et-chaînes-de-caractères).

L'idée derrière cette règle est de distinguer deux types de classes :

- Les classes qui **conservent** l'état d'une **seule variable** (value objects).
- Les classes qui **coordonnent** **deux variables distinctes**.

Deux instances comme limite est un choix arbitraire qui vous oblige à beaucoup découpler vos classes.

## Règle 9 : N'utilisez pas de getter et de setter !

Probablement la règle la plus importante de toute, elle favorise une bonne conception logicielle.

Il est normal d'utiliser des getters pour obtenir l'état d'un objet, tant que vous n'utilisez pas le résultat pour prendre des décisions en dehors de l'objet. 
**Toute décision basée entièrement sur l'état d'un objet doit être prise à l'intérieur de l'objet lui-même**.

C'est pourquoi les getters/setters [sont souvent considérés comme mauvais](https://stackoverflow.com/questions/565095/are-getters-and-setters-poor-design-contradictory-advice-seen). Là encore, ils violent le [principe ouvert/fermé](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle).

Prenons un exemple : 
```Java
// Game
private int score;

public void setScore(int score) {
    this.score = score;
}

public int getScore() {
    return score;
}

// Usage
game.setScore(game.getScore() + ENEMY_DESTROYED_SCORE);
```

Dans le code ci-dessus, le `getScore()` est utilisé pour prendre une décision, vous choisissez comment augmenter votre score, au lieu de laisser cette responsabilité à l'instance de `Game`.

Une meilleure solution serait de **supprimer les getters/setters**, et de fournir des méthodes qui **ont du sens**. 
N'oubliez pas que vous devez [**dire à** la classe de faire quelque chose, et vous ne devez **pas le demander**](https://martinfowler.com/bliki/TellDontAsk.html). 
Dans l'exemple qui suit, vous dites à l'instance de `Game` de mettre à jour votre score en fonction de `ENEMY_DESTROYED_SCORE`.

```Java
// Game
public void addScore(int delta) {
    score += delta;
}

// Usage
game.addScore(ENEMY_DESTROYED_SCORE);
```

C'est bien la responsabilité de `Game` de déterminer comment mettre à jour le score.

Dans ce cas, vous pouvez garder le `getScore()` comme vous voudrez peut-être l'afficher quelque part sur l'interface utilisateur, mais gardez à l'esprit que les setters **ne devraient pas être autorisés**.

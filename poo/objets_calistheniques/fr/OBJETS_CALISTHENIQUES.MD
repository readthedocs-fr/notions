# Objets Calisthéniques

## Rapide présentation 

Avant tout, il est important de rappeler que les règles des objects calisthénics ne sont qu'un **guide pour avoir un code plus propre**, il n'est en aucun cas *obligatoire* de respecter ces règles, bien que *conseillé*.

Pour qu'un objet soit déclaré comme étant calisthénic il doit respecter un ensemble de **9 règles** au total. Nous allons les étudier ensemble avec des exemples et en détaillant leur utilités.
D'autrepart, ces règles sont là pour changer votre façon de programmer. Essayez d'en suivre un maximum au départ, puis abandonnez celles que vous désirez. Cependant, les règles **6** et **9** sont particulièrement importantes : vous ne devriez pas les abandonner. La règle **9** reste néanmoins la plus importante.


## Règle 1 : Un seul niveau d'indentation par méthode

Dans l'optique d'optimiser la **lisibilité** de votre code, vous devez vous restreindre à un seul niveau d'indentation dans une méthode. Dans les faits, vous ne pourrez pas toujours imaginez facilement le comportement de votre méthode dans votre tête. Si l'on prend un exemple avec une boucle dans une boucle, ça devient tout de suite plus parlant, vous ne pouvez pas facilement imaginez ce que fait exactement à tout instant votre méthode. Prenons cet exemple : 
```Java
class Board {
    public String board() {
        StringBuilder buf = new StringBuilder();

        // 0
        for (int i = 0; i < 10; i++) {
            // 1
            for (int j = 0; j < 10; j++) {
                // 2
                buf.append(data[i][j]);
            }
            buf.append("\n");
        }

        return buf.toString();
    }
}
```
Une manière simple de rendre cette méthode beaucoup plus **lisible** et **compréhensible** est d'appliquer le *Extract Method Pattern*. Appliquer ce pattern ne réduira pas le nombre de ligne de code (et ce n'est pas ce qu'on veut) mais il rendra la méthode beaucoup plus **intuitive** pour un développeur qui cherche à comprendre ce qu'elle fait. Derrière le nom du pattern que vous pourriez trouver un peu barbare se cache une méthode simple : crée le plus de méthode utile possible. Ici par exemple, cela donnerait :
```Java
class Board {
    public String board() {
        StringBuilder buf = new StringBuilder();

        collectRows(buf);

        return buf.toString();
    }

    private void collectRows(StringBuilder buf) {
        for (int i = 0; i < 10; i++) {
            collectRow(buf, i);
        }
    }

    private void collectRow(StringBuilder buf, int row) {
        for (int i = 0; i < 10; i++) {
            buf.append(data[row][i]);
        }

        buf.append("\n");
    }
}
```
Revenons sur les changements, il y a l'apparition de deux nouvelles méthodes : `collectRows` et `collectRow`. Elles sont ici pour apporter de la **précision** sur ce que l'on fait, on sait que dans `collectRow` on explore et "collecte" les caractères présents sur une colonne et que logiquement dans `collectRows` on explore et collecte plusieurs colonnes. Rien que le fait d'avoir rajouté ces deux méthodes rend le processus de la méthode `board` plus **compréhensible**.  

## Règle 2 : N'utilisez pas le mot clé else

Le mot clé *else* est bien connu car il est intégré dans la construction *if/else* qui est présente dans presque tous les langages de programmation.
Nous sommes d'accord que lire des instructions conditionnelles imbriquées n'est pas agréable.
C'est exactement pourquoi il faut l'éviter. 
Il est beaucoup plus facile d'ajouter une nouvelle branche au code existant que de le réfactoriser vers une meilleure solution.
Et à force de faire cela, vous vous retrouvez souvent avec un très mauvais code qui est __illisible et incompréhensible__.

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
        return redirect("homepage");
    }

    addFlash("error", "Bad credentials");

    return redirect("login");
}
```

Votre condition peut être **optimiste**, ce qui signifie que vous avez des conditions pour vos cas d'erreur et que le reste de votre méthode suit le scénario par défaut,
ou vous pouvez adopter une approche **défensive**, ce qui signifie que vous mettez le scénario par défaut dans une condition, et s'il n'est pas satisfait,
vous renvoyez un état d'erreur.
C'est mieux car cela évite les problèmes potentiels auxquels vous n'avez pas pensé.

Comme autre alternative, vous pouvez également introduire une variable afin de rendre votre instruction return paramétrable. Bien que pratique, ce n'est cependant pas toujours possible.

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
Enfin, les patterns **Null Object**, **State** et **Strategy** peuvent également vous aider !

## Règle 3 : Encapsulez tous les primitifs et chaîne de caractères

Suivre cette règle est assez simple, il vous suffit d'encapsuler tous les primitifs dans les objets, afin d'éviter l'anti-pattern Primitive Obsession.

Si la variable primitive a un comportement, vous DEVEZ l'encapsuler.
Pour cela, vous pouvez utiliser des **Value Objects** comme `Money` ou `Hour` par exemple. (Une fiche sur les value objets arrive bientôt)

## Règle 4 : Une collection par classe

Toute classe qui contient une collection ne doit contenir **aucun autre field**.
Si vous avez un ensemble d'éléments (une collection) et que vous souhaitez les manipuler, __créez une classe dédiée à cet ensemble__.

Chaque collection est **enveloppée dans sa propre classe**, donc maintenant tous les comportements liés à cette collection doivent être implémentés dans la classe contenant la collection (par exemple les méthodes de filtrage).

## Règle 5 : Un point par ligne (One dot per line)

Ce *point* est celui que vous utilisez pour **appeler des méthodes** en Java. Ce serait une flèche en PHP, mais qui utilise PHP de toute façon ? :D

Fondamentalement, la règle dit que vous ne devez pas enchaîner les appels de méthode. 
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
        StringBuilder buf = new StringBuilder();

        for (Location loc : squares()) {
            buf.append(loc.current.representation.substring(0, 1));
        }

        return buf.toString();
    }
}
```
Il est normal d'avoir des attributs publics dans `Piece` et `Location`.
En fait, avoir une propriété `publique` ou une propriété `private` avec getter/setter est la même chose (voir règle 9).

Cependant, la méthode `boardRepresentation()` est horrible, jetez un œil à cette ligne :

`buf.append(loc.current.representation.substring(0, 1));`

Il accède à une `Location`, puis à son field `piece`, puis au field `representation` de la référence de `Piece` sur laquelle il effectue une action.
C'est loin d'être un point par ligne.

Mais, [la loi de Déméter](http://wiki.c2.com/?LawOfDemeter) vous dit de parler uniquement à vos amis, alors faisons-le :
```Java
class Location {
    private Piece current;

    public void addTo(StringBuilder buf) {
        current.addTo(buf);
    }
}
```
Rendre privée le field `piece` garantit que vous n'essaierez pas de faire quelque chose de mal avec.
Cependant, comme vous devez effectuer une action sur cee field, vous avez besoin d'une nouvelle méthode `addTo()`. 
Ce n'est pas à l'objet `Location` de déterminer comment le `Piece` sera ajouté, alors changeons ça :

```Java
class Piece {
    private String representation;

    public String character() {
        return representation.substring(0, 1);
    }

    public void addTo(StringBuilder buf) {
        buf.append(character());
    }
}
```

Là encore, vous devez modifier la visibilité de votre attribut. 
Pour rappel, [le principe *ouvert/fermé*](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle) dit que les entités logicielles (classes, modules, fonctions, etc.) doivent être ouvertes pour l'extension, mais fermées pour la modification.

De plus, extraire le code pour obtenir le premier caractère du representation dans une nouvelle méthode semble être une bonne idée car il peut être réutilisé à un moment donné. 
Enfin, voici la classe `Board` refactorisée :

```Java
class Board {
    public String boardRepresentation() {
        StringBuilder buf = new StringBuilder();

        for (Location location : squares()) {
            location.addTo(buf);
        }

        return buf.toString();
    }
}
```

Et voilà qui respecte cette règle et qui est en plus, plus lisible !

## Règle 6 : N'abrégez pas !

La bonne question étant : **pourquoi diable voulez-vous abréger ?**

Vous pouvez répondre que c'est parce que vous écrivez le même nom encore et encore ?
Et je répondrais que cette méthode est réutilisée plusieurs fois, et qu'elle ressemble à une **duplication de code**.

Vous direz donc ensuite que le nom de la méthode est de toute façon trop long. 
Et je vous dirais que votre classe a peut-être de **multiples responsabilités**, ce qui est mauvais car cela viole le principe de [responsabilité unique](https://en.wikipedia.org/wiki/Single-responsibility_principle.

Je dis souvent que si vous ne trouvez pas un nom décent pour une classe ou une méthode, il y a probablement un problème. 
C'est une règle que j'utilise pour concevoir un logiciel en nommant des méthodes ou des attributs.

De plus si vous abrégez, la compréhension de votre code peut devenir ambiguë, ce qui n'est pas une bonne chose, même pour vous quand vous allez vous relire plusieurs semaines après avoir écrit ce code. 

N'abbrégez, point final. 

## Règle 7 : Ne pas avoir d'entités trop grosses

Tout d'abord, par entité j'entends les packages, les classes, les méthodes, bref, tout ce qui peut être extensible.

Pas de classe sur 50 lignes et pas de package sur 10 fichiers.
Cela dépend de vous, mais je pense que vous pouvez changer le nombre de lignes de 50 à 150.

L'idée derrière cette règle est que les fichiers longs sont plus difficiles à lire, plus difficiles à comprendre et plus difficiles à gérer.
Donc plus vous avez des entités petites, plus elles sont faciles à comprendre et donc à maintenir, et donc plus vous gagnez de temps !

## Règle 8 : Pas de classes avec plus de deux instances de classe

Cette règle est probablement la plus difficile, mais elle favorise une cohésion élevée et une meilleure encapsulation dans votre classe.

Il faut noter que cette règle est lié à la règle 3.

L'idée derrière cette règle est de distinguer deux types de classes :

-Les classes qui **conservent** l'état d'une **seule variable** (value objects).
-Les classes qui **coordonnent** **deux variables distinctes**.

Deux instances comme limite est un choix arbitraire qui vous oblige à beaucoup découpler vos classes.

## Règle 9 : N'utilisez pas de getter et de setter !

Ma règle préférée.

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
N'oubliez pas que vous devez **dire à** la classe de faire quelque chose, et vous ne devez **pas le demander**. 
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

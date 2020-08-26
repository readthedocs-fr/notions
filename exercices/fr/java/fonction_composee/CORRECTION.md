# Fonction composée - Correction

> Avant de commencer, sachez qu'il existe toujours plusieurs solutions. 
Si celle se trouvant ci dessous ne correspond pas à la vôtre, elle n'est pas forcément fausse. 
Enjoy !

> Écrit par [Alexandre Orékhoff](https://github.com/Hokkaydo)

## Do you know da wae ?

Avant toute chose, il s'agit de bien comprendre l'énoncé.

La première phrase disait : 

> Je veux que vous écriviez une fonction capable de construire la fonction composée de plusieurs fonctions

Okey, donc, on doit écrire une méthode capable de déterminer et renvoyer la composée de 2+ fonctions.

> Nice, t'as répété l'énoncé

C'est pour l'avoir bien en tête (sisi je vous assure).

On va commencer par appliquer la première étape et faire une **méthode** qui peut **composer 2 fonctions en une seule**.

Il faut donc que notre méthode prenne **2 fonctions en paramètre**. Il faut également que notre méthode **renvoie cette fonction composée.**

Pour écrire ce code, nous allons nous servir du package `java.util.function` et des classes/interfaces qui s'y trouvent, notamment l'interface `Function`.

```java
public Function compose(Function f, Function g) {

}
```

Bon, jusque là, pas trop de problème, nous avons la signature de notre méthode. Il reste cependant un léger souci, j'ai déclaré mes types de retour et de paramètre en `rawtypes`. `Rawtype` traduisible par `type brut` en français signifie que je n'ai pas spécifié les types génériques des objets `Function` avec lesquels je vais travailler.

Elles n'acceptent donc par défaut que des `Object`. 

---

> Euh wait wait wait, généquoi ?

Générique

Ne faites pas cette tête étonnée, je vais vous expliquer.

Vous êtes d'accord avec moi qu'une variable ou une méthode, ça a besoin de type. Tant pour se définir que pour dire à quoi on sert.

Vous pouvez par exemple dire que la variable `x`sera de type `int` ce qui signifie qu'elle contiendra un nombre n'est ce pas ?
Vous pouvez également dire que la méthode `concat` prendra 2 `String`en paramètre et renverra un `String`.

Et bien imaginez, si ces types pouvaient varier 😮

C'est à ça que servent les types génériques. 
Une classe générique se définit comme suit : 

```java
public class MyGenericClass<S, T, U> {

}
```

Ici, j'ai défini 2 variables de types, ce sont mes paramètres génériques. 
Je peux à présent faire ceci dans mon code : 

```java
public class MyGenericClass<S, T> {
    T firstVariable;

    public MyGenericClass(S value){
        this.firstVariable = value;
    }
}
```

En effet, je suis sûr que `firstVariable` et `value` sont de même type : `S`. Je peux donc faire ce que je veux avec. 

Les listes, ainsi que toutes les classes qui implémentent l'interface `Collection`, sont d'excellents exemples de générécité.

Je peux également avoir ceci :

```java
public class MyGenericClass<S, T> {
    private S firstVariable;

    public MyGenericClass(S value){
        this.firstVariable = value;
    }

    public T doSomething(S sValue, T tValue) {
        System.out.println(sValue.toString() + tValue.toString() + firstVariable.toString());
        return tValue;
    }
}
```

Ici, lors de l'utilisation de cette méthode, je suis sûr de récupérer en retour un objet du même type que celui passé en second paramètre, ils sont tous les deux de type `T`. 

Un petit exemple rapide de généricité pourrait être le suivant :

J'ai une classe `Person` qui représente une personne lambda. 
 :
```java
public class Person {
    private String name;
    private String surname;
    private int size;
    private int weight;
    
    public Person(String name, String surname, int size, int weight) {
        this.name = name;
        this.surname = surname;
        this.size = size;
        this.weight = weight;
    }
}
```
J'aimerais qu'elle contienne 2 méthodes : `getIdentity()` et `getMeasurements()`.
Cependant, comme vous pouvez le remarquer, ces deux méthodes sont censées renvoyer chacune 2 paramètres. `name` et `surname` pour la première, et `size` et `weight` pour la seconde. Il me faudrait donc créer une classe qui peut agir comme "boîte de stockage" pour renvoyer ces 2 éléments à chaque fois :

```java
public Identity {

    private String name;
    private String surname;
    
    public Identity(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    
    //Getter
    
    //Setter
}
```
Voilà pour les noms.
Et rebelote ...

```java
public Measurements {

    private int size;
    private int weight;
    
    public Measurements(int size, int weight) {
        this.size = size;
        this.weight = weight;
    }
    
    //Getter
    
    //Setter
}
```
Et maintenant, simplement, les méthodes qui utilisent ces classes :

```java
    //Code de la classe Person
    
    public Identity getIdentity() {
        return new Identity(this.name, this.surname);
    }
    
    public Measurements getMeasurements() {
        return new Measurements(this.size, this.weight);
    }
```

Bon.
Techniquement ce code fonctionne...
Le problème ? Eh bien... on se répète !!

Vous voyez bien que nos deux classes `Identity` et `Measurements` ont sensiblement la même structure. Seuls leurs types changent.
Bonne nouvelle, c'est là que la généricité devient utile ;)

La générécité permet de garder une même structure tout en de changeant les types de paramètre et de renvoi.

Tout d'abord, la signature de la classe (nom, `modifiers` (`public`, `final` etc), types). Nous essayons de `générifier` 2 classes qui ne travaillent qu'avec un seul type chacune : `String` pour la classe `Identity` et `int` pour la classe `Measurements`.
Donc, résumé : 1 types, 2 variables. 
2 variables, 2 valeurs, `BiValue` vous semble une bonne idée ?

```java
public class BiValue<T> {

}
```

Super
Ici vous voyez donc comment se déclare un `type générique` : son nom, qui l'identifiera dans toute la classe, il suit le nom de la classe et il est encadré de 2 chevrons. Par convention, on nomme généralement le type générique d'une seule lettre. En fonction du nombre et de l'utilité qu'on compte accorder à ces types, les lettres peuvent changer. En général :
- Un seul type générique -> `<T>
- Comme seul type générique, on peut également trouver `<R>` pour `Result`, résultat en français.
- Une paire de clé - valeur (comme une `Map` par exemple) -> `<K, V>` (et oui, pour définir plusieurs types génériques, on les sépare simplement d'une virugle). `K` pour la clé, soit `Key` en anglais et évidemment `V` pour valeur, `Value` en anglais.
- 2 types -> `<T, U>`
- 3 et 4 types -> `<S, T, U>` et `<S, T, U, V>`
- 5 types et plus (très rare et généralement pas une bonne idée) -> On rajoute une lettre en remontant à chaque fois d'une lettre en arrière dans l'alphabet en partant de `S` (`<R, S, T, U, V>`, etc, ...)

Générifions maintenant cette classe :

```java
public class BiValue<T> {
    
    //Comme on ne sait pas ce qu'elle vont contenir, je préfère ne pas leur donner de nom trop explicite.
    private T firstValue;
    private T secondValue;
    
    public BiValue(T firstValue, T secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    
    public T getFirstValue() {
        return firstValue;
    }
    public T getSecondValue() {
        return secondValue;
    }
    
    public void setFirstValue(T firstValue) {
        this.firtValue = firstValue;
    }
    public void setSecondValue(T secondValue) {
        this.secondValue = secondValue;
    }
}
```

NI-CKEL
Je peux maintenant réutiliser cette classe simplement dans mes 2 méthodes `getIdentity()` et `getMeasurements()`. Ça me fera un beau gain d'une classe entière. Et le pied, c'est que cette classe générique est réutilisable à l'infini !

```java

public BiValue<String> getIdentiy() {
    //Pour spécifier la valeur du type générique, on le place entre chevrons juste après le nom de la classe, comme ceci :
    return new BiValue<String>(name, surname);
}
public BiValue<Integer> getMeasurements() {
    return new BiValue<Integer>(size, weight);
    //Vous avez sûrement remarqué que je n'ai pas précisé `Integer` *(et non `<int>` car les types primitifs, à savoir `int`, `double`, `short`, `byte`, `long`. Il faut passer par leurs types `wrapper` : `Integer`, `Double`, `Short`, etc ...)* car le compilateur est assez intelligent pour `inférer`, soit `trouver tout seul`, le type générique à passer en paramètre à `BiValue`. Il peut le déduire des paramètres que nous passons au constructeur 
}
```

Et voilà, encore une mission remplie avec succès par **super-générique**. 
Bref, les types génériques sont des types pouvant varier d'une instance de classe à l'autre **mais qui restent constants au sein d'une même instance**.

---

Bon c'est pas tout ça mais corrigeons cela :

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {

}
```

---

Au fait, dernier interlude généricité : 
L'interface `Function`est un super exemple de généricité aussi. Son but, c'est de pouvoir contenir une méthode qui prend quelque chose en paramètre et qui renvoie quelque chose. 
Sauf qu'on ne connait pas ces fameux quelque chose. 
Les types sont donc génériques et il faut le préciser.

Voici le code de cette, si renommée, interface `Function` :
```java
public interface Function<T, R> {
    R apply(T t);
} 
```
Vous voyez qu'elle a besoin de 2 types génériques : un type quelconque (`<T>`) et un type de résultat (`<R>`).
Afin de comprendre son fonctionnement, je vous suggère de jeter un coup d'oeil à [cette fiche](https://github.com/readthedocs-fr/notions/tree/master/java/interfaces_fonctionnelles) sur les interfaces fonctionnelles dont notre chère et bien-aimée interface `Function` fait partie .

Mon type de retour est ici un objet de type `Function` et cette fonction prend en paramètre un nombre et renvoie un nombre. Ç'aurait pu être n'importe quoi d'autre, une fonction qui prend un `String`en paramètre et renvoie un `Double`etc...

---

Nickel.

L'étape suivante, c'est de renvoyer une fonction.

Pour cela nous allons utiliser la forme suivante :

`x -> y`

Elle signifie "Cette fonction prend une valeur x et renvoie une valeur y". Encore une fois, je vous invite à lire la fiche sur les [interfaces fonctionnelles en java](https://github.com/readthedocs-fr/notions/tree/master/java/interfaces_fonctionnelles) si cette syntaxe vous est inconnue.

Go :

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {
    Function<Integer, Integer> h = x -> 0;
}
```

Notre fonction `h` prend actuellement un nombre en paramètre et renvoie `0`.

Or ce n'est pas notre but.

Nous voulons faire en sorte que le paramètre de `h` passe à travers la moulinette de la fonction `g` puis à travers celle de `f` et qu'elle soit enfin renvoyée.

La classe `Function` nous met à disposition une méthode `apply` qui prend en paramètre une valeur (dont le type est spécifié par la classe générique, `Integer` dans notre cas soit un nombre).

Nous pourrions donc faire 

`g.apply(x)` 

et nous récupèrerions un nombre non ?

Faisons le :

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {
    Function<Integer, Integer> h = x -> g.apply(x);
}
```

Maintenant, `h` fait le même travail que `g`. 

Inutile donc 

Il nous faut encore passer le résultat de `g.apply(x)` dans la moulinette de `f`.

Qu'est ce qu'on attend ?

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {
    Function<Integer, Integer> h = x -> f.apply(g.apply(x));
}
```

Enfin !

Nous avons notre fonction composée `h` prête à l'emploi.

Suffit de terminer le travail et de rendre à César ce qui est à César (ou plutôt à notre méthode) :

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {
    Function<Integer, Integer> h = x -> f.apply(g.apply(x));
    return h;
}
```

Nous pouvons d'ailleurs sauter le passage en variable de `h`. Nous gagnons ainsi une ligne.

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {
    return x -> f.apply(g.apply(x));
}
```

---

**1ère étape ✅**

---

## Étape intermédiaire

Maintenant que vous commencez à comprendre le fonctionnement, nous allons passer à la vitesse supérieure (don't worry je suis là 👀).

Cette deuxième étape est définie par l'extension à l'illimitééé (🤩). 

Comme je vous l'avais suggéré, nous allons passer par une liste de fonctions.

Reprenons sans plus attendre la signature de la méthode précédente et adaptons là :

```java
public Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {

}
```

À ce stade ci, nous avons désormais 2 possibilités :

- Soit nous allons vers une partie simple et sans découverte en faisant une simple boucle sur la liste et en les appliquant les unes sur les autres (pas fun fun tout ça)
- Soit nous allons vers le monde merveilleux de la récursivité et on fait des choses grandioses 😍

(Évidemment, dans cette correction, vous n'avez pas le choix, c'est moi qui décide ce que j'écris et je décide de partir sur la deuxième option 👀)

*Néanmoins, comme dit au début, toute solution qui fonctionne est une solution correcte, il n'y a pas de solution meilleure que toutes les autres. Si vous avez choisi de partir sur les boucles, bien vu, c'est déjà nickel d'être arrivé jusqu'ici. Vous avez réussi l'exercice. Mais regardez quand même ce que ça donne sous une autre forme, ça ne peut être que bénéfique* 😇

### Interlude récursivité

Expliquons vite fait ce qu'est la récursivité pour ceux qui ne connaissent pas.

Quelque chose de récursif, c'est quelque chose qui fait tout le temps appel à lui même jusqu'à ce que la boucle d'appel se casse ou soit stoppée.

Ça peut être une liste, une méthode, une manière de parcourir un objet itérable, etc.

---

Nous allons donc construire une méthode qui va se rappeler à l'infini tant qu'on ne l'arrête pas. 

Pour l'arrêter, il faut une condition exutoire *(de sortie)*. Cette condition doit être le point à partir duquel la méthode ne peut plus se rappeler car soit cela causerait un bug soit car tout simplement ça ne sert plus à rien.

Ici, nous allons faire en sorte que la méthode se rappelle tant qu'il y a des fonctions à traiter dans la liste qu'on lui passe en paramètre.

Notre méthode va traiter une condition avec la suivante, une fois qu'elles auront été composées en une nouvelle fonction, celle ci se verra ajoutée à la fin de la liste afin qu'on puisse la récupérer.

Notre condition, c'est donc qu'il ne reste qu'une seule fonction dans la liste (notre fonction composée).

Si cette condition est atteinte, cela signifie que la seule méthode de la liste est la méthode à renvoyer.

```java
public Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {
    if(functions.size() == 1) {
        return functions.get(0);
    }
}
```

Tant qu'on est dans les conditions, pensons au cas où l'utilisateur fournit une liste vide. Notre méthode ne pouvons composer de fonction sans fonctions. Renvoyons simplement une fonction qui renvoie toujours 0.

```java
public Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {
    if(funcitons.size() == 1) {
        return functions.get(0);
    }
    
    if(functions.size() == 0) {
        return x -> x; //la forme x-> y
    }
}
```

Maintenant construisons le corps même de la fonction.

Il faut donc commencer par la fin puisque la première fonction de la liste est la dernière à être appliquée *`a(b(c(d(...(x)))))`.* Cela nous permettra également de récupérer facilement la dernière fonction composée créée vu qu'elle sera ajoutée en fin de liste et prendra la place des 2 dernières précédentes.

```java
public Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {
    if(funcitons.size() == 1) {
        return functions.get(0);
    }
    
    if(functions.size() == 0) {
        return x -> x; //la forme x-> y
    }
    
    //Nous récupérons et supprimons les 2 fonctions de la liste
    //La dernière fonction de la liste est celle 
    //qu'il faut appliquer en premier sur notre variable, 
    //on la nomme donc g. F est la seconde fonction.
    Function<Integer, Integer> g = functions.remove(functions.size() - 1);
    Function<Integer, Integer> f = functions.remove(functions.size() - 1);
    
    //Rien de nouveau sous le soleil, c'est l'étape 1
    Function<Integer, Integer> h = x -> f.apply(g.apply(x));

    functions.add(h);

    return compose(functions);
}
```

Et voilà, notre méthode `compose()` est terminée.

Normalement sur le milieu du code, vous devriez pouvoir vous en sortir vous même pour comprendre.

La dernière ligne, `return compose(functions);` , peut être plus coriace.

C'est justement là toute la magie de la récursivité.

Comme nous avons défini un point d'arrêt plus haut, nous pouvons rappeler notre méthode indéfiniment tant qu'elle ne valide pas la condition de stop.

Nous pouvons renvoyer notre propre méthode car comme vous le voyez, et c'est plutôt logique, les types de retour concordent. 
C'est normal vu que c'est la même méthode. 
Nous pouvons également lui passer l'ancienne liste maintenant modifiée en paramètre. 
L'appel n°2 de la méthode fera son travail (elle devra peut-être demander un 3ième appel de méthode et ainsi de suite) puis renverra la bonne valeur à la méthode de l'appel n°1 qui, elle, renverra la valeur à l'endroit duquel elle a été appelée pour la première fois.

## Final sprint

Arrivé ici, ce ne sera qu'une simple formalité pour vous qui avez compris comment fonctionnait le principe de récursivité.

Nous devons ici simplement *généraliser* notre méthode.

Par généraliser, j'entends évidemment *générifier*.

C'est à dire, rendre cette méthode applicable à tout type d'élément en appliquant ce qu'on appelle des paramètres génériques.

Nous allons donc définir 2 paramètres : l'entrée et la sortie. En anglais on dit `input` et `output`. Par convention, les noms de paramètre générique ne sont composés que d'une seule lettre. Ce sera donc `I` et `O`.

Premièrement, nous définissons nos paramètres pour la méthode :

---

Car oui, une classe mais également une méthode peuvent définir des types génériques. La classe les définira pour toutes les méthodes et variables qu'elle contient, tandis que les paramètres de la méthode générique resteront cantonnés à la région locale, soit le corps de la méthode. Une fois que la méthode a terminé de s'exécuter, ces paramètres génériques ne valent plus rien.

Pour définir des types génériques dans une méthode, on passe, comme pour les classes, les noms des différents paramètres entre chevrons (`<` `>`) juste après les `modifiers`de la méthode (`public`, `private`, `synchronized`, etc ...)

---

```java
public <I, O> Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {
    if(funcitons.size() == 1) {
        return functions.get(0);
    }
    
    if(functions.size() == 0) {
        return x -> x; 
    }
    
    Function<Integer, Integer> g = functions.remove(functions.size() - 1);
    Function<Integer, Integer> f = functions.remove(functions.size() - 1);
    
    Function<Integer, Integer> h = x -> f.apply(g.apply(x));

    functions.add(h);

    return compose(functions);
}
```

Dans chacune de nos fonctions, nous avons déclaré que les types d'entrée et de sortie étaient tous les deux des nombres. Remplaçons ça :

```java
public <I, O> Function<I, O compose(List<Function<I, O>> functions) {
    if(funcitons.size() == 1) {
        return functions.get(0);
    }
    
    if(functions.size() == 0) {
        return x -> x; 
    }
    
    Function<I, O> g = functions.remove(functions.size() - 1);
    Function<I, O> f = functions.remove(functions.size() - 1);
    
    Function<I, O> h = x -> f.apply(g.apply(x));

    functions.add(h);

    return compose(functions);
}
```

Impeccable.

Désormais, notre méthode peut composer un nombre illimité de fonction en une seule fonction tant qu'elle gardent le même type de paramètre et de retour.

## Conclusion

Cette *correction* touche à sa fin.

L'exercice n'était pas simple, félicitation à ceux qui l'ont réussi, un grand bravo à tout ceux qui ont essayé de le faire *(le plus important n'est pas de trouver la solution mais de réfléchir)*. 

J'espère que ça vous a bien fait réfléchir, que ça vous a plu et que surtout vous avez appris des choses 😛 

On se retrouve bientôt pour quelque chose de tout aussi croustillant ;)

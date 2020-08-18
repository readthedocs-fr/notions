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

On va commencer par appliquer la première étape et faire une **méthode** qui peut **composer 2 fonctions** **en** **une seule**.

Il faut donc que notre méthode prenne **2 fonctions en paramètre**. Il faut également que notre méthode **renvoie cette fonction composée.**

Pour écrire ce code, nous allons nous servir du package `java.util.function` et des classes qui s'y trouvent, notamment la classe `Function`.

```java
public Function compose(Function f, Function g) {

}
```

Bon, jusque là, pas trop de problème, nous avons la signature de notre méthode. Il reste cependant un léger souci, j'ai déclaré mes types de retour et de paramètre en `rawtypes`. `Rawtype` traduisible par `sans type` en français signifie que je n'ai pas assigné de type à certaines classes génériques. 

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

Ici, j'ai défini 3 variables de types, ce sont mes paramètres génériques. 
Je peux à présent faire ceci dans mon code : 

```java
public class MyGenericClass<S, T, U> {
    S firstVariable;

    public MyGenericClass(S value){
        this.firstVariable = value;
    }
}
```

En effet, je suis sûr que `firstVariable` et `value` sont de même type : `S`. Je peux donc faire ce que je veux avec. 

Des excellents exemples d'utilisation de la généricité, sont les listes, toutes les classes qui implémentent l'interface `List`.

Je peux également avoir ceci :

```java
public class MyGenericClass<S, T, U> {
    S firstVariable;

    public MyGenericClass(S value){
        this.firstVariable = value;
    }

    public U doSomething(S sValue, U uValue) {
        System.out.println(sValue + uValue);
        return uValue;
    }
}
```

Ici, lors de l'utilisation de cette méthode, je suis sûr de récupérer en retour un objet du même type que celui passé en second paramètre.

Bref, les types génériques, ce sont des types variables.

---

Bon c'est pas tout ça mais corrigeons cela :

```java
public Function<Integer, Integer> compose(Function<Integer, Integer> f, Function<Integer, Integer> g) {

}
```

---

Au fait, dernier interlude généricité : 
La classe `Function`est un super exemple de généricité aussi. Son but, c'est de pouvoir contenir une méthode qui prend quelque chose en paramètre et qui renvoie quelque chose. 
Sauf qu'on ne connait pas ces fameux quelque chose. 
Les types sont donc génériques et il faut le préciser.

Mon type de retour est ici un objet de type `Function` et cette fonction prend en paramètre un nombre et renvoie un nombre. Ç'aurait pu être n'importe quoi d'autre, une fonction qui prend un `String`en paramètre et renvoie un `Double`etc...

---

Nickel.

L'étape suivante, c'est de renvoyer une fonction.

Pour cela nous allons utiliser la forme suivante :

`x -> y`

Elle signifie "Cette fonction prend une valeur x et renvoie une valeur y"

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

*Néanmoins, comme dit au début, toute solution qui fonctionne est une solution correcte, il n'y a pas de solution meilleure que toutes les autres Si vous avez choisi de partir sur les boucles, bien vu, c'est déjà nickel d'être arrivé jusqu'ici. Vous avez réussi l'exercice. Mais regardez quand même ce que ça donne sous une autre forme, ça ne peut être que bénéfique* 😇

### Interlude récursivité

Expliquons vite fait ce qu'est la récursivité pour ceux qui ne connaissent pas.

Quelque chose de récursif, c'est quelque chose qui fait tout le temps appel à lui même jusqu'à ce que la boucle d'appel se casse ou soit stoppée**.**

Ça peut être une liste, une méthode, une manière de parcourir un objet itérable etc

---

Nous allons donc construire une méthode qui va se rappeler à l'infini tant qu'on ne l'arrête pas. 

Pour l'arrêter, il faut une condition exutoire *(de sortie)*. Cette condition doit être le point à partir duquel la méthode ne peut plus se rappeler car soit cela causerait un bug soit car tout simplement ça ne sert plus à rien.

Ici, nous allons faire en sorte que la méthode se rappelle tant qu'il y a des fonctions à traiter dans la liste qu'on lui passe en paramètre.

Notre méthode va traiter une condition avec la suivante, une fois qu'elles auront été composées en une nouvelle fonction, celle ci se verra ajoutée à la fin de la liste afin qu'on puisse la récupérer.

Notre condition, c'est donc qu'il ne reste qu'une seule fonction dans la liste (notre fonction composée).

Si cette condition est atteinte, cela signifie que la seule méthode de la liste est la méthode à renvoyer.

```java
public Function<Integer, Integer> compose(List<Function<Integer, Integer>> functions) {
    if(funcitons.size() == 1) {
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
        return x -> 0; //la forme x-> y
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
        return x -> 0; //la forme x-> y
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
        return x -> 0; 
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
        return x -> 0; 
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

Enjoy !

# Fonction composée

Bonjour et bienvenue, installez vous confortablement... voilààà parfait. Commençons !

## Prérequis

- Connaître les bases de Java
- Connaître la notion de fonction en mathématique

## Notions abordées

- Récursivité
- Généricité

## Mathématiques

La fonction composée est, en mathématique, un assemblage de plusieurs fonctions. 

C'est à dire que la variable *(x)* sera affecté par la 1ère fonction, ensuite, ce n'est plus la variable qui sera affecté par la seconde fonction mais bien **le résultat** de la variable par la 1ère fonction
Pour deux fonctions `f` et `g`, elle se note `f o g` et se lit `f rond g`. 

Pour bien comprendre ce que c'est, voyons l'exemple suivant :

Soit les fonctions :

`f(x) = 3x`
`g(x) = x + 2`

Admettons que `x` vaille `3`,

- Si nous passons `x` dans la fonction `f`, nous obtenons `9` (`f(3) = 3 * 3 = 9`).
- Si nous passons `x` dans la fonction `g`, nous obtenons `5` (`g(3) = 3 + 2 = 5`).

Maintenant, formons une fonction composée de la fonction `f` et de la fonction `g`. Appelons la `h` :

`h(x) = f(g(x))`

À présent, décortiquons. 

La première fonction ici est la fonction `g(x)` tandis que la seconde est la fonction `f(x)`.

La variable **x** passera donc d'abord dans la fonction `g` et le résultat de `x` par la fonction `g` (`g(x)`) passera ensuite dans la fonction `f`. Nous obtiendrons ainsi notre résultat final.
Si `f(x) = 3x` et que `g(x) = x + 2`, nous pouvons dire que la variable de la fonction `f` (le `x`) est représentée par la fonction `g`. 
En effet, lorsque nous écrivons `f(g(x))`, nous pouvons prononcer `f de g de x`, si nous devions donner le nom de la fonction `f` ou celui de la fonction `g` cela donnerait `f de x` ou `g de x`. 
C'est bien la preuve que la fonction `g` prend la place du `x` dans la fonction `f`.

Nous pourrions donc écrire

`h(x) = f(x + 2)`

Si vous n'êtes pas d'accord, essayez de relire le paragraphe au dessus.
Sinon, continuons.

Attention, ici la situation se corse visuellement mais si vous avez compris la partie précédente, ce sera tout aussi facile. 
Si j'écris que

`h(x) = 3*(x + 2)`

Vous comprenez ?

Si oui, continuez plus bas, sinon lisez 😉

Comme vous l'avez remarqué, la fonction `f` a *disparue*.
Cependant, si vous regardez de plus près, vous remarquerez qu'on la retrouve dans l'expression `3.(x + 2)`. En effet, ce n'est rien d'autre que sa forme `3x` modifiée. 
Comme nous l'avons dit plus haut, la fonction `g` était le `x` de la fonction `f`.                                    Je l'ai donc simplement remplacé dans l'expression.

Maintenant, essayons de voir ce que donnerait cette fonction `h(x)`.

`h(3) = 3*(3 + 2) = 3*5 = 15`

Et oui, la fonction `h` n'a plus rien à voir avec les fonctions de bases.

La fonction `h` est donc la composée de `f` et de `g`.

> Vous pouvez d'ailleurs essayer de faire la composée dans l'autre sens afin de vous entraîner.
Essayez de trouver le résultat de h(3) si h(x) = g(f(x)) ;)

## Sous forme de code maintenant

Après avoir compris ce qu'était la fonction composée en mathématiques, voyons voir sous forme de code (je vous préviens, vous allez être déçus, c'est 100x plus facile donc 200x moins intéressant mais lisez quand même 😁)

Imaginons nos 2 fonctions `f` et `g` :

```java
    public int f(int x) {
        return 3*x;
    }

    public int g(int x) {
        return x + 2;
    }
```

Regardons ce que donnerait le résultat de `f o g` :

```java
    public int f(int x) {
        return 3*x;
    }
     
    public int g(int x) {
        return x + 2;
    }
    
    public static void main(String... args) {
        System.out.println(f(g(3)));
    }
```

À votre avis, qu'afficherait ce programme ?

> 15

*Bon oke, c'était facile vous aviez la réponse au dessus 🙄*

Vous voyez bien que c'est 400x moins intéressant (j'avais dit 200 ? Oups... 😅).

## Au boulot !

Après vous avoir expliqué ce qu'était une fonction composée, à vous de bosser !

Je veux que vous écriviez une fonction capable de construire la fonction composée de plusieurs fonctions.

1. Commencez par la composée de 2 fonctions seulement.
2. Une fois ceci fait, essayez d'étendre votre fonction à une fonction capable de prendre une infinité de fonction en paramètre et d'en sortir la fonction composée.                                            *Je vous suggère de passer par une liste de fonctions en paramètre pour cette deuxième étape.*
3. Enfin généralisez votre fonction à n'importe quel type de valeur.

---

Voici une liste de fonctions que vous pouvez utiliser pour vos tests :

> x = 2

*À côté de chaque fonction se trouve la valeur que x atteint à cet endroit*

- `a(x) = 3x` - `6`
- `b(x) = x - 7` - `-1`
- `c(x) = 2x + 4` - `2`
- `d(x) = x / 2` - `1`
- `e(x) = arcsin(x)` - `0`
- `f(x) = cos(x)` - `1`
- `g(x) = log(x)` - `0`
- `h(x) = 100*cos(x)` - `100`
- `i(x) = sqrt(x)` - `10`
- `j(x) = x*5/2` - `25`

Nous arrivons ainsi à un beau nombre, 25.
C'est un carré parfait lui-même formé de la somme des 2 carrés parfaits le précédant.
C'est un de ceux que j'aime bien.

## Conclusion

Finalement, vous avez appris ce qu'était cette fameuse fonction dite composée. Vous savez la créer et vous connaissez même la notion mathématique *(si ça se trouve, je vous ai donné une avance sur votre cours :eyes:)*. 
Quoi qu'il en soit, cet exercice touche à sa fin.
J'espère qu'il vous a plu (en tout cas, moi je me suis amusé à expliquer cette notion de composée) et puis on se retrouve dans d'autres exercices ;p

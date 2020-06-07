# Listes à types indéfinis

**Note importante:** dans ce cours, nous prendrons exclusivement comme exemple `java.util.List`, même si les concepts expliqués s'appliquent en réalité à toutes les différentes implémentations de `java.util.Collection`.

### Prérequis

- Une connaissance suffisante en généricité (`class level` et `method level`)
- Etre à l'aise, au moins conceptuellement, avec l'interface `java.util.List`
- Une relativement bonne connaissance en programmation orientée objet, et être confortable avec la notion d'héritage

## Différences

Au premier abord, il est souvent difficile de voir la différence entre `List<A>` et `List<? extends A>`. Quant à `List<? super A>`, pour beaucoup de néophytes, ce n'est rien d'autre qu'un truc inconnu qu'on n'utilise jamais parce qu'on ne sait pas vraiment à quoi ça sert. Cette section permettra de lever le mystère sur ces trois formes, afin de pouvoir enchaîner sur leurs utilités.
***

#### `List<? extends A>` vs `List<A>`

Quand je demande la différence à un débutant entre `List<A>` et `List<? extends A>`, j'obtiens presque tout le temps une réponse du style :
> `List<A>` est une liste d'objets de type `A`, alors que `List<? extends A>` est une liste d'objets de type `A` **et** de ses sous classes.

A cela, je réponds toujours : donc cela veut dire que si `B extends A`, je ne peux pas mettre de B dans une `List<A>` ? Pourtant, un objet de type `B` est aussi un objet de type `A` non ?

Et après, c'est la confusion.
> N'y a-t-il alors aucune différence entre les deux ?

Oh que si. En fait, le piège est que quand on cherche à comprendre ce que veut dire `List<? extends A>`, on le lit simplement de gauche à droite: "Une liste de choses qui héritent de A". Et l'erreur est là. Il faut en fait se poser la question : quel est le **type** de cette liste ? Comme on le sait, le type de la liste est indiqué entre les chevrons (`<>`). Donc, le type de cette liste est... quelque chose qui hérite de `A` (ou `A` lui-même). Mais a-t-on plus de détails ? Pas vraiment. On a un indice, mais on ne connaît pas le réel type de la liste. 

Arrivé à ce stade, j'aime poser la question suivante :
> Si je te donne une `List<? extends Carnivore>` par exemple, que peux-tu ajouter dedans ?

Et si malgré la petite explication ci-dessus la différence entre les deux formes n'est toujours pas claire, la réponse est souvent quelque chose comme :
> Un objet de type `Loup` par exemple ? (En partant du principe que `Loup extends Carnivore`) 

Vous l'avez deviné, cette réponse est fausse. Mais pourquoi ? Revenons au type de la liste en question. Comme mentionné au-dessus, nous n'avons qu'une seule information sur le type de la liste : c'est quelque chose qui hérite de `Carnivore` (ou `Carnivore` lui-même). Une `List<? extends Carnivore>` pourrait donc **potentiellement** être une `List<Carnivore>`. Dans ce cas, ajouter un objet de type `Loup` dedans est parfaitement en règle. Où est le problème alors ? Et bien, une `List<? extends Carnivore>` pourrait très bien aussi être une `List<Chat>`, puisque `Chat extends Carnivore`. De nouveau ce n'est qu'une **possibilité**, on **ne connaît pas le type de notre liste !** Notre seule information est que ce type hérite de Carnivore, ou est Carnivore lui-même. Et dans le cas où il s'agirait d'une `List<Chat>`, impossible d'ajouter un objet de type `Loup` ! Donc, on ne peut pas ajouter de loup dans cette liste, puisqu'il y a des chances que la liste ne puisse pas en contenir.
> Alors on pourrait ajouter un objet de type `Carnivore` ?

Toujours pas. Si je reprends exactement le même exemple qu'au-dessus, dans le cas où notre liste serait une `List<Chat>`, on ne peut absolument pas ajouter un `Carnivore`, puisque `Chat extends Carnivore`, pas l'inverse. En fait, on ne peut **strictement rien** ajouter dans une telle liste.
> Et à quoi peut bien servir une liste dans laquelle on ne peut rien ajouter ? Ce n'est rien d'autre qu'une liste vide...

Ah non ! Je vais vous donner un exemple.

```java
interface Useless {}

class MyClass implements Useless {
    
    void test1() { System.out.println("This is a test!"); }
    void test2() { System.out.println("Here's another one!); }
}
```

Prenons maintenant un exemple de code comme celui-ci :

```java
Useless foo = new MyClass();
```

A partir de cet objet `foo`, vous ne pourrez rien faire, ni appeler `test1`, ni `test2`. Est ce que cela veut dire que l'objet de possède pas ces méthodes ? Absolument pas. Notre objet est simplement **déclaré** comme un objet de type `Useless`, mais son réel type est différent. Pour nos listes, c'est le même principe. Une `List<? extends A>` ne peut pas être modifiée, mais cela ne veut pas dire qu'elle n'a jamais été modifiée. Imaginons une `List<B>` (toujours avec `B extends A`). A sa création, elle a été déclarée comme une `List<B>`, elle a donc pu être modifiée. Elle pourrait par exemple contenir 2 objets de type `B` et un objet de type `C` (avec `C extends B`). Et imaginons maintenant une méthode qui demanderait en paramètre une `List<? extends A>`. Notre `List<B>` est une liste tout à fait éligible pour ce paramètre, puisqu'il s'agit bien d'une liste dont le type hérite de `A` ! Pourtant, elle est loin d'être vide. Il ne faut pas confondre le type **déclaré** d'un objet et son **réel** type (son type le plus précis).

Voyons voir une autre question. Si on récupère un élément d'une `List<? extends A>`, quel sera le type **déclaré** de cet élément ? En d'autres termes, quel est le type le plus **précis** qu'on peut être garanti d'obtenir ? Et bien, si le type de notre liste était **complètement** inconnu, la réponse aurait été `Object`, puisqu'il s'agit du type le plus général possible : en Java, toute classe est une sous classe de la classe `Object`. Sauf qu'ici, on a quand même une information à propos du type de notre liste. On sait qu'il s'agit d'une liste de type `A`, ou d'une de ses sous-classes. En d'autres termes, le type le plus général possible de notre liste est `A`. Donc très logiquement, le type le plus précis qu'on peut obtenir de manière garantie en récupérant un élément de notre liste est `A`.
***

#### `List<? super A>` vs `List<A>`

Passons maintenant à `List<? super A>`. Puisque c'est exactement le même principe, j'irai droit à la définition. Il s'agit d'une liste dont le type est `A`, ou une classe **parente** de A. Par exemple, une `List<? super Loup>` pourrait être une `List<Loup>`, une `List<Animal>` voire même une `List<Object>`. Posons nous directement les questions suivantes :
- Que peut-on ajouter dans une telle liste ?
- Quel type d'élément s'attend-on à recevoir lorsqu'on récupère un élément de cette liste ?

Pour répondre à la première question, une approche possible est de se demander: "quel type d'objet rentre dans **toutes** les possibilités de types pour notre liste sans exception ?". Si on reprend notre exemple du dessus, on peut ajouter un loup dans une `List<Loup>`, mais aussi dans une `List<Animal>`, et également dans une `List<Object>`. Donc on peut ajouter un objet de type `Loup` dans une `List<? super Loup>` (ce qui est logique, puisque le type le plus "précis" et donc restreignant possible pour notre liste est une `Loup`, on peut donc évidemment ajouter un loup dans une liste de loups). Voyons voir les classes parentes de `Loup`. Pourrait-on ajouter un `Animal` dans une telle liste ? La réponse est non, puisque cette liste pourrait potentiellement être une `List<Loup>`, ce qui n'irait pas. Et les sous-classes de `Loup` ? La réponse est instantanée: selon les lois de l'héritage, si un objet est de type `X`, et que `X extends Loup`, alors l'objet est également de type `Loup`. On peut donc ajouter dans une `List<? super A>` n'importe quel objet de type `A`, ou d'un type héritant de `A`.

Pour répondre à la deuxième question, on cherche à savoir quel est le type le plus précis possible que l'on peut avoir la garantie d'obtenir. Il faut donc se demander : "quel est le **pire** scénario possible, celui dans lequel notre liste serait du type le plus **général**, donc le plus restreignant possible ?" La réponse est simple, il s'agit du cas où notre liste serait une `List<Object>`. On ne récupère donc que des objets de type `Object` si l'on possède une `List<? super A>`.

### Pour récapituler 
- `List<A>`
  - On peut y ajouter des objets de type `A`, ou d'un type héritant de `A`
  - On récupère des éléments de type `A`
- `List<? extends A>`
  - On ne peut rien y ajouter
  - On récupère des éléments de type `A`
- `List<? super A>`
  - On peut y ajouter des objets de type `A`, ou d'un type héritant de `A`
  - On récupère des éléments de type `Object`

## Utilisation

Maintenant qu'on a la théorie, il est intéressant de s'intéresser aux différentes utilités de ces différentes déclarations de liste. L'une d'entre elles est l'abstraction. Si ce terme vous est inconnu, je vous invite à mettre en pause la lecture de ce paragraphe et d'aller jeter un coup d'oeil à [cette fiche](../../../../poo/abstraction).

#### `List<? extends A>`

Imaginons une méthode comme ceci :

```java
void feedAll(List<Eater> eaters) {
    for(Eater eater : eaters) {
        eater.feed();
    }
}
```

Avec `Eater` une interface qui ressemblerait à :

```java
interface Eater {
    void feed();
}
```

Si vous avez suivi le cours sur l'abstraction, vous comprenez qu'on a créé une interface `Eater` afin de ne donner qu'accès à `feed` à notre méthode `feedAll`, et aussi afin que cette méthode puisse servir pour n'importe quoi qui peut manger, pas uniquement un humain par exemple. Enfin... est-ce vraiment le cas ?
> Absolument. Une `List<Eater>` peut contenir des humains, des animaux, n'importe quoi qui mange. Donc il n'y a aucun souci.

C'est vrai. Mais qu'en est-il d'une `List<Human>` ? Cela devrait aussi marcher, puisque tout humain possède la méthode `feed`. Pourtant... il ne s'agit pas d'une valeur valide pour le paramètre de cette méthode.
> Pourquoi diable ne peut-on pas donner une `List<Human>` si on demande une `List<Eater>`, sachant que `Human extends Eater` ?

Et bien... il suffit de se rappeler pourquoi on ne peut rien ajouter dans une `List<? extends A>` ! Imaginez ce code :
```java
void addEater(List<Eater> myList) {
    myList.add(new Plant()); // parce que oui, les plantes ça mange
}
```
Essayez maintenant d'imaginer ce qui se passerait si `List<Human>` était une valeur valide pour le paramètre `List<Eater>`... On se retrouverait avec une liste d'humains qui contient une plante, ce qui n'est évidemment pas possible. Voilà qui est étrangement familier au problème du début du cours avec `List<? extends A>` non ?

Je suis sûr que vous l'avez compris, la solution est donc de demander une `List<? extends Eater>` au lieu d'une `List<Eater>`. Dans ce cas-là, une `List<Human>` sera parfaitement valide, et une `List<Eater>` aussi d'ailleurs. Et en conséquence, la méthode `feedAll` n'aura plus le pouvoir d'ajouter des éléments dans la liste, ce qui est une excellente chose : elle n'était pas censée pouvoir le faire.

#### `List<? super A>`

Imaginons une méthode qui demande en paramètre une liste d'humains, et qui ajoute 3 humains présents sur la planète dans cette liste. Quelque chose comme ceci :

```java
void addThreeRandomHumans(List<Humain> humans) {
    Humain[] randomHumans = TheEarth.getSomeHumans(3); // Inutile de montrer l'implémentation de getSomeHumans
    for(Humain randomHuman : randomHumans) {
        humans.add(randomHuman);
    }
}
```

Parfait, notre méthode fait exactement ce qu'on veut. Mais comme d'habitude, quelque chose ne va pas. Deux choses en fait.
- Cette méthode a le pouvoir d'espionner notre liste, c'est à dire qu'elle pourrait se permettre de regarder chaque élément et de l'analyser. Inutile de préciser qu'elle n'est pas censée pouvoir le faire
- De manière similaire, notre méthode exige une `List<Humain>`. Logiquement, on ne devrait pas pouvoir donner de `List<Adult>` comme paramètre (en partant du principe que `Adult extends Human`), puisqu'on ajoute des humains dans la liste et que `Humain` est plus général que `Adult`. Par contre, pourquoi ne pourrait-on pas fournir une `List<Living>` à notre méthode ? Il serait tout à fait valide de mixer les humains et les animaux (hum) dans une liste, pourtant cette méthode ne nous le permet pas.

Si la solution à ce problème vous paraît un peu répétitive, c'est bon signe : vous avez compris le cours. Pour ceux qui auraient encore un peu de mal, il suffit de demander une `List<? super Humain>` en paramètre pour régler le problème :
- On peut désormais fournir une `List<Living>`, puisqu'il s'agit bien d'une `List<? super Human>`
- Notre méthode n'a plus le pouvoir d'espionner notre liste : elle ne peut récupérer rien d'autre que des objets de type `Object` de cette liste

## Conclusion

Cette petite utilisation de `List<? extends A>` au lieu de `List<A>` nous a permis de faire de l'abstraction très efficace :
- On a une facilité d'accès, puisque `feedAll` accepte désormais toute liste dont le type hérite de `Eater` (ou est `Eater`)
- On a une restriction d'accès, puisque `feedAll` n'a plus le pouvoir d'ajouter des éléments dans la liste qui lui est fournie.

D'une manière un peu différente, `List<? super A>` au lieu de `List<A>` nous a aussi permis de faire de l'abstraction :
- On a une facilité d'accès, puisque `addThreeRandomHumans` accepte désormais toute liste dont le type est `Human` ou une de ses classes parentes
- On a une restriction d'accès, puisque `addThreeRandomHumans` n'a plus le pouvoir de récupérer des éléments de type `Human` depuis la liste qui lui est fournie.

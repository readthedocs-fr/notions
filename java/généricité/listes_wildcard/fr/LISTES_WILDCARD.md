# Listes à types indéfinis

### Prérequis

- Une connaissance suffisante en généricité, au moins en `class level`
- Etre à l'aise, au moins conceptuellement, avec l'interface `java.util.List`
- Une relativement bonne connaissance en programmation orientée objet, et être confortable avec la notion d'héritage

### Différences

A première vue, il est souvent difficile de voir la différence entre `List<A>` et `List<? extends A>`. Et `List<? super A>`, c'est un truc inconnu qu'on utilise jamais parce qu'on sait pas vraiment à quoi ça sert. 

Quand je demande la différence à un débutant entre `List<A>` et `List<? extends A>`, j'obtiens presque tout le temps une réponse du style :
> `List<A>` est une liste d'objets de type `A`, alors que `List<? extends A>` est une liste d'objets de type A et de ses sous classes.

A cela, je réponds toujours :
> *Cela veut dire que si `B extends A`, je ne peux pas mettre de B dans une `List<A>` ? Pourtant, un objet de type `B` est aussi un objet de type `A` non ?*

Et après, c'est la confusion. N'y a-t-il alors aucune différence entre les deux ? En fait, le piège est que quand on cherche à comprendre ce que veut dire `List<? extends A>`, on le lit simplement de gauche à droite: "Une liste de choses qui héritent de A". Et l'erreur est là. Il faut en fait se poser la question directement : quel est le **type** de cette liste ? Comme on le sait, le type de la liste est indiqué entre les chevrons (`<>`). Donc, le type de la liste est... quelque chose qui hérite de `A`. Mais a-t-on plus de détails ? Pas vraiment. Arrivé à ce stade, j'aime poser la question suivante :
> Si je te donne une `List<? extends Carnivore>`, que peux-tu ajouter dedans ?

Et si la différence n'est toujours pas claire, la réponse est souvent quelque chose comme :
> Un objet de type `Loup` par exemple ?

Vous l'avez deviné, cette réponse est fausse. Mais pourquoi ? Revenons au type de la liste en question. Comme mentionné au-dessus, nous n'avons qu'une seule information sur le type de la liste : c'est quelque chose qui hérite de `Carnivore` (ou `Carnivore` lui-même). Une `List<? extends Carnivore>` pourrait donc **potentiellement** être une `List<Carnivore>`. Dans ce cas, ajouter un objet de type `Loup` dedans est parfaitement en règle. Où est le problème alors ? Et bien, cette `List<? extends Carnivore>` pourrait très bien être une `List<Chat>`, puisque `Chat extends Carnivore`. De nouveau ce n'est qu'une **possibilité**, on **ne connaît pas le type de notre liste !** Notre seule information est que ce type hérite de Carnivore, ou est Carnivore lui-même. Et dans le cas où il s'agirait d'une `List<Chat>`, impossible d'ajouter un objet de type `Loup` ! Donc, on ne peut pas ajouter de loup dans cette liste, puisqu'on n'est pas sûr qu'elle puisse en contenir.
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

A partir de cet objet `foo`, vous ne pourrez rien faire, ni appeler `test1`, ni `test2`. Est ce que cela veut dire que l'objet de possède pas ces méthodes ? Absolument pas. Notre objet est simplement **déclaré** comme un objet de type `Useless`, mais son réel type est différent. Pour nos listes, c'est le même principe. Une `List<? extends A>` ne peut pas être modifiée, mais cela ne veut pas dire qu'elle n'a jamais été modifiée. Imaginons qu'il s'agisse d'une `List<B>` (toujours avec `B extends A`). A sa création, elle a été déclarée comme une `List<B>`, elle a donc pu être modifiée ! Elle pourrait contenir 2 objets de type `B` et un objet de type `C` (avec `C extends B`) par exemple. Et imaginons maintenant une méthode qui demanderait en paramètre une `List<? extends A>`. Notre `List<B>` est une liste tout à fait éligible pour ce paramètre, il s'agit bien d'une liste dont le type hérite de `A`.

Voyons voir une autre question. Si on récupère un élément d'une `List<? extends A>`, quel sera le type **déclaré** de cet élément ? En d'autres termes, quel est le type le plus **précis** qu'on peut être garanti d'obtenir ? Et bien, si le type de notre liste était **complètement** inconnu, la réponse aurait été `Object`, puisqu'il s'agit du type le plus général possible : en Java, toute classe est une sous classe de la classe `Object`. Sauf qu'ici, on a quand même une information à propos du type de notre liste. On sait qu'il s'agit d'une liste de type `A`, ou d'une de ses sous-classes. En d'autres termes, le type le plus général possible de notre liste est `A`. Donc très logiquement, le type le plus précis qu'on peut obtenir de manière garantie en récupérant un élément de notre liste est `A`.

Passons maintenant à `List<? super A>`. Puisque c'est exactement le même principe, j'irai droit à la définition. Il s'agit d'une liste dont le type est `A`, ou une classe **parente** de A. Par exemple, une `List<? super Loup>` pourrait être une `List<Loup>`, une `List<Animal>` voire même une `List<Object>`. Posons nous directement les questions suivantes :
- Que peut-on ajouter dans une telle liste ?
- Quel type d'élément s'attend-on à recevoir lorsqu'on récupère un élément de cette liste ?

Pour répondre à la première question, une approche est de se demander: "quel type d'objet rentre **toutes** les possibilités de types pour notre liste sans exception ?". Si on reprend notre exemple du dessus, on peut ajouter un loup dans une `List<Loup>`, mais aussi dans une `List<Animal>`, et également dans une `List<Object>`. Donc on peut ajouter un objet de type `Loup` (ce qui est logique, puisque le type le plus "précis" possible pour notre liste est une `Loup`, on peut donc évidemment ajouter un loup dans une liste de loups). Voyons voir les classes parentes de `Loup`. Pourrait-on ajouter un `Animal` dans une telle liste ? La réponse est non, puisque cette liste pourrait potentiellement être une `List<Loup>`, ce qui n'irait pas. Et les sous-classes de `Loup` ? La réponse est instantannée: selon les lois de l'héritage, si un objet est de type `X`, et que `X extends Loup`, alors l'objet est également de type `Loup`. On peut donc ajouter dans une `List<? super A>` n'importe quel objet de type `A`, ou d'un type héritant de `A`.

Pour répondre à la deuxième question, on cherche à savoir quel est le type le plus précis possible que l'on peut avoir la garantie d'obtenir. Il faut donc se demander : "quel est le **pire** scénario possible, celui dans lequel notre liste serait du type le plus **général** possible ?" La réponse est simple, il s'agit du cas où notre liste serait une `List<Object>`. On ne récupère donc que des objets de type `Object` si l'on possède une `List<? super A>`

## Pour récapituler 
- `List<A>`
  - On peut y ajouter des objets de type `A`, ou d'un type héritant de `A`
  - On récupère des éléments de type `A`
- `List<? extends A>
  - On ne peut rien y ajouter
  - On récupère des éléments de type `A`
- `List<? super A>
  - On peut y ajouter des objets de type `A`, ou d'un type héritant de `A`
  - On récupère des éléments de type `Object`

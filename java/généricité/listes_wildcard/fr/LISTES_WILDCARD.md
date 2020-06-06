# Listes à types indéfinis

### Prérequis

- Une connaissance suffisante en généricité, au moins en `class level genericity`
- Etre à l'aise, au moins conceptuellement, avec l'interface `java.util.List`
- Une relativement bonne connaissance en programmation orientée objet

### Différences

A première vue, il est souvent difficile de voir la différence entre `List<A>` et `List<? extends A>`. Et `List<? super A>`, c'est un truc inconnu qu'on utilise jamais parce qu'on sait pas vraiment à quoi ça sert. 

Quand je demande la différence à un débutant entre `List<A>` et `List<? extends A>`, j'obtiens presque tout le temps une réponse du style :
> `List<A>` est une liste d'objets de type `A`, alors que `List<? extends A>` est une liste d'objets de type A et de ses sous classes.

A cela, je réponds toujours :
> Cela veut dire que si `B extends A`, je ne peux pas mettre de B dans une `List<A>` ? Pourtant, un objet de type `B` est aussi un objet de type `A` non ?

Et après, c'est la confusion. N'y a-t-il alors aucune différence entre les deux ? En fait, le piège est que quand cherche à comprendre ce que veut dire `List<? extends A>`, on le lit simplement de gauche à droite: "Une liste de choses qui héritent de A". Et là est l'erreur. Il faut en fait se poser la question directement : quel est le **type** de cette liste ? Comme on le sait, le type de la liste est indiqué entre les chevrons (`<>`). Donc, le type de la liste est... quelque chose qui hérite de `A`. Mais a-t-on plus de détails ? Pas vraiment. Arrivé à ce stade, j'aime poser la question suivante :
> Si je te donne une `List<? extends Carnivore>`, que peux-tu ajouter dedans ?

Et si la différence n'est toujours pas claire, la réponse est souvent du type :
> Un objet de type `Loup` par exemple ?

Vous l'avez deviné, cette réponse est fausse. Mais pourquoi ? Revenons au type de la liste en question. Comme mentionné au-dessus, nous n'avons qu'une seule information sur le type de la liste : c'est quelque chose qui hérite de `Carnivore` (ou `Carnivore` lui-même). Une `List<? extends Carnivore>` pourrait donc **potentiellement** être une `List<Carnivore>`. Dans ce cas, ajouter un objet de type `Loup` dedans est parfaitement en règle. Où est le problème alors ? Et bien, cette `List<? extends Carnivore>` pourrait très bien être une `List<Chat>`, puisque `Chat extends Carnivore`. De nouveau ce n'est qu'une **possibilité**, on **ne connaît pas le type de notre liste !** Notre seule information est que ce type hérite de Carnivore, ou est Carnivore lui-même. Et dans le cas où il s'agirait d'une `List<Chat>`, impossible d'ajouter un objet de type `Loup` ! Donc, on ne peut pas ajouter de loup dans cette liste, puisqu'on n'est pas sûr qu'elle puisse en contenir.
> Alors on pourrait ajouter un objet de type `Carnivore` ?

Toujours pas. Si je reprends exactement le même exemple qu'au-dessus, dans le cas où notre liste serait une `List<Chat>`, on ne peut absolument pas ajouter un `Carnivore`, puisque `Chat extends Carnivore`, pas l'inverse. En fait, on ne peut **strictement rien** ajouter dans une telle liste.
> Et à quoi peut bien servir une liste dans laquelle on ne peut rien ajouter ? Ce n'est rien d'autre qu'une liste vide...

Ah non ! Je vais vous donner un exemple

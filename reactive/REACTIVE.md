Donc commençons par la définition imbitable de wikipédia :
En français
> En informatique, la programmation réactive est un paradigme de programmation visant à conserver une cohérence d'ensemble en propageant les modifications d'une source réactive (modification d'une variable, entrée utilisateur, etc.) aux éléments dépendants de cette source.
Et en anglais, parce que l'anglais c'est bien
> In computing, reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change. With this paradigm it is possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.

D'ailleurs je préfère la version anglaise qui est bien plus complète :D
Mais qu'est-ce que tout ça veut dire ? (Spoiler : Que le projet Reactor c'est bien, mais on va l'expliquer)

Donc, en se référant à l'introduction de wikipédia, la programmation réactive ça parle de flux de données (data streams pour les intimes) qu'on va pouvoir manipuler dans tous les sens pour en faire ce qu'on veut, comme on veut. Comment ? Et bien un flux de données, ça peut être soit statique (ie, calculé à l'avance, et on va simplement travailler de manière lazy dessus comme avec l'API stream standard mais en mieux) soit dynamique — aka asynchrone — et ce dernier cas est le plus intéressant.
En effet, la manipulation de flux de données asynchrones qui m'est si chère est applicable dans bon nombre de contextes, qu'il s'agisse de communiquer avec une db, de faire des requêtes en tout genre, ou même de mettre en place une ux ! Si vous avez déjà fait un peu de programmation fonctionnelle (que je vous recommande chaudement au passage), sachez que la manipulation des Flux (tels des listes de haskell) comme des Monos (qui sont des monades) y ressemble fortement ; on remplace cependant les opérateurs infix par des méthodes, java oblige.
La programmation réactive avec Reactor s'intègre aussi parfaitement avec la majorité des paradigmes permettant de travailler de manière asynchrone : il est possible de créer des Mono et Flux à partir de Publisher, Promise, CompletableFuture...

Si vous avez des questions n'hésitez pas !

Un Emitter, c'est quoi ? Ça va vous paraître un peu simple, mais un Emitter c'est quelque chose qui émet autre chose. Un émetteur quoi.
Enfin la notion est un peu vide pour qu'on travaille dessus.
Alors vu qu'on sait qu'un emitter ça émet des trucs, on va créer une structure qui va simplement correspondre à tous ces trucs émis, en fonction du temps.
Cette structure, on peut lui attacher des transformations, c'est à dire qu'on peut transformer les trucs émis, la manière dont ils sont émis, la manière dont ils sont organisés, etc...
Cette structure ça s'appelle un Flux. Et un truc très important à retenir avec tout ce qui est Publisher (le petit nom d'emitter), c'est qu'il y en a deux types.
Est-ce que tu as déjà entendu parler des hot/cold observables ?

Alors déjà on va commencer par ce qu'est un Observable.
Un Observable, on peut l'observer (no joke). Ça veut dire que quand son état change, il va aller notifier tout ses Observer (ceux qui l'observent) qu'il a changé, pour que eux ils fassent des trucs, appliquent des transformations, changent leur état, et notifie leur propres Observer, et ainsi de suite. C'est ce qu'on appelle la propagation du changement (propagation of change en anglais, c'est un terme qui revient souvent).
Mais on peut aller plus loin !
Tu remarqueras avec moi qu'un Flux est un Observable. Quand son état change [ie quand une valeur est émise], la valeur va passer à travers les transformations du Flux, ainsi que les side-effects qu'on a pu lui assigner. Ces transformations et side-effects sont donc des Observer, non ? Mais ce sont aussi des Flux !
Quand on applique une transformation à un Flux, cela crée juste un nouveau Flux, avec peut-être une nouvelle organisation, peut-être des éléments mappés, peut-être exactement les même éléments, mais quand il y en a un qui passe il y a un effet qui se déclenche.
Qu'est-ce qu'on retient ? Un Flux, c'est à la fois un Observable, et un Observer.

Bon. Donc maintenant entrons dans le vif du sujet !
Un hot Observable, c'est pas compliqué. Son état peut changer n'importe quand, et les Observer ont intérêt à être déjà prêts quand le changement arrive. Par exemple, un émetteur qui capture les évènements de clics de souris sur une ui est un hot Observable: les clics de souris peuvent arriver n'importe quand !
En conséquence, il est très simple d'exprimer ces évènements par un Flux.

Un cold Observable, c'est pas compliqué non plus, on a de la chance. Il a froid, il est fatigué, il est paresseux. Il va bouger que si on lui demande. Il est lazy. Un cold Observable va changer d'état quand on lui souscrit. Cette action de souscrire ça veut dire qu'on produit un Subscriber (mais pas besoin de s'en occuper, Reactor le fait pour nous)
Ainsi, un cold Observable ne produit des valeurs que quand on lui demande, ie quand on lui souscrit. On aura beau lui mettre autant d'Observer qu'on veut, si on ne lui souscrit pas il n'émettra jamais rien. D'ailleurs, ça permet de s'assurer que tout les Observer sont définis et prêts avant de déclencher l'émission des valeurs.
Par exemple, lancer un appel à une database correspond à un cold Observable: on peut définir toutes les actions qui se produiront sur les valeurs une fois que l'appel sera de retour, mais on n'effectuera pas l'appel tant qu'on ne souscrira pas à l'Observable.
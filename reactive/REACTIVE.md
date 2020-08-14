Avez vous déjà entendu parler de la programmation réactive ? Non ? Et bien, vous devriez !

Donc, commençons par la définition imbitable de wikipédia :


# Définition

En [français](https://fr.wikipedia.org/wiki/Programmation_r%C3%A9active)
> En informatique, la programmation réactive est un paradigme de programmation visant à conserver une cohérence d'ensemble en propageant les modifications d'une source réactive (modification d'une variable, entrée utilisateur, etc.) aux éléments dépendants de cette source.

Et en [anglais](https://en.wikipedia.org/wiki/Reactive_programming), parce que l'anglais c'est bien
> In computing, reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change. With this paradigm it is possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.

D'ailleurs je préfère la version anglaise qui est bien plus complète :D

> Ouhla, tout ceci est bien compliqué, je ne comprends rien

Pas de panique, si vous ne comprenez pas c'est normal !


# La lumière de l'explication dans l'obscurité de wikipédia

## Une première approche

Si je vous dis `emitter`, vous pensez à quoi ?
> Euh... Un truc qui émet ?

**EXACT !** Un emitter, ça n'est ni plus, ni moins qu'un truc qui émet. Un émetteur, quoi. On appelle aussi ça `Publisher` par ailleurs.
Et quand ça a finit d'émettre, ça émet un signal de complétion, histoire ceux qui écoutent la radio sâchent que le programme ne passera plus et qu'ils peuvent arrêter d'écouter.
Comme vous n'avez pas l'air très impressionné je vais passer à la suite :p

Alors vu qu'on sait qu'un emitter ça émet des trucs, on va créer une structure qui va simplement correspondre à tous ces trucs émis, en fonction du temps.
Cette structure, on peut la transformer, c'est à dire qu'on peut modifier les valeurs émises, la manière dont elles sont émises, la manière dont elles sont organisées, etc...
Cette structure, on peut lui attacher des side-effects, c'est à dire qu'on peut faire une truc sur un truc quand le truc en question est émis.
Cette structure, c'est un flux de données (les fameux data streams dont parle wikipedia)
Cette structure, ça s'appelle un Flux.



## Une seconde approche

Je digresse. Je digresse tellement que tout d'un coup je décide qu'on va parler d'Observable. Alors un Observable, c'est quoi ?
Un Observable, on peut l'observer (no joke). Ça veut dire que quand son état change, il va aller notifier tout ses Observer (ceux qui l'observent) qu'il a changé, pour qu'ils appliquent des effets, et si ce sont eux mêmes des Observable, changent leur état, notifient leur propres Observer, et ainsi de suite. C'est ce qu'on appelle la propagation du changement (propagation of change en anglais, c'est un terme qui revient souvent quand on essaie de définir la programmation réactive et vous allez vite comprendre pourquoi).
Tout ça, c'est l'[Observer Pattern][observer].

> Mais dites donc, j'ai lu ce paragraphe pour rien alors ? C'est un complot pour me faire perdre mon temps, c'est ça ?!

Heureusement qu'on peut aller plus loin ! L'observer pattern c'est sympa, mais ça a des limitations...


### Un lien avec le sujet du cours sauvage apparait !

Voyons, voyons... Nous savons déjà qu'un Flux, c'est un flux de données qui correspond aux données émises par un émetteur. Vous l'aurez peut-être d'ores et déjà compris, mais que se passe-t-il quand l'émetteur émet une valeur ? Cette valeur passe dans le flux de données, ce qui veut dire que celui-ci *change d'état*.

MINDBLOW! Un Flux est un Observable!
Et en fait, c'est même mieux qu'un Observable classique tel que décrit plus haut. Si si, croyez moi sur parole !

Quand son état change (ie quand une valeur est émise), la valeur va passer à travers les transformations du Flux, ainsi que les side-effects qu'on a pu lui assigner. Ces transformations et side-effects sont donc des Observer, non ? Mais ce sont aussi des Flux !
Quand on applique une transformation à un Flux, cela crée un nouveau Flux, avec peut-être une nouvelle organisation, peut-être des éléments mappés, peut-être exactement les même éléments, et une action se déclenchant à chaque passage. En transformant un Flux on a pu créer un nouveau Flux qui observe le premier afin de déduire ses propres éléments.
Qu'est-ce qu'on retient ? Un Flux, c'est à la fois un Observable, et un Observer (et un Publisher ! En effet, il émet lui aussi des données).

Bon, un Flux c'est un hybride Observable/Observer/Publisher, ok, c'est sympa. Mais en fait c'est encore plus sympa.
Donc maintenant entrons dans le vif du sujet !



## Le vif du sujet

Il existe deux types d'Observable, les hot observables ainsi que les cold observables. Et figurez vous qu'un flux ça peut être soit l'un, soit l'autre. UwU.

Un hot observable, c'est pas compliqué. Son état peut changer n'importe quand, et les Observer ont intérêt à être déjà prêts quand le changement arrive. Par exemple, un émetteur qui capture les évènements de clics de souris sur une ui est un hot Observable: les clics de souris peuvent arriver n'importe quand !
En représentant ces clics de souris par un Flux, on se retrouve tout simplement avec un data stream dont le type de donnée est un évènement de clic de souris. Rien de compliqué, n'est-ce pas ? Il suffit ensuite d'appliquer autant de transformations et d'effets secondaires à chaque évènement que nécessaire pour pouvoir y associer le comportement désiré.

Un cold observable, c'est pas compliqué non plus, on a de la chance. Un cold observable a froid (comme son nom pourrait l'indiquer), il est fatigué, il est paresseux. Il bougera uniquement si on le force. Il est *lazy*. Un cold Observable ne changera son état qu'à partir du moment où on lui demande. Par exemple, un Flux qui serait un cold observable n'émettra jamais rien avant qu'on lui demande. Et après cela, il n'aura émis *que* ce qu'on lui demande: c'est un partisan du moindre effort !

Cette action de forcer un cold observable à se remuer le cul s'appelle lui souscrire. On aura beau lui attacher autant d'Observer qu'on veut, si on ne souscrit pas à un cold observable il n'émettra jamais rien. D'ailleurs, ça permet de s'assurer que tout les Observer sont définis et prêts avant de déclencher l'émission des valeurs, car la souscription doit être la *dernière* action que l'on fait sur un cold observable; après on n'y touche plus !

Par exemple, pour lancer un appel à une database vous aurez besoin d'un Flux se comportant comme un cold observable: d'abord il faut définir toutes les manipulations qui auront lieu sur les valeurs récupérées, et ensuite seulement vous pourrez lui souscrire, déclenchant alors l'appel à la database. Toutes vos manipulations seront effectuées de manière totalement asynchrone une fois que l'appel est de retour.

Un autre effet intéressant du cold observable est que le signal de complétion (propriété du Flux en tant que Publisher) retirera toutes les souscriptions, tandis qu'un hot observable pourrait ne jamais finir et ne jamais émettre de signal de complétion.

Pfiou, cette section était plutôt longue.



## Mais on n'a pas encore fini !

Rappelons tout ce qu'on a appris sur un Flux jusqu'à présent :
 - c'est un Publisher
 - c'est un Observable (hot ou cold)
 - c'est un Observer
 - c'est un flux de données

> C'est déjà pas mal !

En effet, c'est déjà pas mal. Cependant quelque chose me dérange... comme si... on avait oublié quelque chose...
Ah oui ! Que fait on quand un Publisher n'émet qu'une, et une seule valeur ? (voire pas du tout)
C'est un cas très courant, et créer un flux de données pour traiter un seul objet, c'est un peu comme construire une autoroute à la disposition d'une seule voiture. Heureusement, il y a les `Mono`. Un Mono fonctionne en tout point comme un Flux, à ceci près qu'il ne se complètera toujours qu'avec au plus une valeur.

Par exemple, si dans votre fameux appel à la database vous voulez récupérer les informations d'un seul client, un Mono est ce qu'il vous faut !

Nous avons fini pour de bon, cette fois ci ^^
Vous trouverez ci-dessous des liens pour approfondir les notions brièvement abordées pendant cette fiche.


# Les liens ci-dessous pour approfondir les notions brièvement abordées pendant cette fiche.

* **[Observer Pattern][observer] :** Les grands principes de l'observer pattern
* **[Hot/Cold Observables](https://leecampbell.com/2010/08/19/rx-part-7-hot-and-cold-observables/) :** Pour une explication plus détaillée de la différence entre hot et cold observables
* **[Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/#intro-reactive) :** Pourquoi vous devriez utiliser la programmation réactive partout
* **[Introduction to Reactive](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754) :** Une très bonne ressource pour commencer à se familiariser avec du code en programmation réactive. Utilise Rx.js mais vous pouvez transposez dans votre langage.

### Libs pour utiliser la sacro-sainte programmation réactive dans votre langage favori
* **[Reactor](https://projectreactor.io/) :** uniquement pour les langages jvm, mais bien mieux nommée et intuitive que RxJava selon moi
* **[ReactiveX](http://reactivex.io/) :** disponible dans moult langages, vous trouverez une liste dans le lien

[observer]: https://github.com/readthedocs-fr/notions/blob/master/poo/design_patterns/fr/observateur/OBSERVATEUR_PATTERN.md "L'Observer Pattern c'est bien, la programmation réactive c'est mieux !"
